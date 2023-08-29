package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.*;

import java.util.List;

public interface IEmplyeeServices {

    public List<Employee> retrieveAllEmployee();

    public List<Employee> getEmployeeByPolitique(Integer idPolitique);
    public Employee getEmployeById(Integer id) ;


}
