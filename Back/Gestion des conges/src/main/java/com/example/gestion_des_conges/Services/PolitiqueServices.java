package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.*;
import com.example.gestion_des_conges.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PolitiqueServices implements IPolitiqueServices {
    @Autowired
    private final ICongeRepository congeRepository;

    @Autowired
    private final IEmployeeRepository employeeRepository;

    @Autowired
    private final IJourFerieRepository jourFerieRepository;

    @Autowired
    private final IMotifRefusRepository motifRefusRepository;

    @Autowired
    private final IPolitiqueRepository politiqueRepository;

    @Autowired
    private final IRoleRepository roleRepository;

    @Autowired
    private final ITypeCongeRepository typeCongeRepository;

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

    @Override
    public List<Politique> retrieveAllPoitiquesbyJourFerie(int idJourFerie) {
        List<Politique> allPolitiques = retrieveAllPolitique();
        return allPolitiques.stream()
                .filter(politique -> politique.getJourFerie()
                        .stream()
                        .anyMatch(jourFerie -> jourFerie.getIdJour() == idJourFerie))
                .collect(Collectors.toList());
    }

    @Override
    public List<Politique> retrieveAllPolitiquesByType(int idTypeConge){
        List<Politique> allPolitiques = retrieveAllPolitique();
        return allPolitiques.stream()
                .filter(politique -> politique.getTypeConge()
                        .stream()
                        .anyMatch(type -> type.getIdTypeConge() == idTypeConge))
                .collect(Collectors.toList());
    }
}
