package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.*;

import java.io.IOException;
import java.util.List;

public interface IJourFerieServices {

    public void miseAJourJourFerie() throws IOException;
    public JourFerie addJourFerie(JourFerie jourFerie);
    public JourFerie updateJourFerie(JourFerie jourFerie);
    public void deleteJourFerie(int id);
    public JourFerie retrieveJourFerie(int id);
    public List<JourFerie> retrieveAllJourFerie();

}
