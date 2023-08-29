package com.example.gestion_des_conges.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"jourFerie", "employees"})

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
    @JsonIgnore
    List<Employee> employees;

    @ManyToMany
    List<TypeConge> typeConge;
}
