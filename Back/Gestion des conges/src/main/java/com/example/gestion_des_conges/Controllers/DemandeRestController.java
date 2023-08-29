package com.example.gestion_des_conges.Controllers;

import com.example.gestion_des_conges.Entities.*;
import com.example.gestion_des_conges.Services.IDemandeService;
import com.example.gestion_des_conges.Services.IEmplyeeServices;
import com.example.gestion_des_conges.Services.IRoleServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demande")
@CrossOrigin(origins = "*")
public class DemandeRestController {
    private final IDemandeService demandeService;
    private final IEmplyeeServices emplyeeServices ;

    @PostMapping("/addType")
    public TypeConge addRole(@RequestBody TypeConge type) {
        return demandeService.addTypeConge(type);
    }

    @PutMapping(value = "/addAndAssignType", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     public ResponseEntity<?> addConge(@RequestParam("conge") String congeJson, @RequestParam(value = "file", required = false) @Nullable MultipartFile file, @RequestParam("idtype") Integer idtype, @RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate) throws IOException, MessagingException {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Conge conge = objectMapper.readValue(congeJson, Conge.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(endDate, formatter);

        conge.setDateDebut(dateTime);
        conge.setDateFin(dateTime2);

         return demandeService.addCongeAndAssignCongeToType(conge, file, idtype);

    }



    @GetMapping("/durationN")
    public ResponseEntity<Float> getDurationN(@RequestParam("startDate") String startDate,
                                             @RequestParam("endDate") String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        LocalDateTime dateDebut = LocalDateTime.parse(startDate, formatter);
        LocalDateTime dateFin = LocalDateTime.parse(endDate, formatter);
        System.out.println(dateDebut);
        float duration = demandeService.calculDurationNormale(dateDebut, dateFin);
        return ResponseEntity.ok(duration);
    }

    @GetMapping("/durationS")
    public ResponseEntity<Float> getDurationS(@RequestParam("startDate") String startDate,

                                             @RequestParam("idtype") Integer idType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateDebut = LocalDateTime.parse(startDate, formatter);

        float duration = demandeService.calculDurationSpecaile(dateDebut, idType);
        return ResponseEntity.ok(duration);

    }

    @PutMapping(value = "/updateConge" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Conge updateConge(@RequestParam("conge") String congeJson, @RequestParam(value = "file", required = false) @Nullable MultipartFile file, @RequestParam("idtype") Integer idtype  ,@RequestParam("startDate") String startDate , @RequestParam("endDate") String endDate ) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Conge conge = objectMapper.readValue(congeJson, Conge.class);

        //conge.setPieceJointe(file.getBytes());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(endDate, formatter);
        conge.setDateDebut(dateTime);
        conge.setDateFin(dateTime2);

        return demandeService.updateConge(conge,idtype);
    }

    @GetMapping("/getConge")
    Conge getConge(@RequestParam("id") Integer id){
        return demandeService.retrieveConge(id);
    }

    @GetMapping("/allConge")
    List<Conge> getAllConge(){
        return demandeService.getAllConge();
    }

    @GetMapping("/allCongeEmployee")
    List<Conge> getAllCongeByEmployee(){
        return demandeService.getListCongeByEmploye();
    }

    @GetMapping("/CongeEnAttente")
    List<Conge> getAllCongeEnAttente(){
        return demandeService.getAllCongeEnAttente();
    }

    @GetMapping("/allTypeConge")
    List<TypeConge> getAllTypeConge(){
        return demandeService.getAllTypes();
    }

    @PutMapping("/annulCong")
    void annulConge(@RequestParam("id") Integer id) throws MessagingException {
        demandeService.annulConge(id);
    }

   /* @GetMapping("/enConge")
    boolean estEnConge(@RequestParam("id") Integer id,
                       @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate,
                       @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate) {
        return demandeService.estEnConge(id, endDate, startDate);
    }*/


    @PutMapping("/delegation")
    public ResponseEntity<?> delegationSup(@RequestParam("idEmpl") Integer idEmpl ,@RequestParam("startDate") String startDate , @RequestParam("endDate") String endDate)  {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(endDate, formatter);

        return demandeService.delegationDeRole(idEmpl,dateTime,dateTime2);
    }

    @GetMapping("/generatepdf")
    public ResponseEntity<byte[]> generatePdf(/*@PathVariable("id") Integer id*/) throws  IOException, DocumentException {
        return demandeService.generatePdf();
    }

    @GetMapping("/getTypeCongebyNature/{natureType}")
    public List<TypeConge> getTypeCongebyNature(@PathVariable("natureType") NatureType natureType) {
        return demandeService.getTypeCongebyNature(natureType);
    }

    @GetMapping("/getemployeedelegDev")
    public List<Employee> getEmployeesDeleg() {
        return demandeService.getEmployeeDev();
    }

    @GetMapping("/getemployeedelegContr")
    public List<Employee> getEmployeesDelegControle() {
        return demandeService.getEmployeeControle();
    }


    @GetMapping("/exportExcel")
    public ResponseEntity<byte[]> exportCongeExcel() {
        return demandeService.exportCongeExcel();
    }

    @GetMapping("/getAllCongesAccep")
    public List<Conge> getAllCongesAccept() {
        return demandeService.touteLaListeDesConges();
    }


    @GetMapping("/getEmplyesContainDate")
    public List<Employee> getEmployesContainDate(@RequestParam("startDate") String startDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(startDate, formatter);
        System.out.println(dateTime);
        return  demandeService.getEmployeesWithLeaveContainingDate(dateTime);
    }

    @GetMapping("/getListCongeDesSuperviseurs")
    public List<Conge> getListCongeDesSuperviseurs (){
        return demandeService.getListCongeDesSuperviseurs();
    }


    @GetMapping("/getDelegueEmployeeName")
    public ResponseEntity<String> getDelegueEmployeeName(@RequestParam("idConge") Integer idConge) {
        String delegueEmployeeName = demandeService.getDelegueEmployeeName(idConge);

        if (delegueEmployeeName != null) {
            return ResponseEntity.ok(delegueEmployeeName);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
