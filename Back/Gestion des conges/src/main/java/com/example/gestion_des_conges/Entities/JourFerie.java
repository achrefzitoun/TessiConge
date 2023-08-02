package com.example.gestion_des_conges.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JourFerie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idJour;

    String nomDate;

    LocalDateTime dateDebut;

    LocalDateTime dateFin;

    Integer annee;

    @ManyToMany
    @JsonIgnore
    List<Politique> politiques;
}
