package com.example.gestion_des_conges.Repositories;

import com.example.gestion_des_conges.Entities.Politique;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPolitiqueRepository extends CrudRepository<Politique, Integer> {
}