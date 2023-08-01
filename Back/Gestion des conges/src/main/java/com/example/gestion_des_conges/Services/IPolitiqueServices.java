package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.*;

import java.util.List;

public interface IPolitiqueServices {
    public Politique addPolitique(Politique politique);
    public Politique updatePolitique(Politique politique);
    public void deletePolitique(int id);
    public Politique retrievePolitique(int id);
    public List<Politique> retrieveAllPolitique();
}
