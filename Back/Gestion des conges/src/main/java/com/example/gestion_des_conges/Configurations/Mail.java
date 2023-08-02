package com.example.gestion_des_conges.Configurations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class Mail {
    private String from;
    private String mailTo;
    private String subject;
    private Map<String, Object> props;

    // Constructeur, getters et setters

}
