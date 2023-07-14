package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.Role;

import java.util.List;

public interface IRoleServices {

    //Crud
    public Role addRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(int id);
    public Role retrieveRole(int id);
    public List<Role> retrieveAllRoles();
}
