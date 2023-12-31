package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.*;

import java.util.List;

public interface ITypeCongeServices {
    public TypeConge addTypeConge(TypeConge typeConge, List<Politique> politiques);
    public TypeConge updateTypeConge(TypeConge typeConge, List<Politique> politiques);
    public List<TypeConge> getTypeCongebyNature(NatureType natureType);
    public void deleteTypeConge(int id);
    public TypeConge retrieveTypeConge(int id);
    public List<TypeConge> retrieveAllTypeConge();
}
