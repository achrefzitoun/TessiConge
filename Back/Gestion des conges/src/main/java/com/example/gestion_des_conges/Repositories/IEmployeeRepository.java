package com.example.gestion_des_conges.Repositories;

import com.example.gestion_des_conges.Entities.Employee;
import com.example.gestion_des_conges.Entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findEmployeeByUsername(String username);

    List<Employee> findByRole(Role role);

}

