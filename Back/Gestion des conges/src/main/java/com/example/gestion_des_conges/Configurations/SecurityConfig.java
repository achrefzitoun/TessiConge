package com.example.gestion_des_conges.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class  SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     /*   auth
                .ldapAuthentication()
                .userDnPatterns("cn={0}")  // Modèle DN pour la recherche d'utilisateurs
                .contextSource(contextSource())
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");*/
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      /*  http
                .authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()              .and()
                .formLogin();*/
    }


}
