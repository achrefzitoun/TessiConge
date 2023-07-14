package com.example.gestion_des_conges.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idEmp;

    String username;

    String motDePass;

    String nom;

    String prenom;

    String adresse;

    String soldeConge;

    @Enumerated(EnumType.STRING)
    Statut statut;

    String entite;

    @ManyToOne
    Role role;

    @ManyToOne
    Employee superviseur;

    @OneToMany(mappedBy = "demandeur")
    List<Conge> listDemande;

    @OneToMany(mappedBy = "validateur")
    List<Conge> listValidation;
    @ManyToOne
    Politique politique;
}
