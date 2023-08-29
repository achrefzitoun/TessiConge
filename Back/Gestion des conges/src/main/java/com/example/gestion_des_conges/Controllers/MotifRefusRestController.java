package com.example.gestion_des_conges.Controllers;

import com.example.gestion_des_conges.Entities.Conge;
import com.example.gestion_des_conges.Entities.MotifRefus;
import com.example.gestion_des_conges.Entities.TypeConge;
import com.example.gestion_des_conges.Services.IDemandeService;
import com.example.gestion_des_conges.Services.IMotifRefusServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/motif")
@CrossOrigin(origins = "*")
public class MotifRefusRestController {
    private final IMotifRefusServices motifRefusServices;

    @PostMapping("/addMotif")
    public MotifRefus addMotif(@RequestBody MotifRefus motif) {
        return motifRefusServices.addMotifRefus(motif);
    }

    @GetMapping("/allMotifs")
    List<MotifRefus> getAllMotif(){
        return motifRefusServices.getAllMotifRefus();
    }

    @PutMapping("/updateMotif")
    MotifRefus updateSpeaker(@RequestBody MotifRefus motifRefus) {
        return motifRefusServices.updateMotifRefus(motifRefus);
    }

    @GetMapping("/getMotif")
    MotifRefus getSpeaker(@RequestParam("id") Integer idMotifRefus) {
        return motifRefusServices.retrieveMotifRefus(idMotifRefus);
    }

    @DeleteMapping("/deleteMotif")
    void deleteSpeaker(@RequestParam("id") Integer idMotifRefus) {
        motifRefusServices.deleteMotifRefus(idMotifRefus);
    }


}
