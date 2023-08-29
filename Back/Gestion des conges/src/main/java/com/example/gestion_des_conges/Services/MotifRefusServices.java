package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.Conge;
import com.example.gestion_des_conges.Entities.MotifRefus;
import com.example.gestion_des_conges.Entities.TypeMotif;
import com.example.gestion_des_conges.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotifRefusServices implements IMotifRefusServices {

    private final IMotifRefusRepository motifRefusRepository;


    @Override
    public MotifRefus addMotifRefus(MotifRefus motifRefus) {
        motifRefus.setTypeMotif(TypeMotif.Statique);
        return motifRefusRepository.save(motifRefus);
    }

    @Override
    public MotifRefus updateMotifRefus(MotifRefus motifRefus) {
        return motifRefusRepository.save(motifRefus);
    }

    @Override
    public void deleteMotifRefus(int id) {
        motifRefusRepository.deleteById(id);
    }

    @Override
    public MotifRefus retrieveMotifRefus(int id) {
        return motifRefusRepository.findById(id).orElse(null);
    }

    @Override
    public List<MotifRefus> retrieveAllMotifRefus() {
        return motifRefusRepository.findByTypeMotif(TypeMotif.Statique);
    }

    @Override
    public MotifRefus retrieveMotifByConge(Conge conge) {
        return conge.getMotifRefus();
    }

    @Override
    public List<MotifRefus> getAllMotifRefus(){
        List<MotifRefus> motifRefuses = new ArrayList<>();
        motifRefusRepository.findAll().forEach(motifRefuses::add);
        return motifRefuses;
    }


}
