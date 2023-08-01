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
}
