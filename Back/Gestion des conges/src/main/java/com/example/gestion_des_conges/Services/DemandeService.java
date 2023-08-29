package com.example.gestion_des_conges.Services;


import com.example.gestion_des_conges.Configurations.Mail;
import com.example.gestion_des_conges.Entities.*;
import com.example.gestion_des_conges.Repositories.ICongeRepository;
import com.example.gestion_des_conges.Repositories.IEmployeeRepository;
import com.example.gestion_des_conges.Repositories.IRoleRepository;
import com.example.gestion_des_conges.Repositories.ITypeCongeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class DemandeService implements  IDemandeService{

    private final ITypeCongeRepository typeCongeRepository;
    private final ICongeRepository congeRepository;

    private final IEmployeeRepository employeeRepository;

    private final IRoleRepository roleRepository;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    //Mail
    @Override
    public void sendEmail(Mail mail, String templateName) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(mail.getProps());
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getMailTo());
        helper.setSubject(mail.getSubject());
        String html = templateEngine.process(templateName, context);
        helper.setText(html, true);
        mailSender.send(message);
    }


    @Override
    public TypeConge addTypeConge(TypeConge typeConge) {
        return typeCongeRepository.save(typeConge);
    }

    @Override
    //@Scheduled(cron = "0 0 1 1 * *")
    public void updateSoldeCongeEmployees(){
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        for (Employee e : employees){
            e.setSoldeConge((long) (e.getSoldeConge() + 2.17));
            e.setAutorisationDuration(6);
            employeeRepository.save(e);
        }
    }

    @Override
    //@Scheduled(cron = "0 0 1 * * *")
    public void updateSoldeAutorisationEmployees(){
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        for (Employee e : employees){
            e.setAutorisationJour(false);
            employeeRepository.save(e);
        }
    }

    @Override
    public ResponseEntity<?> getAutorisation( int idEmployee, float duree){
        Employee employee = employeeRepository.findById(idEmployee).orElse(null);
        Map<String, String> responseMap = new HashMap<>();

        if(employee==null){
            String rep = "Employee non trouver.";
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }
        else{
            if(employee.isAutorisationJour()){
                String rep = "Tu as deja pris une autorisation aujourd'hui .";
                responseMap.put("message", rep);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }
            if(employee.getAutorisationDuration()<duree ){
                String rep = "Solde d'autorisation insuffisant.";
                responseMap.put("message", rep);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }
        }
        employee.setAutorisationDuration((long) (employee.getAutorisationDuration() - duree));
        employee.setAutorisationJour(true);

        String rep = "Autorisation enregistrer avec succées.";
        responseMap.put("message", rep);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> addCongeAndAssignCongeToType(Conge conge, MultipartFile file , Integer idType ) throws IOException, MessagingException {

        Employee e = employeeRepository.findById(1).orElse(null);

        TypeConge typeConge = typeCongeRepository.findById(idType).orElse(null);

        List<Conge> congesEmployee = estEnConge(e.getIdEmp(), conge.getDateDebut(), conge.getDateFin());
        Map<String, String> responseMap = new HashMap<>();

        if (file != null) {
            byte[] bytes = file.getBytes();
            conge.setPieceJointe(bytes);
        }
        conge.setTypeConge(typeConge);
        conge.setDemandeur(e);
        conge.setValidateur(e.getSuperviseur());

        if (typeConge.getNatureType().equals(NatureType.Speciale)) {

                if (!typeConge.getNomType().equals("Congé de maladie")){
                    float calculDurationSpecaile = calculDurationSpecaile(conge.getDateDebut(), idType);
                    float nbr = typeConge.getNbrJours();
                    LocalDateTime calculatedEndDate = conge.getDateDebut().plusDays((long) nbr);
                    conge.setDuree(calculDurationSpecaile);
                    conge.setDateFin(calculatedEndDate);
                    congeRepository.save(conge);
                }
                else {
                    float calculDurationNormale = calculDurationNormale(conge.getDateDebut(), conge.getDateFin());
                    conge.setDuree(calculDurationNormale);
                    congeRepository.save(conge);
                }



        }
        else if (typeConge.getNatureType().equals(NatureType.Normale)) {
            float calculDurationNormale = calculDurationNormale(conge.getDateDebut(), conge.getDateFin());
            if (e.getSoldeConge() - calculDurationNormale > 0 && !typeConge.getNomType().equals("Congé sans solde")) {
                conge.setDuree(calculDurationNormale);
                congeRepository.save(conge);

            }
            else if (typeConge.getNomType().equals("Demi jour matin") || typeConge.getNomType().equals("Demi jour après midi")){
                conge.setDuree((float) 0.5);
                congeRepository.save(conge);
            }
            else if (typeConge.getNomType().equals("Congé sans solde")){
                conge.setDuree(calculDurationNormale);
                congeRepository.save(conge);

            }
            else {
                String rep = "Vous n'avez pas le droit de passer un congé";
                responseMap.put("message", rep);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);

            }
        }

        Mail mail = new Mail();

        mail.setFrom("chaima.hadjkacem@esprit.tn");
        mail.setMailTo(e.getSuperviseur().getEmail());
        mail.setSubject("Demande de congé");

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("namevalidateur", e.getSuperviseur().getNom()+ " " + e.getSuperviseur().getPrenom());
        model.put("namedemandeur", e.getNom() + " " + e.getPrenom());
        model.put("datedebut", conge.getDateDebut());
        model.put("datefin", conge.getDateFin());
        model.put("datedemande", conge.getDateDemande());
        model.put("dure", conge.getDuree());
        model.put("type", conge.getTypeConge().getNomType());

        mail.setProps(model);
        //sendEmail(mail, "EmailDeDemande.html");

        String rep = "OK";
        responseMap.put("message", rep);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);

    }


    @Override
    public float calculDurationSpecaile(LocalDateTime startDate, Integer idType) {
        TypeConge typeConge = typeCongeRepository.findById(idType).orElse(null);
        float nbr = typeConge.getNbrJours();
        LocalDateTime calculatedEndDate = startDate.plusDays((long) nbr);
        long jours = ChronoUnit.DAYS.between(startDate, calculatedEndDate);
        return jours;
    }

    @Override
    public float calculDurationNormale(LocalDateTime startDate, LocalDateTime endDate){
        long jours = ChronoUnit.DAYS.between(startDate, endDate);
        return jours;
    }

    @Override
    public Conge updateConge(Conge conge , Integer idType){
        TypeConge typeConge = typeCongeRepository.findById(idType).orElse(null);
        conge.setTypeConge(typeConge);
        long jours = ChronoUnit.DAYS.between(conge.getDateDebut(), conge.getDateFin());
        conge.setDuree(jours);
        return congeRepository.save(conge);
    }

    @Override
    public List<Conge> getAllConge(){
        List<Conge> conges = new ArrayList<>();
        congeRepository.findAll().forEach(conges::add);
        return conges;
    }

    @Override
    public List<Conge> getAllCongeEnAttente(){
        List<Conge> conges = congeRepository.findByEtat(Etat.En_Attente);
        return conges;
    }


    @Override
    public List<Conge> getListCongeByEmploye(/*Integer id*/){
        Employee employee = employeeRepository.findById(1).orElse(null);
        List <Conge> conges = new ArrayList<>();
        for(Conge c : employee.getListDemande()){
            conges.add(c);
        }
        return conges ;
    }



    @Override
    public List<TypeConge> getAllTypes(){
        List<TypeConge> typeconges = new ArrayList<>();
        typeCongeRepository.findAll().forEach(typeconges::add);
        return typeconges;
    }

    @Override
    public Conge retrieveConge(Integer idConge) {
        return congeRepository.findById(idConge).orElse(null);
    }

    @Override
    public void annulConge(Integer idConge) throws MessagingException {
        Employee e = employeeRepository.findById(1).orElse(null);


        Conge cong = congeRepository.findById(idConge).orElse(null);
        cong.setEtat(Etat.valueOf("Annule"));
        congeRepository.save(cong);


        Mail mail = new Mail();

        mail.setFrom("chaima.hadjkacem@esprit.tn");
        mail.setMailTo(e.getSuperviseur().getEmail());
        mail.setSubject("Annulation de la demande de congé ");

        Map<String, Object> model = new HashMap<String, Object>();

        model.put("namevalidateur", e.getSuperviseur().getNom()+ " " + e.getSuperviseur().getPrenom());
        model.put("namedemandeur", e.getNom() + " " + e.getPrenom());
        model.put("datedebut", cong.getDateDebut());
        model.put("datefin", cong.getDateFin());
        model.put("datedemande", cong.getDateDemande());


        mail.setProps(model);
        sendEmail(mail, "EmailAnnulation.html");


    }


    public List<Conge> estEnConge(Integer idemploye, LocalDateTime dateDebut, LocalDateTime dateFin) {
        Employee employe = employeeRepository.findById(idemploye).orElse(null);
        List<Conge> congesPendantPeriode = congeRepository.findByDemandeurAndDateDebutIsLessThanEqualAndDateFinIsGreaterThanEqual(employe, dateFin, dateDebut);
        return congesPendantPeriode;
    }


    @Override
    public ResponseEntity<?> delegationDeRole( /*Integer idsup,*/ Integer idEmpl,LocalDateTime dateDebut, LocalDateTime dateFin) {
        Employee superv = employeeRepository.findById(2).orElse(null);
        Employee emp = employeeRepository.findById(idEmpl).orElse(null);
        Map<String, String> responseMap = new HashMap<>();


        Role superviseurRole = roleRepository.findByNomRole("superviseur");


        Conge cSuperv = congeRepository.findFirstByDemandeurAndDateDebutAndDateFin(superv,dateDebut,dateFin);

        if (cSuperv != null  && dateDebut.isBefore(LocalDateTime.now()) && dateFin.isAfter(LocalDateTime.now())) {
          // l'employé n'est pas en congé pendant la période du superviseur.
            List<Conge> congesEmployeePendantPeriodeSuperviseur = estEnConge(idEmpl, dateDebut, dateFin);
                if (congesEmployeePendantPeriodeSuperviseur.isEmpty()) {
                        emp.setRole(superviseurRole);
                        cSuperv.setIdDelegue(idEmpl);
                        employeeRepository.save(emp);
                        congeRepository.save(cSuperv);
                        String rep = "OK";
                        responseMap.put("message", rep);

                }
                else {

                        String rep = "L'employe est egalement en conge pendant cette periode.";
                        responseMap.put("message", rep);

                }
        }
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }


    //@Scheduled(fixedDelay = 60000)
    @Override
    public void retablirRoleDeleg(){
        List<Conge> congesSuperviseurs = congeRepository.findAllByDateFinLessThan(LocalDateTime.now());

        for (Conge conge : congesSuperviseurs) {
            if (conge.getIdDelegue() != null) {

                Employee empDelegue = employeeRepository.findById(conge.getIdDelegue()).orElse(null);

                if (empDelegue != null) {
                    Conge cSuperv = congeRepository.findFirstByDemandeur(empDelegue);
                    if (cSuperv != null && cSuperv.getDateDebut().isBefore(LocalDateTime.now()) && cSuperv.getDateFin().isBefore(LocalDateTime.now())) {

                        Role roleEmploye = roleRepository.findByNomRole("employee");
                        empDelegue.setRole(roleEmploye);
                        employeeRepository.save(empDelegue);
                    }
                }
            }
        }
    }

    @Override
    public List<Employee> getEmployeeDev(){
        return employeeRepository.getEmplyeesDev();
    }

    @Override
    public List<Employee> getEmployeeControle(){
        return employeeRepository.getEmplyeesContr();
    }



    @Override
    public ResponseEntity<byte[]> generatePdf(/*int idemp*/) throws  IOException, DocumentException {

        Employee employee = employeeRepository.findById(1).orElse(null);
        List<Conge> conges = congeRepository.findByDemandeurAndEtat(employee,Etat.Accepte);


        Document document = new Document();

        File file = new File("Historique" + employee.getNom()+ ".pdf");

        PdfWriter.getInstance(document, Files.newOutputStream(file.toPath()));
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("employee",employee);
        context.setVariable("conges",conges);


        String htmlContent = templateEngine.process("templates/Historique.html", context);
        PdfWriter.getInstance(document, Files.newOutputStream(file.toPath()));
        document.open();
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(htmlContent, Files.newOutputStream(file.toPath()), converterProperties);

        byte[] contents = Files.readAllBytes(file.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("attachment", "Historique.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);

        return response;
    }

    @Override
    public List<TypeConge> getTypeCongebyNature(NatureType natureType) {
        return typeCongeRepository.findAllByNatureType(natureType);
    }

    @Value("${files.file.path}")
    private String files;



    @Override
    public ResponseEntity<byte[]> exportCongeExcel() {

        Workbook workbook = new XSSFWorkbook();

        Employee demandeur = employeeRepository.findById(1).orElse(null);
        List<Conge> conges = congeRepository.findByDemandeurAndEtat(demandeur,Etat.Accepte);
        Sheet sheet;

        String fileName;


        sheet = workbook.createSheet("Tous les congé accepter");
        fileName = files+"\\Liste_Des_congés_accepter"+demandeur.getNom()+".xlsx";


        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Type de congé");
        headerRow.createCell(1).setCellValue("Date de soumission");
        headerRow.createCell(2).setCellValue("Date debut");
        headerRow.createCell(3).setCellValue("Date fin");
        headerRow.createCell(4).setCellValue("Validateur");

        int rowNum=1;

        for(Conge conge : conges){

            Row row = sheet.createRow(rowNum++);

            if(demandeur!=null){
                row.createCell(0).setCellValue(conge.getTypeConge().getNomType());
                row.createCell(1).setCellValue(conge.getDateDemande().toString());
                row.createCell(2).setCellValue(conge.getDateDebut().toString());
                row.createCell(3).setCellValue(conge.getDateFin().toString());
                row.createCell(4).setCellValue(conge.getValidateur().getNom() + " " + conge.getValidateur().getPrenom());
            }
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] excelBytes = byteArrayOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "Liste_Des_Conges.xlsx");

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }


    @Override
    public List<Conge> touteLaListeDesConges(){
        List<Conge> conges = congeRepository.findByEtat(Etat.Accepte);
        return conges;
    }


    @Override
    public List<Employee> getEmployeesWithLeaveContainingDate(LocalDateTime targetDate) {
        List<Employee> employees = new ArrayList<>();
        List<Employee> uniqueEmployees = new ArrayList<>();

        List<Conge> conges = new ArrayList<>();
         congeRepository.findAll().forEach(conges::add);

        for (Conge conge : conges) {
            LocalDateTime dateDebutConge = conge.getDateDebut();
            LocalDateTime dateFinConge = conge.getDateFin();

            if(conge.getEtat().equals(Etat.Accepte)){
                if (targetDate.toLocalDate().isEqual(dateDebutConge.toLocalDate())
                        || targetDate.toLocalDate().isEqual(dateFinConge.toLocalDate())
                        || (targetDate.toLocalDate().isAfter(dateDebutConge.toLocalDate()) && targetDate.toLocalDate().isBefore(dateFinConge.toLocalDate()))) {
                           Employee employee = conge.getDemandeur();
                             if (!uniqueEmployees.contains(employee)) {
                               uniqueEmployees.add(employee);
                                  employees.add(employee);
                             }
                }
            }
        }
        return employees;
    }

    @Override
    public List<Conge> getListCongeDesSuperviseurs (/*Integer idEmpl*/){
        Employee e = employeeRepository.findById(2).orElse(null);

            if (e.getRole().getNomRole().equals("superviseur")){
                List<Conge> conges = congeRepository.findByDemandeurAndEtat(e,Etat.Accepte);
                return conges ;
            }
        return null ;
    }


    @Override
    public String getDelegueEmployeeName(Integer idConge) {
        Conge congeOptional = congeRepository.findById(idConge).orElse(null);

        if (congeOptional!=null) {
            Integer idDelegue = congeOptional.getIdDelegue();

            if (idDelegue != null) {
                Employee employeeOptional = employeeRepository.findById(idDelegue).orElse(null);

                if (employeeOptional!=null) {
                    return employeeOptional.getNom() + employeeOptional.getPrenom()  ;
                }
            }
        }

        return null;
    }
}






