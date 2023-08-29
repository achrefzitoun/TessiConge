package com.example.gestion_des_conges.Repositories;

import com.example.gestion_des_conges.Entities.NatureType;
import com.example.gestion_des_conges.Entities.TypeConge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITypeCongeRepository extends CrudRepository<TypeConge, Integer> {

    @Query("SELECT t FROM TypeConge t WHERE t.natureType = 'Speciale'")
    List<TypeConge> findByNatureSpeciale();

    List<TypeConge> findAllByNatureType(NatureType natureType);


}