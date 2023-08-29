package com.example.gestion_des_conges.Controllers;

import com.example.gestion_des_conges.Entities.*;
import com.example.gestion_des_conges.Services.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
@RestController
@RequiredArgsConstructor
@RequestMapping("/conge")
@CrossOrigin(origins = "*")
public class GestionDeCongeController {

    private final IRoleServices roleServices;

    private final ICongeServices congeServices;

    private final IEmplyeeServices emplyeeServices;

    private final IJourFerieServices jourFerieServices;

    private final IPolitiqueServices politiqueServices;

    private final IMotifRefusServices motifRefusServices;

    private final ITypeCongeServices typeCongeServices;

    @GetMapping("/getTypeCongebyNature/{natureType}")
    public List<TypeConge> getTypeCongebyNature(@PathVariable("natureType") NatureType natureType) {
        return typeCongeServices.getTypeCongebyNature(natureType);
    }

    @PostMapping("/NewRole")
    public Role addRole(@RequestBody Role role) {
        return roleServices.addRole(role);
    }

    @GetMapping("/retrieveAllTypeConge")
    public List<TypeConge> retrieveAllTypeConge() {
        return typeCongeServices.retrieveAllTypeConge();
    }


        @PutMapping("/UpdateRole")
    public Role updateRole(@RequestBody Role role) {
        return roleServices.updateRole(role);
    }

    @DeleteMapping("/DeleteRole/{id}")
    public void deleteRole(@PathVariable("id") int id) {
        roleServices.deleteRole(id);
    }

    @GetMapping("/ViewRole/{id}")
    public Role retrieveRole(@PathVariable("id") int id) {
        return roleServices.retrieveRole(id);
    }

    @GetMapping("/ViewRoles")
    public List<Role> retrieveAllRoles() {
        return roleServices.retrieveAllRoles();
    }


    @PutMapping("/reponseconge")
    public ResponseEntity<?> reponseConge(@RequestParam("idConge") int idConge, @RequestParam("etat") String etat/*, Principal principal*/, @RequestBody @Nullable MotifRefus motifRefus) throws MessagingException, IOException {

        return congeServices.reponseConge(idConge,Etat.valueOf(etat),motifRefus);
    }

    @GetMapping("/exportCongeExcel")
    public ResponseEntity<?> exportCongeExcel(@RequestParam("dateDebut") String dateD, @RequestParam("dateFin") String dateF) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime dateDebut = LocalDateTime.parse(dateD, formatter);
        LocalDateTime dateFin = LocalDateTime.parse(dateF, formatter);

        return congeServices.exportCongeExcel(dateDebut, dateFin);
    }

    @PutMapping(value="/affectationConge", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> affectationConge(@RequestParam("conge") String congeJon, @RequestParam("idDemandeur") String idDemandeur
            , @RequestParam("idTypeConge") String idTypeConge
            , @RequestParam(value = "file",required = false) @Nullable MultipartFile file) throws MessagingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Conge conge = objectMapper.readValue(congeJon,Conge.class);

        int idDemandeurS = Integer.parseInt(idDemandeur);
        int idTypeCongeS = Integer.parseInt(idTypeConge);

        if(file!=null){
            byte[] bytes = file.getBytes();
            conge.setPieceJointe(bytes);
        }
        return congeServices.affectationConge(conge,idDemandeurS,idTypeCongeS);
    }

    @GetMapping("/getHolidaysForCountry/{countryCode}/{year}")
    public Set<Holiday> getHolidaysForCountry(@PathVariable("countryCode") String countryCode, @PathVariable("year") int year) {
        HolidayManager holidayManager = HolidayManager.getInstance(HolidayCalendar.valueOf(countryCode));
        return holidayManager.getHolidays(year);
    }

    @PutMapping("/JourFerie")
    public void miseAJourJourFerie() throws IOException {
        jourFerieServices.miseAJourJourFerie();
    }

    @GetMapping("/GetAllEmplyees")
    public List<Employee> getAllEmployees(){
        return emplyeeServices.retrieveAllEmployee();
    }

    @GetMapping("/getEmployeesByRole/{id}")
    public List<Employee> retrieveEmployeesByRole(@PathVariable("id") int id){
        return this.roleServices.retrieveEmployeesByRole(id);
    }

    @GetMapping("/retrieveAllMotifRefus")
    public List<MotifRefus> retrieveAllMotifRefus() {
        return motifRefusServices.retrieveAllMotifRefus();
    }

    @PutMapping("/getAutorisation")
    public ResponseEntity<?> getAutorisation(@RequestBody Autorisation autorisation){
        System.out.println("test");
        return congeServices.getAutorisation(autorisation);
    }

    @GetMapping("/getJourFerieByPolitique")
    public List<JourFerie> getJourFerieByPolitique(@RequestParam("idPolitique") int idPolitique){
        return jourFerieServices.getJourFerieByPolitique(idPolitique);
    }

    @GetMapping("/retrievePolitique")
    public Politique retrievePolitique(@RequestParam("idPolitique") int id) {
        return politiqueServices.retrievePolitique(id);
    }

    @GetMapping("/retrieveAllPoitiquesbyJourFerie")
    public List<Politique> retrieveAllPoitiquesbyJourFerie(@RequestParam("idJourFerie") int idJourFerie) {
        return  politiqueServices.retrieveAllPoitiquesbyJourFerie(idJourFerie);
    }

    @GetMapping("/getJourFerieByDate")
    public JourFerie getJourFerieByDate(@RequestParam("date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime datee = LocalDateTime.parse(date, formatter);
        return jourFerieServices.getJourFerieByDate(datee);
    }

    @PutMapping("/updateJourFerie")
    public JourFerie updateJourFerie(@RequestBody JourFerie jourFerie){
        return jourFerieServices.updateJourFerie(jourFerie);
    }

    @PutMapping("/assignJourFeriePolitiques")
    public void assignJourFeriePolitiques(@RequestParam("idPolitique") int idPolitique, @RequestParam("idJourFerie") int idJourFerie){
        jourFerieServices.assignJourFeriePolitiques(idPolitique, idJourFerie);
    }

    @PostMapping("/addJourFerie")
    public JourFerie addJourFerie(@RequestBody JourFerie jourFerie) {
        return jourFerieServices.addJourFerie(jourFerie);
    }

    @GetMapping("/retrieveAllPolitique")
    public List<Politique> retrieveAllPolitique() {
        return politiqueServices.retrieveAllPolitique();
    }

    @GetMapping("/calculSoldeConge/{idConge}")
    public float calculSoldeConge(@PathVariable("idConge") int idConge){
        return congeServices.calculSoldeConge(idConge);
    }

    @PostMapping("/addTypeConge")
    public TypeConge addTypeConge(@RequestBody Map<String, Object> requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();

        TypeConge typeConge = objectMapper.convertValue(requestBody.get("typeConge"), TypeConge.class);
        List<Politique> politiques = objectMapper.convertValue(requestBody.get("politiques"), new TypeReference<List<Politique>>() {});

        return typeCongeServices.addTypeConge(typeConge, politiques);
    }

    @PutMapping("/deleteTypeConge/{idTypeConge}")
    public void deleteTypeConge(@PathVariable("idTypeConge")int idTypeConge) {
        typeCongeServices.deleteTypeConge(idTypeConge);
    }

    @GetMapping("/retrieveAllPolitiquesByType/{idTypeConge}")
    public List<Politique> retrieveAllPolitiquesByType(@PathVariable("idTypeConge") int idTypeConge){
        return politiqueServices.retrieveAllPolitiquesByType(idTypeConge);
    }


    @PutMapping("/editTypeConge")
    public TypeConge editTypeConge(@RequestBody Map<String, Object> requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();

        TypeConge typeConge = objectMapper.convertValue(requestBody.get("typeConge"), TypeConge.class);
        List<Politique> politiques = objectMapper.convertValue(requestBody.get("politiques"), new TypeReference<List<Politique>>() {});

        return typeCongeServices.updateTypeConge(typeConge, politiques);
    }



}
