package com.example.gestion_des_conges.Services;

import com.example.gestion_des_conges.Entities.*;

import java.util.List;
import java.util.Map;

public interface IMotifRefusServices {
    public MotifRefus addMotifRefus(MotifRefus motifRefus);
    public MotifRefus updateMotifRefus(MotifRefus motifRefus);
    public void deleteMotifRefus(int id);
    public MotifRefus retrieveMotifRefus(int id);
    public List<MotifRefus> retrieveAllMotifRefus();

    public MotifRefus retrieveMotifByConge(Conge conge);

}
