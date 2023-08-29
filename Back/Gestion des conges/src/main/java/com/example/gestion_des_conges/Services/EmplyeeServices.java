package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.Employee;
import com.example.gestion_des_conges.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmplyeeServices implements IEmplyeeServices {


    private final IEmployeeRepository employeeRepository;


    @Override
    public List<Employee> retrieveAllEmployee() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
    }

    @Override
    public List<Employee> getEmployeeByPolitique(Integer idPolitique){
        return  employeeRepository.getEmployeesByPolitiqueId(idPolitique);
    }

    @Override
    public Employee getEmployeById(Integer id){
        return employeeRepository.findById(id).orElse(null);
    }



}
