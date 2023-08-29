package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.Politique;
import com.example.gestion_des_conges.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolitiqueServices implements IPolitiqueServices {

    private final IPolitiqueRepository politiqueRepository;


    @Override
    public Politique addPolitique(Politique politique) {
        return politiqueRepository.save(politique);
    }

    @Override
    public Politique updatePolitique(Politique politique) {
        return politiqueRepository.save(politique);
    }

    @Override
    public void deletePolitique(int id) {
        politiqueRepository.deleteById(id);
    }

    @Override
    public Politique retrievePolitique(int id) {
        return politiqueRepository.findById(id).orElse(null);
    }

    @Override
    public List<Politique> retrieveAllPolitique() {
        List<Politique> politiques = new ArrayList<>();
        politiqueRepository.findAll().forEach(politiques::add);
        return politiques;
    }



}
