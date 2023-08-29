package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Configurations.Mail;
import com.example.gestion_des_conges.Entities.*;
import com.example.gestion_des_conges.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CongeServices implements ICongeServices {

    @Autowired
    private final ICongeRepository congeRepository;

    @Autowired
    private final IEmployeeRepository employeeRepository;

    @Autowired
    private final IJourFerieRepository jourFerieRepository;

    @Autowired
    private final IMotifRefusRepository motifRefusRepository;

    @Autowired
    private final IPolitiqueRepository politiqueRepository;

    @Autowired
    private final IRoleRepository roleRepository;

    @Autowired
    private final ITypeCongeRepository typeCongeRepository;

    @Autowired
    private final IAutorisationRepository autorisationRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${files.file.path}")
    private String files;

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
    public ResponseEntity<?> reponseConge(int idConge, Etat etat/*, Principal principal*/, MotifRefus motifRefus) throws MessagingException {
        Conge conge = congeRepository.findById(idConge).orElse(null);
        Map<String, String> responseMap = new HashMap<>();

        if(conge!=null){

            Employee demandeur = employeeRepository.findById(conge.getDemandeur().getIdEmp()).orElse(null);

            //Employee validateur = employeeRepository.findEmployeeByUsername(principal.getName());
            Employee validateur = employeeRepository.findById(1).orElse(null);

            if(conge.getEtat().toString().equals(Etat.Annule.toString())){
                String rep = "Congé est déja annulé .";
                responseMap.put("message", rep);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }

            if(!conge.getEtat().equals(Etat.En_Attente)){
                String rep = "La réponse est déja envoyée  .";
                responseMap.put("message", rep);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }

            if(demandeur == null || validateur == null){
                String rep = "Mauvaise demande.";
                responseMap.put("message", rep);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }

            if(demandeur.getSuperviseur()!=validateur){
                String rep = "L'employée n'est pas dans votre entité.";
                responseMap.put("message", rep);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }

            if (!validateur.getRole().getNomRole().equals("admin") && !validateur.getRole().getNomRole().equals("superviseur")) {
                String rep = "Accès refusé. Vous n'avez pas les permissions nécessaires.";
                responseMap.put("message", rep);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }

            if(etat==null || etat.toString().equals("En_Attente")){
                String rep = "Donner la nouvelle état du congé.";
                responseMap.put("message", rep);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }

            if(etat.toString().equals("Refuse")){
                if(motifRefus.getTypeMotif()==null){
                    String rep = "Réponse non valide. Vous n'avez pas donner le motif de refus.";
                    responseMap.put("message", rep);
                    return new ResponseEntity<>(responseMap, HttpStatus.OK);
                }

                if (motifRefus.getIdMotif() != null) {
                    if(motifRefusRepository.findById(motifRefus.getIdMotif()).isPresent()){
                        motifRefus = motifRefusRepository.findById(motifRefus.getIdMotif()).get();
                        if(motifRefus.getConge().isEmpty()){
                            List<Conge> c = new ArrayList<>();
                            c.add(conge);
                            motifRefus.setConge(c);
                        }
                        else {
                            motifRefus.getConge().add(conge);
                        }
                    }
                }

                if(motifRefus.getDescription()==null){
                    return new ResponseEntity<>("Donner une description de refus.", HttpStatus.BAD_REQUEST);
                }

                if (motifRefus.getTypeMotif() == null ) {
                    motifRefus.setTypeMotif(TypeMotif.Autre);
                }

                motifRefusRepository.save(motifRefus);
                conge.setMotifRefus(motifRefus);
            }

            if(etat.toString().equals("Accepte")){
                if(conge.getTypeConge().getNatureType().equals(NatureType.Normale)){
                    Duration duree = Duration.between(conge.getDateDebut(), conge.getDateFin());

                    if(demandeur.getSoldeConge()-duree.toDays()<0){
                        String rep = "Solde du conge de ce employee est insuffisant.";
                        responseMap.put("message", rep);
                        return new ResponseEntity<>(responseMap, HttpStatus.OK);
                    }
                    if(duree.toDays()!=0){
                        demandeur.setSoldeConge(demandeur.getSoldeConge()-(duree.toDays()-calculSoldeConge(conge.getIdConge())));
                    }
                    else {
                        demandeur.setSoldeConge(demandeur.getSoldeConge()-0.5);
                    }
                    employeeRepository.save(demandeur);
                }
            }

            conge.setDateValidation(LocalDateTime.now());
            conge.setEtat(etat);
            conge.setValidateur(validateur);
            congeRepository.save(conge);

            ///////////////////////////////////// Mail validation (Mail Validation) + Mail de refus (Mail Refus)

            Mail mail = new Mail();

            mail.setFrom("achref.zitoun@esprit.tn");
            mail.setMailTo(demandeur.getEmail());
            mail.setSubject("Résponse du congé");

            Map<String, Object> model = new HashMap<String, Object>();

            model.put("namedemandeur", demandeur.getNom() + " " + demandeur.getPrenom());
            model.put("datedebut", conge.getDateDebut());
            model.put("datefin", conge.getDateFin());
            model.put("namevalidateur", validateur.getNom() + " " + validateur.getPrenom());
            mail.setProps(model);

            if(etat.toString().equals(Etat.Accepte.toString())){
              //  sendEmail(mail, "EmailValidation.html");
            }
            else if(etat.toString().equals(Etat.Refuse.toString())){
                model.put("motifrefus",motifRefus.getDescription());
              //  sendEmail(mail, "EmailRefus.html");
            }

            String rep = "Conge du : " + conge.getDemandeur().getNom() + " " + conge.getDemandeur().getPrenom() +" est " + etat.toString() ;
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);

        }
        else {
            String rep = "Conge indisponnible.";
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> affectationConge(Conge conge, int idDemandeur,/* Principal principal, */int idTypeConge) throws MessagingException, IOException {
        //Employee validateur = employeeRepository.findEmployeeByUsername(principal.getName());
        Employee validateur = employeeRepository.findById(1).orElse(null);
        Employee demandeur = employeeRepository.findById(idDemandeur).orElse(null);
        TypeConge typeConge = typeCongeRepository.findById(idTypeConge).orElse(null);

        Map<String, String> responseMap = new HashMap<>();

        if (!validateur.getRole().getNomRole().equals("admin") && !validateur.getRole().getNomRole().equals("superviseur")) {
            String rep = "Accès refusé. Vous n'avez pas les permissions nécessaires.";
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }

        if(conge==null){
            String rep = "Mauvaise demande.";
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }

        if(demandeur== null){
            String rep = "Donner un demandeur du congé.";
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }

        if(typeConge== null){
            String rep = "Donner le type de congé.";
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }

        if(!demandeur.getPolitique().getTypeConge().contains(typeConge)){
            String rep = "Ce employée ne peux pas bénéficer de ce type de congé.";
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }

        if(!checkDisponibilite(demandeur,conge.getDateDebut().toLocalDate(),conge.getDateFin().toLocalDate())){
            String rep = "Ce employée a un congé dans ces dates.";
            responseMap.put("message", rep);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        }

        conge.setEtat(Etat.En_Attente);
        conge.setDemandeur(demandeur);
        conge.setTypeConge(typeConge);
        congeRepository.save(conge);
        float f = Duration.between(conge.getDateDebut(), conge.getDateFin()).toDays()-calculSoldeConge(conge.getIdConge());
        conge.setDuree(f);
        congeRepository.save(conge);

        ///////////////////////////////////////////////// Mail confirmation au demandeur (Mail Confirmation)

        Mail maildemandeur = new Mail();

        maildemandeur.setFrom("achref.zitoun@esprit.tn");
        maildemandeur.setSubject("Demande de congé");

        Map<String, Object> modeldemandeur = new HashMap<>();

        modeldemandeur.put("demandeur", demandeur.getNom() + " " + demandeur.getPrenom());
        modeldemandeur.put("duree", f);
        modeldemandeur.put("datedebut", conge.getDateDebut());
        modeldemandeur.put("datefin", conge.getDateFin());
        maildemandeur.setProps(modeldemandeur);

        /* if(demandeur.getEmail()!=null){
            maildemandeur.setMailTo(demandeur.getEmail());
           sendEmail(maildemandeur, "EmailConfirmation.html");
        }*/

        ///////////////////////////////////////////////// Mail information au validateur (Mail Information)
        if(demandeur.getSuperviseur()!=null){
            Mail mailvalidateur = new Mail();

            mailvalidateur.setFrom("achref.zitoun@esprit.tn");
            mailvalidateur.setSubject("Demande de congé : " + demandeur.getNom() + " " + demandeur.getPrenom());

            Map<String, Object> modelvalidateur = new HashMap<String, Object>();

            modelvalidateur.put("superviseur", demandeur.getSuperviseur().getNom() + " " + demandeur.getSuperviseur().getPrenom());
            modelvalidateur.put("demandeur", demandeur.getNom() + " " + demandeur.getPrenom());
            modelvalidateur.put("duree", Duration.between(conge.getDateDebut(), conge.getDateFin()).toDays());
            modelvalidateur.put("datedebut", conge.getDateDebut());
            modelvalidateur.put("datefin", conge.getDateFin());
            mailvalidateur.setProps(modelvalidateur);

          /*  if(demandeur.getSuperviseur().getEmail()!=null) {
                mailvalidateur.setMailTo(demandeur.getSuperviseur().getEmail());
                sendEmail(mailvalidateur, "EmailInformation.html");
            }*/
        }

        String rep = "conge du : " + conge.getDemandeur().getNom() + " " + conge.getDemandeur().getPrenom()
                +" est enregistre ";
        responseMap.put("message", rep);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }



    @Override
    public ResponseEntity<byte[]> exportCongeExcel(LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<Conge> conges = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet;

        String fileName;

        Map<String, String> responseMap = new HashMap<>();

        if(dateDebut!=null || dateFin!=null){
            conges.addAll(congeRepository.findByDateDebutBetweenAndEtat(dateDebut, dateFin, Etat.Accepte));
            sheet = workbook.createSheet("Congé accepter" + dateDebut.getMonth() + "-" + dateDebut.getDayOfMonth() + "_" + dateFin.getMonth() + "-" + dateFin.getDayOfMonth());
            fileName = files + "\\Congé_accepter" +  dateDebut.getMonth() + "-" + dateDebut.getDayOfMonth() + "_" + dateFin.getMonth() + "-" + dateFin.getDayOfMonth() + ".xlsx";
        }
        else {
            conges.addAll(congeRepository.findByEtat(Etat.Accepte));
            sheet = workbook.createSheet("Tous les congé accepter");
            fileName = files + "\\Tous_les_congés_accepter"+".xlsx";
        }

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID Employee");
        headerRow.createCell(1).setCellValue("ID Congé");
        headerRow.createCell(2).setCellValue("Nom");
        headerRow.createCell(3).setCellValue("Prenom");
        headerRow.createCell(4).setCellValue("Type de congé");
        headerRow.createCell(5).setCellValue("Date debut");
        headerRow.createCell(6).setCellValue("Date fin");
        headerRow.createCell(7).setCellValue("Validateur");

        int rowNum=1;

        for(Conge conge : conges){

            Employee demandeur = employeeRepository.findById(conge.getDemandeur().getIdEmp()).orElse(null);

            Row row = sheet.createRow(rowNum++);

            if(demandeur!=null){
                row.createCell(0).setCellValue(demandeur.getIdEmp());
                row.createCell(1).setCellValue(conge.getIdConge());
                row.createCell(2).setCellValue(demandeur.getNom());
                row.createCell(3).setCellValue(demandeur.getPrenom());
                row.createCell(4).setCellValue(conge.getTypeConge().getNomType());
                row.createCell(5).setCellValue(conge.getDateDebut().toString());
                row.createCell(6).setCellValue(conge.getDateFin().toString());
                row.createCell(7).setCellValue(conge.getValidateur().getNom() + " " + conge.getValidateur().getPrenom());
            }
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            byte[] contents = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            return new ResponseEntity<>(contents, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean checkDisponibilite(Employee employee, LocalDate startDate, LocalDate endDate) {
        for (Conge conge : employee.getListDemande()) {
            LocalDate startConge = conge.getDateDebut().toLocalDate();
            LocalDate endConge = conge.getDateFin().toLocalDate();

            if ((startDate.isAfter(startConge) && startDate.isBefore(endConge))
                    || (endDate.isAfter(startConge) && endDate.isBefore(endConge))
                    || startDate.isEqual(startConge)
                    || endDate.isEqual(endConge)) {
                return false;
            }
        }
        return true;
    }
/*
    @Scheduled(cron = "0 0 1 1 * *")
    public void updateSoldeCongeEmployees(){
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        for (Employee e : employees){
            e.setSoldeConge((long) (e.getSoldeConge() + 2.17));
            e.setAutorisationDuration(6);
            employeeRepository.save(e);
        }
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void updateSoldeAutorisationEmployees(){
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        for (Employee e : employees){
            e.setAutorisationJour(false);
            employeeRepository.save(e);
        }
    }
*/
    @Override
    public ResponseEntity<?> getAutorisation(Autorisation autorisation){
        Employee employee = employeeRepository.findById(1).orElse(null);
        Map<String, String> responseMap = new HashMap<>();
        System.out.println("test");
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
            if(employee.getAutorisationDuration()<2 ){
                String rep = "Solde d'autorisation insuffisant.";
                responseMap.put("message", rep);
                return new ResponseEntity<>(responseMap, HttpStatus.OK);
            }
        }

        employee.setAutorisationDuration( (employee.getAutorisationDuration() - 2));
        employee.setAutorisationJour(true);
        employeeRepository.save(employee);
        autorisation.setDemandeur(employee);
        autorisation.setDateFin(autorisation.getDateDebut().plusHours(2));
        autorisationRepository.save(autorisation);

        String rep = "Autorisation enregistrer avec succées.";
        responseMap.put("message", rep);
        System.out.println("test");

        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @Override
    public float calculSoldeConge(int idConge){
        Conge conge = congeRepository.findById(idConge).orElse(null);
        Employee demandeur = conge.getDemandeur();
        LocalDateTime dateDebut = conge.getDateDebut();
        LocalDateTime dateFin = conge.getDateFin();
        Duration dureeExacte = Duration.between(dateDebut, dateFin);

        List<JourFerie> jourFeries = demandeur.getPolitique().getJourFerie();
        List<JourFerie> jourInclud = new ArrayList<>();
        for (JourFerie j : jourFeries) {
            if (!(dateDebut.isAfter(j.getDateFin().plusDays(2)) || dateFin.isBefore(j.getDateDebut().minusDays(2)))) {
                jourInclud.add(j);
            }
        }


        LocalDate currentDate = dateDebut.toLocalDate();
        long daysToDeduct = 0;

        while (!currentDate.isAfter(dateFin.toLocalDate())) {
            boolean isWeekend = currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY;

            boolean isIncluded = false;
            for (JourFerie j : jourInclud) {
                if ((!currentDate.isBefore(j.getDateDebut().toLocalDate()) && !currentDate.isAfter(j.getDateFin().toLocalDate())) || dateDebut.isAfter(j.getDateFin()) || dateFin.isBefore(j.getDateDebut())) {
                    isIncluded = true;
                    break;
                }
            }

            if (isWeekend || isIncluded) {
                daysToDeduct++;
            }

            System.out.println(isWeekend);
            System.out.println(isIncluded);

            currentDate = currentDate.plusDays(1);
        }

        return daysToDeduct;
    }


}
