package com.example.gestion_des_conges.Repositories;

import com.example.gestion_des_conges.Entities.Autorisation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAutorisationRepository extends CrudRepository<Autorisation, Integer> {
}