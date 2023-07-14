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
public class MotifRefus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idMotif;

    @Enumerated(EnumType.STRING)
    TypeMotif typeMotif;

    String description;

    @OneToMany(mappedBy = "motifRefus")
    List<Conge> conge;
}
