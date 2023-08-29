package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Configurations.Mail;
import com.example.gestion_des_conges.Entities.Conge;
import com.example.gestion_des_conges.Entities.Employee;
import com.example.gestion_des_conges.Entities.NatureType;
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

    public void sendEmail(Mail mail, String templateName) throws MessagingException;
    TypeConge addTypeConge(TypeConge typeConge);

    public ResponseEntity<?> addCongeAndAssignCongeToType (Conge conge , MultipartFile file ,  Integer idType) throws IOException, MessagingException;

    public Conge updateConge(Conge conge , Integer idType);
    public List<Conge> getAllConge();
    public List<Conge> getListCongeByEmploye(/*Integer id*/);

    public List<Conge> getAllCongeEnAttente();

    public List<TypeConge> getAllTypes();
    public Conge retrieveConge(Integer idConge);
    public void annulConge(Integer idConge) throws MessagingException;


    public ResponseEntity<?> delegationDeRole(/*Integer idsup, */Integer idEmpl, LocalDateTime dateDebut, LocalDateTime dateFin);
    public List<Conge> estEnConge(Integer idemploy, LocalDateTime dateFin , LocalDateTime dateDebut);

    public void retablirRoleDeleg();

    public ResponseEntity<byte[]> generatePdf(/*int idemp*/) throws  IOException, DocumentException;

    public float calculDurationSpecaile(LocalDateTime startDate, Integer idType);


    public float calculDurationNormale(LocalDateTime startDate, LocalDateTime endDate);

    public List<TypeConge> getTypeCongebyNature(NatureType natureType);

    public ResponseEntity<?> getAutorisation( int idEmployee, float duree);

    public void updateSoldeCongeEmployees();
    public void updateSoldeAutorisationEmployees();

    public List<Employee> getEmployeeDev();

    public List<Employee> getEmployeeControle() ;

    //public ResponseEntity<String> exportCongeExcel();

    public ResponseEntity<byte[]> exportCongeExcel() ;

    public List<Conge> touteLaListeDesConges();

    //public List<Employee> getEmployeesBetweenDates(LocalDateTime d1, LocalDateTime d2);

    public List<Employee> getEmployeesWithLeaveContainingDate(LocalDateTime targetDate);

    public List<Conge> getListCongeDesSuperviseurs ();


    public String getDelegueEmployeeName(Integer idConge);

    }
