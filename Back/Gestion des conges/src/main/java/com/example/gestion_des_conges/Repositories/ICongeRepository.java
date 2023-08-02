package com.example.gestion_des_conges.Repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.example.gestion_des_conges.Entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository


    List<Conge> findByDateDebutLessThanEqualAndDateFinGreaterThanEqual (LocalDateTime dateDebut, LocalDateTime dateFin);
    List<Conge>  findByDemandeurAndDateDebutIsLessThanEqualAndDateFinIsGreaterThanEqual(Employee employe, LocalDateTime dateFin, LocalDateTime dateDebut);

    Conge findFirstByDemandeur(Employee employee);

    boolean findByDateDebutGreaterThanEqualAndDateFinIsLessThan(LocalDateTime d1 , LocalDateTime d2);

    List<Conge> findAllByDateFinLessThan(LocalDateTime date);


    List<Conge> findByDemandeur(Employee employee);

    List<Conge> findByDateDebutBetweenAndEtat(LocalDateTime datedeb, LocalDateTime datefin, Etat etat);

    List<Conge> findByEtat(Etat etat);


}