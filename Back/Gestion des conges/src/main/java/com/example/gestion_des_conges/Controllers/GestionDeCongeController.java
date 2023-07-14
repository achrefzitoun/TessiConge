package com.example.gestion_des_conges.Controllers;

import com.example.gestion_des_conges.Entities.Role;
import com.example.gestion_des_conges.Services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conge")
public class GestionDeCongeController {

    private final IRoleServices roleServices;

    private final ICongeServices congeServices;

    private final IEmplyeeServices emplyeeServices;

    private final IJourFerieServices jourFerieServices;

    private final IPolitiqueServices politiqueServices;

    private final IMotifRefusServices motifRefusServices;

    private final ITypeCongeServices typeCongeServices;

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

}
