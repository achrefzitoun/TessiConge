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
import java.util.stream.Collectors;

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

    @Autowired
    private final PolitiqueServices politiqueServices;

    @Override
    public TypeConge addTypeConge(TypeConge typeConge, List<Politique> politiques) {
        typeCongeRepository.save(typeConge);
        for(Politique politique : politiques){
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
    public TypeConge updateTypeConge(TypeConge typeConge, List<Politique> politiques) {

        List<Politique> politiqueList = new ArrayList<>();
        politiqueRepository.findAll().forEach(politiqueList::add);

        for (Politique p : politiqueList){
            p.getTypeConge().removeIf(type -> type.getIdTypeConge() == typeConge.getIdTypeConge());
            politiqueRepository.save(p);
        }


        for(Politique politique : politiques){
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
    public List<TypeConge> getTypeCongebyNature(NatureType natureType) {
        return typeCongeRepository.findAllByNatureType(natureType);
    }

    @Override
    public void deleteTypeConge(int id) {
        TypeConge typeConge = typeCongeRepository.findById(id).orElse(null);


        List<Politique> politiques = new ArrayList<>();
        politiqueRepository.findAll().forEach(politiques::add);

        for (Politique p : politiques){
            if(p.getTypeConge().contains(typeConge)){
                p.getTypeConge().remove(typeConge);
                politiqueRepository.save(p);
            }
        }

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
