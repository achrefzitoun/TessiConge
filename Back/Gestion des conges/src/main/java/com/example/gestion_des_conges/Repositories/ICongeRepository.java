package com.example.gestion_des_conges.Repositories;

import com.example.gestion_des_conges.Entities.Conge;
import com.example.gestion_des_conges.Entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ICongeRepository extends CrudRepository<Conge, Integer> {

    List<Conge> findByDateDebutLessThanEqualAndDateFinGreaterThanEqual (LocalDateTime dateDebut, LocalDateTime dateFin);
    List<Conge>  findByDemandeurAndDateDebutIsLessThanEqualAndDateFinIsGreaterThanEqual(Employee employe, LocalDateTime dateFin, LocalDateTime dateDebut);

    Conge findFirstByDemandeur(Employee employee);

    boolean findByDateDebutGreaterThanEqualAndDateFinIsLessThan(LocalDateTime d1 , LocalDateTime d2);

    List<Conge> findAllByDateFinLessThan(LocalDateTime date);


    List<Conge> findByDemandeur(Employee employee);

}