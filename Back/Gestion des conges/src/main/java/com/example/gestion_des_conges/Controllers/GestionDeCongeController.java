package com.example.gestion_des_conges.Controllers;

import com.example.gestion_des_conges.Entities.Employee;
import com.example.gestion_des_conges.Entities.Role;
import com.example.gestion_des_conges.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GestionDeCongeController {

    private final IRoleServices roleServices;

    private final ICongeServices congeServices;

    private final IEmployeeServices employeeServices;

    private final IJourFerieServices jourFerieServices;

    private final IPolitiqueServices politiqueServices;

    private final IMotifRefusServices motifRefusServices;

    private final ITypeCongeServices typeCongeServices;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String index () {
        return "Home page" ;
    }



    // ----- ROLE -----
    @PostMapping("/NewRole")
    public Role addRole(@RequestBody Role role) {
        return roleServices.addRole(role);
    }

    @PutMapping("/UpdateRole")
    public Role updateRole(@RequestBody Role role) {
        return roleServices.updateRole(role);
    }

    @DeleteMapping("/DeleteRole/{id}")
    public void deleteRole(@PathVariable("id") int id) {
        roleServices.deleteRole(id);
    }

    @GetMapping("/ViewRole/{id}")
    public Role retrieveRole(@PathVariable("id") int id) {
        return roleServices.retrieveRole(id);
    }

    @GetMapping("/ViewRoles")
    public List<Role> retrieveAllRoles() {
        return roleServices.retrieveAllRoles();
    }





    // ----- Employee -----
    @PostMapping("/NewEmployee")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeServices.addEmployee(employee);
    }

    @PutMapping("/UpdateEmployee")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return employeeServices.updateEmployee(employee);
    }

    @DeleteMapping("/DeleteEmployee/{id}")
    public void deleteEmployee(@PathVariable("id") int id) {
        employeeServices.deleteEmployee(id);
    }

    @GetMapping("/ViewEmployee/{id}")
    public Employee retrieveEmployee(@PathVariable("id") int id) {
        return employeeServices.retrieveEmployee(id);
    }

    @GetMapping("/ViewEmployee")
    public List<Employee> retrieveAllEmployee() {
        return employeeServices.retrieveAllEmployee();
    }

}
