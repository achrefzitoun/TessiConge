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

    LocalDateTime dateDemance;

    LocalDateTime dateDebut;

    LocalDateTime dateFin;

    Integer duree;

    @Enumerated(EnumType.STRING)
    Etat etat;

    String description;

    byte[] pieceJointe;

    @ManyToOne
    TypeConge typeConge;

    @ManyToOne
    Employee demandeur;
    @ManyToOne
    Employee validateur;

    @ManyToOne
    MotifRefus motifRefus;

}
