package com.example.gestion_des_conges.Repositories;

import com.example.gestion_des_conges.Entities.Autorisation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IAutorisationRepository extends CrudRepository<Autorisation, Integer> {

    Integer countByDateDebutBetween(LocalDateTime d, LocalDateTime f);

    List<Autorisation> findByDateDebutBeforeAndDateFinAfter(LocalDateTime dateDebut, LocalDateTime dateFin);

}
