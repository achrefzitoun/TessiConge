package com.example.gestion_des_conges.Repositories;

import com.example.gestion_des_conges.Entities.Employee;
import com.example.gestion_des_conges.Entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Repository
public interface IEmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findEmployeeByUsername(String username);

    List<Employee> findByRole(Role role);


    @Query("SELECT e FROM Employee e WHERE e.statut ='Actif' and e.role.nomRole='employee' and e.entite='Developpement'")
    List<Employee> getEmplyeesDev();

    @Query("SELECT e FROM Employee e WHERE e.statut ='Actif' and e.role.nomRole='employee' and e.entite='Controle'")
    List<Employee> getEmplyeesContr();

    @Query("SELECT e FROM Employee e WHERE e.politique.idPolitique = :politiqueId")
    List<Employee> getEmployeesByPolitiqueId(@Param("politiqueId") Integer politiqueId);

    @Query("SELECT e FROM Employee e INNER JOIN Conge c ON c.idDelegue = e.idEmp WHERE c.idDelegue = :emplId")
    Employee getEmployee(@Param("emplId") Integer emplId);







}