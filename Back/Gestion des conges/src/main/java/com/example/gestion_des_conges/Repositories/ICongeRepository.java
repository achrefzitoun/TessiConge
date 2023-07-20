package com.example.gestion_des_conges.Repositories;

import com.example.gestion_des_conges.Entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ICongeRepository extends CrudRepository<Conge, Integer> {
    List<Conge> findByDateDebutBetweenAndEtat(LocalDateTime datedeb, LocalDateTime datefin, Etat etat);

    List<Conge> findByEtat(Etat etat);
}