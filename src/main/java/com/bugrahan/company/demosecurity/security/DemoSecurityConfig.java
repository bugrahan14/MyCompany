package com.bugrahan.company.demosecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DemoSecurityConfig {

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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(configurer ->
                configurer.anyRequest().authenticated()
        )
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll());
        return http.build();
    }

}
