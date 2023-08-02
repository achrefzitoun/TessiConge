package com.example.gestion_des_conges.Services;


import com.example.gestion_des_conges.Entities.*;
import com.example.gestion_des_conges.Repositories.ICongeRepository;
import com.example.gestion_des_conges.Repositories.IEmployeeRepository;
import com.example.gestion_des_conges.Repositories.IRoleRepository;
import com.example.gestion_des_conges.Repositories.ITypeCongeRepository;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class DemandeService implements  IDemandeService{

    private final ITypeCongeRepository typeCongeRepository;
    private final ICongeRepository congeRepository;

    private final IEmployeeRepository employeeRepository;

    @Autowired
    private final IRoleRepository roleRepository;
    @Autowired
    private JavaMailSender mailSender;

    //Mail
    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);
    }

    @Override
    public TypeConge addTypeConge(TypeConge typeConge) {
        return typeCongeRepository.save(typeConge);
    }

    @Override
    public void addCongeAndAssignCongeToType(Conge conge, MultipartFile file , Integer idType ) throws IOException, MessagingException {

        Employee e = employeeRepository.findById(1).orElse(null);

        TypeConge typeConge = typeCongeRepository.findById(idType).orElse(null);
        if(file!=null){
            byte[] bytes = file.getBytes();
            conge.setPieceJointe(bytes);
        }

        conge.setTypeConge(typeConge);
        conge.setDemandeur(e);
        conge.setValidateur(e.getSuperviseur());


        if(typeConge.getNbrJours()==1){
            conge.setDateFin(conge.getDateDebut().plusDays(1));
            conge.setDuree(1);
        }
        else if(typeConge.getNbrJours()==62){
            conge.setDateFin(conge.getDateDebut().plusMonths(2));
            long jours = ChronoUnit.DAYS.between(conge.getDateDebut(), conge.getDateDebut().plusMonths(2));
            conge.setDuree(jours);
        }
        else if (typeConge.getNbrJours()==0.083) {
            conge.setDateFin(conge.getDateDebut().plusHours(2));
            conge.setDuree(0.083F);
        }
        else{
            LocalDate dateDebut = conge.getDateDebut().toLocalDate();
            LocalDate dateFin = conge.getDateFin().toLocalDate();

            long jours = ChronoUnit.DAYS.between(dateDebut, dateFin);

            conge.setDuree(jours);
        }


        congeRepository.save(conge);

        String to = e.getSuperviseur().getEmail();
        String subject = "Demande de congé";
        String body = "Bonjour" + e.getSuperviseur().getNom() + " " + ",\n" +
                "\n" + "J'espère que ce message vous trouve bien" + "Je tenais à vous informer qu'un de nos employés," +  e.getNom() +
                "\n" +
                 " , a récemment soumis une demande de congé. Je souhaite attirer votre attention sur les détails de cette demande. \n" +
                "\n" +
                "Voici les informations relatives à la demande de conge d'" +   e.getNom()  +":"+ " Nom de l'employé  \n" + e.getNom() + "\n" +
                "Date de soumission : \n"+  conge.getDateDemande()+ "\n"+ "Période de congé demandée: \n" + "de \n"+   conge.getDateDebut()+ "\n"+
                "au \n"+   conge.getDateFin()+ "\n"+ "Conformément à notre processus de gestion des stages, je vous invite à examiner attentivement cette demande et à prendre les mesures nécessaires. \n" +
                "\n" ;
        sendEmail(to, subject , body);

    }

    @Override
    public Conge updateConge(Conge conge , Integer idType){
        TypeConge typeConge = typeCongeRepository.findById(idType).orElse(null);
        conge.setTypeConge(typeConge);

        return congeRepository.save(conge);
    }

    @Override
    public List<Conge> getAllConge(){
        List<Conge> conges = new ArrayList<>();
        congeRepository.findAll().forEach(conges::add);
        return conges;
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


        String to = e.getSuperviseur().getEmail();
        String subject = "Annulation de la demande de congé de l'employé " + e.getNom();
        String body = "Bonjour" + e.getSuperviseur().getNom()  + ",\n" +
                "\n" + "J'espère que vous vous portez bien. Nous souhaitons vous informer qu'une mise à jour importante concerne l'employé " + e.getNom() +"`\n"+" et sa demande de congé." +
                "\n" +
                "En effet, nous avons été notifiés que Mohamed a décidé d'annuler sa demande de congé initialement soumise le \n" + cong.getDateDemande()+ "."+
                "\n" +
                "Par conséquent, les dates de congé prévues du \n " + cong.getDateDebut()+ " au \n" +cong.getDateFin()+  "ne seront plus valides." +
                "\n"+
                "Nous comprenons que cela peut entraîner des ajustements dans la planification des ressources, " +
                "et nous vous prions de bien vouloir prendre en compte cette modification dans l'organisation du travail de l'équipe. \n" +
                "\n" +
                "Nous apprécions grandement votre compréhension et votre gestion efficace de cette situation. Votre professionnalisme contribue à maintenir un environnement de travail harmonieux.";
        sendEmail(to, subject , body);

    }


    public List<Conge> estEnConge(Integer idemploye, LocalDateTime dateDebut, LocalDateTime dateFin) {
        Employee employe = employeeRepository.findById(idemploye).orElse(null);
        List<Conge> congesPendantPeriode = congeRepository.findByDemandeurAndDateDebutIsLessThanEqualAndDateFinIsGreaterThanEqual(employe, dateFin, dateDebut);
        return congesPendantPeriode;
    }


    @Override
    public void delegationDeRole(Integer idsup, Integer idEmpl) {
        Employee superv = employeeRepository.findById(idsup).orElse(null);
        Employee emp = employeeRepository.findById(idEmpl).orElse(null);

        if (superv == null || emp == null) {
            // Gérer le cas où le superviseur ou l'employé n'est pas trouvé dans le référentiel.
            return;
        }

        Role superviseurRole = roleRepository.findByNomRole("superviseur");
        Conge cSuperv = congeRepository.findFirstByDemandeur(superv);

        if (cSuperv != null && cSuperv.getDateDebut().isBefore(LocalDateTime.now()) && cSuperv.getDateFin().isAfter(LocalDateTime.now())) {

                // Vérifier si l'employé n'est pas en congé pendant la période du superviseur.
                List<Conge> congesEmployeePendantPeriodeSuperviseur = estEnConge(idEmpl, cSuperv.getDateDebut(), cSuperv.getDateFin());

                if (congesEmployeePendantPeriodeSuperviseur.isEmpty() && superv.getRole().equals(superviseurRole)) {
                    // Si l'employé a le rôle de superviseur et n'est pas en congé pendant la période du superviseur,
                    // nous pouvons effectuer la délégation manuelle.
                    emp.setRole(superviseurRole);
                    cSuperv.setIdDelegue(idEmpl);
                    employeeRepository.save(emp);
                    congeRepository.save(cSuperv);
                } else {
                    System.out.println("L'employé est également en congé pendant la période du superviseur.");
                }
        }
        else {
                System.out.println("Le superviseur n'est pas en congé actuellement.");
        }
    }


    //@Scheduled(fixedDelay = 60000)
    @Override
    public void retablirRoleDeleg(){
        // Récupérer tous les congés actifs pour les superviseurs
        List<Conge> congesSuperviseurs = congeRepository.findAllByDateFinLessThan(LocalDateTime.now());

        for (Conge conge : congesSuperviseurs) {
            if (conge.getIdDelegue() != null) {
                // Si l'employé est délégué pendant la période de congé du superviseur, vérifier si la période de congé du superviseur est terminée
                Employee empDelegue = employeeRepository.findById(conge.getIdDelegue()).orElse(null);

                if (empDelegue != null) {
                    Conge cSuperv = congeRepository.findFirstByDemandeur(empDelegue);
                    if (cSuperv != null && cSuperv.getDateDebut().isBefore(LocalDateTime.now()) && cSuperv.getDateFin().isBefore(LocalDateTime.now())) {
                        // La période de congé du superviseur est terminée, rétablir le rôle de l'employé délégué à "employee"
                        Role roleEmploye = roleRepository.findByNomRole("employee");
                        empDelegue.setRole(roleEmploye);
                        employeeRepository.save(empDelegue);
                    }
                }
            }
        }
    }



    @Override
    public ResponseEntity<byte[]> generatePdf(int idemp) throws  IOException, DocumentException {

        Employee employee = employeeRepository.findById(idemp).orElse(null);
        List<Conge> conges = congeRepository.findByDemandeur(employee);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        // Create a new PDF document
        Document document = new Document();

        // Create a file on the server to write the PDF to
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



        // Read the PDF file into a byte array
        byte[] contents = Files.readAllBytes(file.toPath());

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("attachment", "Historique.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        // Create the ResponseEntity with the PDF contents and headers
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);

        return response;
    }


}






