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
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idRole;

    String nomRole;

    @Column(columnDefinition = "TEXT")
    String description;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    List<Employee> employees;
}
