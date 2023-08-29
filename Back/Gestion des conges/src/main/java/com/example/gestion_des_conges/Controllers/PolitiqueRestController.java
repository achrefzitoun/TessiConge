package com.example.gestion_des_conges.Controllers;

import com.example.gestion_des_conges.Entities.Employee;
import com.example.gestion_des_conges.Entities.MotifRefus;
import com.example.gestion_des_conges.Entities.Politique;
import com.example.gestion_des_conges.Services.EmplyeeServices;
import com.example.gestion_des_conges.Services.PolitiqueServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/politique")
@CrossOrigin(origins = "*")
public class PolitiqueRestController {

    private final PolitiqueServices politiqueServices;
    private final EmplyeeServices emplyeeServices;


    @PostMapping("/addPolitique")
    public Politique addMotif(@RequestBody Politique politique) {
        return politiqueServices.addPolitique(politique);
    }

    @GetMapping("/allPolitiques")
    List<Politique> getAllMotif(){
        return politiqueServices.retrieveAllPolitique();
    }

    @PutMapping("/updatePolitique")
    Politique updateSpeaker(@RequestBody Politique politique) {
        return politiqueServices.updatePolitique(politique);
    }

    @GetMapping("/getPolitique")
    Politique getSpeaker(@RequestParam("id") Integer idPolitique) {
        return politiqueServices.retrievePolitique(idPolitique);
    }

    @DeleteMapping("/deletePolitique")
    void deleteSpeaker(@RequestParam("id") Integer idPolitique) {
        politiqueServices.deletePolitique(idPolitique);
    }

    @GetMapping("/employeByPolitique")
    List<Employee> getEmployeByPolitique(@RequestParam("id") Integer idPolitique){
        return emplyeeServices.getEmployeeByPolitique(idPolitique);
    }
}
