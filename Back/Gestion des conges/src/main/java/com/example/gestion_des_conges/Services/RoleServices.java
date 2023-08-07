package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.Employee;
import com.example.gestion_des_conges.Entities.Role;
import com.example.gestion_des_conges.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoleServices implements IRoleServices {
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
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role retrieveRole(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Role> retrieveAllRoles() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    @Override
    public List<Employee> retrieveEmployeesByRole(Role role) {
        List<Employee> employees = employeeRepository.findByRole(role);
        return employees;
    }

    @Override
    public Map<String, Integer> countEmployeesByRole() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        Map<String,Integer> result = new HashMap<>();
        for(Role r : roles){
            result.put(r.getNomRole(),employeeRepository.findByRole(r).size());
        }
        return result;
    }

}
