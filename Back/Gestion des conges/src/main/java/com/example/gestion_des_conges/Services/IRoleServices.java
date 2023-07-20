package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.*;

import java.util.List;
import java.util.Map;

public interface IRoleServices {

    //Crud
    public Role addRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(int id);
    public Role retrieveRole(int id);
    public List<Role> retrieveAllRoles();
    public List<Employee> retrieveEmployeesByRole(Role role);
    public Map<String, Integer> countEmployeesByRole();
}
