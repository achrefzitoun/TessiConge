package com.example.gestion_des_conges.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Autorisation implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idAutorisation;

    LocalDateTime dateDebut;

    LocalDateTime dateFin;

    String description;
    @ManyToOne
    Employee demandeur;
}
