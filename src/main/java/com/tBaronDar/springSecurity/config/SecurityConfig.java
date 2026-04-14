package com.tBaronDar.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //this methode will return an object that will edit
    //the web filter chain AKA the configuration
    //see the console logs when app starts
    //we do this in order to disable csrf
    //make app stateless, send creds on each request
    //we want this app to be same site, not cross site

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        //lambda approach
        //disable csrf
        http.csrf(customizer -> customizer.disable());
        //all requests must be authenticated
        http.authorizeHttpRequests(req -> req.anyRequest().authenticated());
        //enable the default login form(dont need in stateless
//        http.formLogin(Customizer.withDefaults());
        //type of security == basic
        http.httpBasic(Customizer.withDefaults());
        //disable statefulness(no session)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }

    @Bean
    public AuthenticationProvider authProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        return provider;
    }
}
