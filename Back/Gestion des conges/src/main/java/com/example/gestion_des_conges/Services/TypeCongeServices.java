package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.*;
import com.example.gestion_des_conges.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TypeCongeServices implements ITypeCongeServices{
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
    public TypeConge addTypeConge(TypeConge typeConge, int idPolitique) {
        typeCongeRepository.save(typeConge);
        Politique politique = politiqueRepository.findById(idPolitique).orElse(null);
        if(politique!=null){
            if(politique.getTypeConge()==null){
                List<TypeConge> tConges = new ArrayList<>();
                tConges.add(typeConge);
                politique.setTypeConge(tConges);
            }
            else {
                politique.getTypeConge().add(typeConge);
            }
            politiqueRepository.save(politique);
        }
        return typeConge;
    }

    @Override
    public TypeConge updateTypeConge(TypeConge typeConge, int idPolitique) {
        Politique politique = politiqueRepository.findById(idPolitique).orElse(null);
        if(politique!=null){
            if(politique.getTypeConge()==null){
                List<TypeConge> tConges = new ArrayList<>();
                tConges.add(typeConge);
                politique.setTypeConge(tConges);
            }
            else {
                politique.getTypeConge().add(typeConge);
            }
            politiqueRepository.save(politique);

        }
        typeCongeRepository.save(typeConge);

        return typeConge;
    }

    @Override
    public void deleteTypeConge(int id) {
        typeCongeRepository.deleteById(id);
    }

    @Override
    public TypeConge retrieveTypeConge(int id) {
        return typeCongeRepository.findById(id).orElse(null);
    }

    @Override
    public List<TypeConge> retrieveAllTypeConge() {
        List<TypeConge> typeConges = new ArrayList<>();
        typeCongeRepository.findAll().forEach(typeConges::add);
        return typeConges;
    }
}
