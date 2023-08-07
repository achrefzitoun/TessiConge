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
    @Autowired
    private final ICongeRepository congeRepository;

    @Autowired
    private final IEmployeeRepository employeeRepository;

    @Autowired
    private final IJourFerieRepository jourFerieRepository;

    @Autowired
    private final IMotifRefusRepository motifRefusRepository;

    @Autowired
    private final IPolitiqueRepository politiqueRepository;

    @Autowired
    private final IRoleRepository roleRepository;

    @Autowired
    private final ITypeCongeRepository typeCongeRepository;

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee retrieveEmployee(int id) {
        return null;
    }

    @Override
    public List<Employee> retrieveAllEmployee() {
        List<Employee> employees = new ArrayList<Employee>();
        employeeRepository.findAll().forEach(books1 -> employees.add(books1));
        return employees;
    }
}
