package com.example.gestion_des_conges.Configurations;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;

import java.util.Collections;

@Configuration
public class LDAPconfig {


    @Value("${ldap.url}")
    private String ldapUrl;

    @Value("${ldap.user-base}")
    private String ldapBase;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrl);
        contextSource.setBase(ldapBase);
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(ldapAuthenticationProvider()));
    }

    @Bean
    public LdapAuthenticationProvider ldapAuthenticationProvider() {
        return new LdapAuthenticationProvider(ldapBindAuthenticator());
    }

    @Bean
    public BindAuthenticator ldapBindAuthenticator() {
        return new BindAuthenticator(contextSource());
    }


}



