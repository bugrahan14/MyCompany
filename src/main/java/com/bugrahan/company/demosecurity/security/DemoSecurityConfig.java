package com.bugrahan.company.demosecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {



    // The data is retrieved from the database
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        return new JdbcUserDetailsManager(dataSource);
    }


    // CUSTOM LOGİN PAGE
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/").hasRole("EMPLOYEE")
                        .requestMatchers("/leaders/**").hasRole("MANAGER")
                        .requestMatchers("/systems/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        )
                // CUSTOM PAGE
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )

                // LOGOUT
                .logout(logout -> logout.permitAll()
                )


                // CUSTOM ERROR MESSAGE AND PAGE
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied"));

        return http.build();
    }


    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails bugrahan = User.builder()
                .username("bugrahan")
                .password("{noop}test123")
                .roles("EMPLOYEE","MANAGER", "ADMIN")
                .build();

        UserDetails mehmet = User.builder()
                .username("mehmet")
                .password("{noop}test123")
                .roles("EMPLOYEE")
                .build();

        UserDetails ayhan = User.builder()
                .username("ayhan")
                .password("{noop}test123")
                .roles("EMPLOYEE","MANAGER")
                .build();

        return new InMemoryUserDetailsManager(bugrahan,mehmet,ayhan);
    }
    */

}
