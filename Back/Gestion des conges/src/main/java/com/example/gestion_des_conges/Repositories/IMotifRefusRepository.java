package com.example.gestion_des_conges.Repositories;

import com.example.gestion_des_conges.Entities.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMotifRefusRepository extends CrudRepository<MotifRefus, Integer> {
    List<MotifRefus> findByTypeMotif(TypeMotif typeMotif);

}