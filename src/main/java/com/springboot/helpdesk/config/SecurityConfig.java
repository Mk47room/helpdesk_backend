package com.springboot.helpdesk.config;

import com.springboot.helpdesk.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

// I will define a bean of type UserDetailsService
// You are telling Spring: I don't want to use your details service,
// I want to define my own Service and my own credentials.

    private final MyUserDetailsService myUserDetailsService ;

    private final JwtFilter jwtFilter;

    /*
    @Bean
    public UserDetailsService users() {
        UserDetails Customer1 = User.builder()
                .username("customer1")
                .password("{noop}customer1")
                .authorities("CUSTOMER")
                .build();
        UserDetails Executive1 = User.builder()
                .username("executive1")
                .password("{noop}executive1")
                .authorities("EXECUTIVE")
                .build();
        UserDetails Manager1 = User.builder()
                .username("manager1")
                .password("{noop}manager1")
                .authorities("MANAGER")
                .build();

        return new InMemoryUserDetailsManager(Customer1, Executive1,Manager1);
    }
    */


    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // login API
                        .requestMatchers(HttpMethod.GET,"/api/auth/login").authenticated()

                        .requestMatchers(HttpMethod.POST,"/api/customer/add").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/customer/{id}").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/api/ticket/add").hasAuthority("CUSTOMER")

                        // Admin onboard API (permitAll to add) (After addition - denyAll)
                        .requestMatchers(HttpMethod.POST,"/api/admin/add").denyAll()
                        // Admin can onboard manager
                        .requestMatchers(HttpMethod.POST,"/api/manager/add").hasAuthority("ADMIN")
                        // Executive can be onboarded by manager after they login
                        .requestMatchers(HttpMethod.POST,"/api/executive/add").hasAuthority("MANAGER")
                        // Manager assigning the ticket to executive
                        .requestMatchers(HttpMethod.PUT,"/api/ticket/assign/{executiveId}/{ticketId}").hasAuthority("MANAGER")

                        .anyRequest().permitAll()
                );

        // Add jwt filter before Spring's Auth Filter
        http.addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class);

        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public AuthenticationProvider getProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(getEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }
}

