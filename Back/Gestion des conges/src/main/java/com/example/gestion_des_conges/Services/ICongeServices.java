package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Configurations.Mail;
import com.example.gestion_des_conges.Entities.*;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public interface ICongeServices {
    public ResponseEntity<?> reponseConge (int idConge, Etat etat, /*Principal principal,*/ MotifRefus motifRefus) throws MessagingException, IOException;
    public ResponseEntity<?> affectationConge (Conge conge, int idDemandeur/*, Principal principal*/, int idTypeConge) throws MessagingException, IOException;

    //Mailling
    public void sendEmail(Mail mail, String templateName) throws MessagingException, IOException;

    //Excel
    public ResponseEntity<?> exportCongeExcel(LocalDateTime DateDebut, LocalDateTime DateFin);

    public ResponseEntity<?> getAutorisation(Autorisation autorisation);

    public boolean checkDisponibilite(Employee employee, LocalDate startDate, LocalDate endDate) ;

    public float calculSoldeConge(int idConge);
}
