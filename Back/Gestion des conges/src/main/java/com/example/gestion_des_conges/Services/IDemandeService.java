package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.Conge;
import com.example.gestion_des_conges.Entities.Employee;
import com.example.gestion_des_conges.Entities.TypeConge;
import com.itextpdf.text.DocumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IDemandeService {

    TypeConge addTypeConge(TypeConge typeConge);

    void addCongeAndAssignCongeToType (Conge conge , MultipartFile file ,  Integer idType) throws IOException, MessagingException;

    public Conge updateConge(Conge conge , Integer idType);
    public List<Conge> getAllConge();
    public Conge retrieveConge(Integer idConge);
    public void annulConge(Integer idConge) throws MessagingException;

    public void sendEmail(String to, String subject, String body) throws MessagingException;

    public void delegationDeRole(Integer idsup, Integer idEmpl);
    public List<Conge> estEnConge(Integer idemploy, LocalDateTime dateFin , LocalDateTime dateDebut);

    public void retablirRoleDeleg();

    public ResponseEntity<byte[]> generatePdf(int idemp) throws  IOException, DocumentException;

}
