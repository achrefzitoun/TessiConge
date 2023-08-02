package com.example.gestion_des_conges.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Conge implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idConge;

    LocalDateTime dateDemande = LocalDateTime.now();

    LocalDateTime dateDebut;

    LocalDateTime dateFin;

    float duree ;

    @Enumerated(EnumType.STRING)
    Etat etat = Etat.En_Attente;

    String description;

    byte[] pieceJointe;

    Integer idDelegue;

    LocalDateTime dateValidation;

    @ManyToOne
    TypeConge typeConge;

    @ManyToOne
    Employee demandeur;
    @ManyToOne
    Employee validateur;

    @ManyToOne
    MotifRefus motifRefus;

}
