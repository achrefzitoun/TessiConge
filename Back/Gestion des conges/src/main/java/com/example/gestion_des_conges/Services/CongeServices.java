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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
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
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


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
    public ResponseEntity<String> reponseConge(int idConge, Etat etat/*, Principal principal*/, MotifRefus motifRefus) throws MessagingException {
        Conge conge = congeRepository.findById(idConge).orElse(null);

        if(conge!=null){
            Employee demandeur = employeeRepository.findById(conge.getDemandeur().getIdEmp()).orElse(null);

            //Employee validateur = employeeRepository.findEmployeeByUsername(principal.getName());
            Employee validateur = employeeRepository.findById(1).orElse(null);

            if(conge.getEtat().toString().equals(Etat.Annule.toString())){
                return new ResponseEntity<String>("Congé est déja annulé .", HttpStatus.BAD_REQUEST);
            }
            if(demandeur == null || validateur == null){
                return new ResponseEntity<String>("Mauvaise demande.", HttpStatus.BAD_REQUEST);
            }
            if(demandeur.getSuperviseur()!=validateur){
                return new ResponseEntity<String>("L'employée n'est pas dans votre entité.", HttpStatus.BAD_REQUEST);
            }
            if (!validateur.getRole().getNomRole().equals("admin") && !validateur.getRole().getNomRole().equals("superviseur")) {
                return new ResponseEntity<String>("Accès refusé. Vous n'avez pas les permissions nécessaires.", HttpStatus.FORBIDDEN);
            }

            if(etat==null || etat.toString().equals("En_Attente")){
                return new ResponseEntity<String>("Donner la nouvelle état du congé.", HttpStatus.BAD_REQUEST);
            }

            if(etat.toString().equals("Refuse")){
                if(motifRefus==null){
                    return new ResponseEntity<String>("Réponse non valide. Vous n'avez pas donner le motif de refus.", HttpStatus.BAD_REQUEST);
                }
                if (motifRefus.getIdMotif() != null) {
                    if(motifRefusRepository.findById(motifRefus.getIdMotif()).isPresent()){
                        motifRefus= motifRefusRepository.findById(motifRefus.getIdMotif()).get();
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
                    return new ResponseEntity<String>("Donner une description de refus.", HttpStatus.BAD_REQUEST);
                }
                motifRefusRepository.save(motifRefus);
                conge.setMotifRefus(motifRefus);
            }

            if (motifRefus.getTypeMotif() == null ) {
                motifRefus.setTypeMotif(TypeMotif.Autre);
            }
            conge.setDateValidation(LocalDateTime.now());
            conge.setEtat(etat);
            conge.setValidateur(validateur);

            congeRepository.save(conge);

            ///////////////////////////////////// Mail validation (Mail Validation)

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
                sendEmail(mail, "EmailValidation.html");
            }
            else if(etat.toString().equals(Etat.Refuse.toString())){
                model.put("motifrefus",motifRefus.getDescription());
                sendEmail(mail, "EmailRefus.html");
            }

            return new ResponseEntity<String>("Conge du : " + conge.getDemandeur().getNom() + " " + conge.getDemandeur().getPrenom()
                    +" est : " + etat.toString() , HttpStatus.OK);
        }

        else {
            return new ResponseEntity<String>("Conge indisponnible.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> affectationConge(Conge conge, int idDemandeur,/* Principal principal, */int idTypeConge) throws MessagingException, IOException {
        //Employee validateur = employeeRepository.findEmployeeByUsername(principal.getName());
        Employee validateur = employeeRepository.findById(1).orElse(null);
        Employee demandeur = employeeRepository.findById(idDemandeur).orElse(null);
        TypeConge typeConge = typeCongeRepository.findById(idTypeConge).orElse(null);

        if (!validateur.getRole().getNomRole().equals("admin") && !validateur.getRole().getNomRole().equals("superviseur")) {
            return new ResponseEntity<String>("Accès refusé. Vous n'avez pas les permissions nécessaires.", HttpStatus.FORBIDDEN);
        }
        if(conge==null){
            return new ResponseEntity<String>("Mauvaise demande.", HttpStatus.BAD_REQUEST);
        }
        if(demandeur== null){
            return new ResponseEntity<String>("Donner un demandeur du congé.", HttpStatus.BAD_REQUEST);
        }
        if(typeConge== null){
            return new ResponseEntity<String>("Donner le type de congé.", HttpStatus.BAD_REQUEST);
        }
        if(!demandeur.getPolitique().getTypeConge().contains(typeConge)){
            return new ResponseEntity<String>("Ce employée ne peux pas bénéficer de ce type de congé.", HttpStatus.BAD_REQUEST);
        }

        conge.setEtat(Etat.En_Attente);
        conge.setDemandeur(demandeur);
        conge.setTypeConge(typeConge);
        congeRepository.save(conge);

        ///////////////////////////////////////////////// Mail confirmation au demandeur (Mail Confirmation)

        Mail maildemandeur = new Mail();

        maildemandeur.setFrom("achref.zitoun@esprit.tn");
        maildemandeur.setMailTo(demandeur.getEmail());
        maildemandeur.setSubject("Demande de congé");

        Map<String, Object> modeldemandeur = new HashMap<String, Object>();

        modeldemandeur.put("demandeur", demandeur.getNom() + " " + demandeur.getPrenom());
        modeldemandeur.put("duree", Duration.between(conge.getDateDebut(), conge.getDateFin()).toDays());
        modeldemandeur.put("datedebut", conge.getDateDebut());
        modeldemandeur.put("datefin", conge.getDateFin());
        maildemandeur.setProps(modeldemandeur);

        if(demandeur.getEmail()!=null){
            sendEmail(maildemandeur, "EmailConfirmation.html");
        }

        ///////////////////////////////////////////////// Mail information au validateur (Mail Information)

        Mail mailvalidateur = new Mail();

        mailvalidateur.setFrom("achref.zitoun@esprit.tn");
        mailvalidateur.setMailTo(demandeur.getSuperviseur().getEmail());
        mailvalidateur.setSubject("Demande de congé : " + demandeur.getNom() + " " + demandeur.getPrenom());

        Map<String, Object> modelvalidateur = new HashMap<String, Object>();

        modelvalidateur.put("superviseur", demandeur.getSuperviseur().getNom() + " " + demandeur.getSuperviseur().getPrenom());
        modelvalidateur.put("demandeur", demandeur.getNom() + " " + demandeur.getPrenom());
        modelvalidateur.put("duree", Duration.between(conge.getDateDebut(), conge.getDateFin()).toDays());
        modelvalidateur.put("datedebut", conge.getDateDebut());
        modelvalidateur.put("datefin", conge.getDateFin());
        mailvalidateur.setProps(modelvalidateur);

        if(demandeur.getSuperviseur().getEmail()!=null) {
            sendEmail(mailvalidateur, "EmailInformation.html");
        }

        return new ResponseEntity<String>("Conge du : " + conge.getDemandeur().getNom() + " " + conge.getDemandeur().getPrenom()
                +" est enregistré " , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> exportCongeExcel(LocalDateTime dateDebut, LocalDateTime dateFin) {
        List<Conge> conges = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet;

        String fileName;

        if(dateDebut!=null && dateFin!=null){
            conges.addAll(congeRepository.findByDateDebutBetweenAndEtat(dateDebut, dateFin, Etat.Accepte));
            sheet = workbook.createSheet("Congé accepter" + dateDebut.getMonth() + "-" + dateDebut.getDayOfMonth() + "_" + dateFin.getMonth() + "-" + dateFin.getDayOfMonth());
            fileName = "D:\\Stage 2 Esprit\\Back\\Gestion des conges\\src\\main\\resources\\files\\Congé_accepter" +  dateDebut.getMonth() + "-" + dateDebut.getDayOfMonth() + "_" + dateFin.getMonth() + "-" + dateFin.getDayOfMonth() + ".xlsx";
        }
        else {
            conges.addAll(congeRepository.findByEtat(Etat.Accepte));
            sheet = workbook.createSheet("Tous les congé accepter");
            fileName = "D:\\Stage 2 Esprit\\Back\\Gestion des conges\\src\\main\\resources\\files\\Tous_les_congés_accepter"+".xlsx";
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

        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        } catch (IOException e) {
             e.printStackTrace();
        }

        return new ResponseEntity<String>("Le fichier " , HttpStatus.OK);
    }

}
