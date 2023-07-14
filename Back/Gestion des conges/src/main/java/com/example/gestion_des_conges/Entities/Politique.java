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
public class Politique implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idPolitique;

    String nomPolitique;

    String descriptionPolitique;

    Integer nombreJourOuvrable;

    @ManyToMany(mappedBy = "politiques")
    List<JourFerie> jourFerie;

    @OneToMany(mappedBy = "politique")
    List<Employee> employees;

    @ManyToMany
    List<TypeConge> typeConge;
}
