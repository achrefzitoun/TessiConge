package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.Employee;
import com.example.gestion_des_conges.Entities.Role;

import java.util.List;

public interface IEmplyeeServices {
    public Employee addEmployee (Employee employee);
    public Employee updateEmployee(Employee employee);
    public void deleteEmployee(int id);
    public Employee retrieveEmployee(int id);
    public List<Employee> retrieveAllEmployee();

}
