package com.example.gestion_des_conges.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    String email;

    String username;

    String motDePass;

    String nom;

    String prenom;

    String adresse;

    double soldeConge;

    boolean autorisationJour = false;

    long autorisationDuration = 6;

    @Enumerated(EnumType.STRING)
    Statut statut;

    String entite;

    @ManyToOne
    Role role;

    @ManyToOne
    @JsonIgnore
    Employee superviseur;

    @OneToMany(mappedBy = "demandeur")
    @JsonIgnore
    List<Conge> listDemande;

    @OneToMany(mappedBy = "validateur")
    @JsonIgnore
    List<Conge> listValidation;
    @ManyToOne
    Politique politique;

    @OneToMany(mappedBy = "demandeur")
            @JsonIgnore
    List<Autorisation> autorisations;
}
