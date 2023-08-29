package com.example.gestion_des_conges.Controllers;


import com.example.gestion_des_conges.Entities.Employee;
import com.example.gestion_des_conges.Entities.Role;
import com.example.gestion_des_conges.Services.IEmplyeeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final IEmplyeeServices employeeServices;

    public Employee addEmployee (@RequestBody Employee employee) {
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

    @GetMapping("/ViewEmployees")
    public List<Employee> retrieveAllEmployees() {
        return employeeServices.retrieveAllEmployee();
    }


}
