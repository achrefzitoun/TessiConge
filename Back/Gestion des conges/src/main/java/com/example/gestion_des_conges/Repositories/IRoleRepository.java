package com.example.gestion_des_conges.Repositories;

import com.example.gestion_des_conges.Entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends CrudRepository<Role, Integer> {
    Role findByNomRole(String s);
}