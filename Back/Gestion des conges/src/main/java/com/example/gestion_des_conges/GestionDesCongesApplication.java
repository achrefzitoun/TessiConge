package com.example.gestion_des_conges;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@Import(CorsConfiguration.class)
public class GestionDesCongesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionDesCongesApplication.class, args);
    }

}
