package com.tBaronDar.springSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * Step 2:
     * add the UserDetailsService to this config class
     * and pass it to DaoAuthenticationProvider below
     * then create a service that implements this
     * interface. Here see MyUserDetailsService
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Step 0:
     * set up filter security chain
     * this methode will return an object that will edit
     * the web filter chain AKA the configuration
     * see the console logs when app starts
     * we do this in order to disable csrf
     * make app stateless(send creds on each request)
     * we want this app to be same site, not cross site
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        //lambda approach
        //disable csrf(session)
        http.csrf(customizer -> customizer.disable());
        //all requests must be authenticated
        http.authorizeHttpRequests(req -> req
                //exept register that is un protected
                .requestMatchers("/register", "/login").permitAll()
                .anyRequest().authenticated());
        //enable the default login form(dont need in stateless)
        //http.formLogin(Customizer.withDefaults());
        //type of security == basic
        http.httpBasic(Customizer.withDefaults());
        //disable statefulness(no session)
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //JWT filter insertion
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Step 1:
     * Add postgres driver and data jpa driver
     * use DaoAuthenticationProvider to declare that
     * we will get the authentication from a database
     * no password encoding in this case.
     * steps 1a and 1b is the creation of User model
     * and UserRepo using data jpa
     *
     */
    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    /**
     * JWT
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }
}
