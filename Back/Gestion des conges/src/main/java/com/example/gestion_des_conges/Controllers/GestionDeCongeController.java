package com.example.gestion_des_conges.Controllers;

import com.example.gestion_des_conges.Configurations.Mail;
import com.example.gestion_des_conges.Entities.*;
import com.example.gestion_des_conges.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conge")
public class GestionDeCongeController {

    private final IRoleServices roleServices;

    private final ICongeServices congeServices;

    private final IEmplyeeServices emplyeeServices;

    private final IJourFerieServices jourFerieServices;

    private final IPolitiqueServices politiqueServices;

    private final IMotifRefusServices motifRefusServices;

    private final ITypeCongeServices typeCongeServices;

    @PostMapping("/NewRole")
    public Role addRole(@RequestBody Role role) {
        return roleServices.addRole(role);
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


    @PutMapping("/reponseconge/{idConge}/{etat}")
    public ResponseEntity<String> reponseConge(@PathVariable("idConge") int idConge, @PathVariable("etat") Etat etat/*, Principal principal*/, @RequestBody @Nullable MotifRefus motifRefus) throws MessagingException, IOException {
        return congeServices.reponseConge(idConge,etat,motifRefus);
    }

    @GetMapping("/exportCongeExcel")
    public ResponseEntity<String> exportCongeExcel(@RequestParam("dateDebut") String dateD, @RequestParam("dateFin") String dateF) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        LocalDateTime dateDebut = LocalDateTime.parse(dateD, formatter);
        LocalDateTime dateFin = LocalDateTime.parse(dateF, formatter);

        return congeServices.exportCongeExcel(dateDebut, dateFin);
    }

    @PutMapping("/affectationConge/{idDemandeur}/{idTypeConge}")
    public ResponseEntity<String> affectationConge(@RequestBody Conge conge, @PathVariable("idDemandeur") int idDemandeur,/* Principal principal, */ @PathVariable("idTypeConge") int idTypeConge) throws MessagingException, IOException {
        return congeServices.affectationConge(conge,idDemandeur,idTypeConge);
    }


}
