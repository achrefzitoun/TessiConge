package com.example.gestion_des_conges.Controllers;

import com.example.gestion_des_conges.Entities.Conge;
import com.example.gestion_des_conges.Entities.Role;
import com.example.gestion_des_conges.Entities.TypeConge;
import com.example.gestion_des_conges.Services.IDemandeService;
import com.example.gestion_des_conges.Services.IRoleServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demande")
public class DemandeRestController {
    private final IDemandeService demandeService;

    @PostMapping("/addType")
    public TypeConge addRole(@RequestBody TypeConge type) {
        return demandeService.addTypeConge(type);
    }

    @PutMapping(value = "/addAndAssignType/{idtype}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void addConge(Conge conge, @RequestParam("file") MultipartFile file, @PathVariable("idtype") Integer id  , @RequestParam("s") String s , @RequestParam("f") String f ) throws IOException, MessagingException {

        //Conge cong = new ObjectMapper().readValue(conge, Conge.class);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
        System.out.println(dateTime);
        LocalDateTime dateTime2 = LocalDateTime.parse(f, formatter);
        conge.setDateDebut(dateTime);
        conge.setDateFin(dateTime2);

         demandeService.addCongeAndAssignCongeToType(conge,file,id);

    }

    @PutMapping(value = "/updateConge/{idtype}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Conge updateConge(Conge conge, @RequestParam("file") MultipartFile file, @PathVariable("idtype") Integer id  , @RequestParam("s") String s , @RequestParam("f") String f  ) throws IOException {

        //Conge cong = new ObjectMapper().readValue(conge, Conge.class);

        conge.setPieceJointe(file.getBytes());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(f, formatter);
        conge.setDateDebut(dateTime);
        conge.setDateFin(dateTime2);

        return demandeService.updateConge(conge,id);
    }

    @GetMapping("/getConge/{id}")
    Conge getConge(@PathVariable("id") Integer id){

        return demandeService.retrieveConge(id);
    }

    @GetMapping("/allConge")
    List<Conge> getAllConge(){

        return demandeService.getAllConge();
    }

    @PutMapping("/annulCong/{id}")
    void annulConge(@PathVariable("id") Integer id) throws MessagingException {
        demandeService.annulConge(id);
    }

   /* @GetMapping("/enConge")
    boolean estEnConge(@RequestParam("id") Integer id,
                       @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate,
                       @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate) {
        return demandeService.estEnConge(id, endDate, startDate);
    }*/


    @PutMapping("/delegation/{idsup}/{idEmpl}")
    void delegationSup(@PathVariable("idsup") Integer idsup , @PathVariable("idEmpl") Integer idEmpl)  {
        demandeService.delegationDeRole(idsup,idEmpl);
    }

    @GetMapping("/generatepdf/{id}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable("id") Integer id) throws  IOException, DocumentException {
        return demandeService.generatePdf(id);
    }

}
