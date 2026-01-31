package com.sentinelmind.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class DemoUserDetailsService {

    @Bean
    public UserDetailsService userDetailsService() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails doctor = User.builder()
                .username("doctor")
                .password(encoder.encode("doctor123"))
                .roles("DOCTOR")
                .build();

        UserDetails analyst = User.builder()
                .username("analyst")
                .password(encoder.encode("analyst123"))
                .roles("ANALYST")
                .build();

        return new InMemoryUserDetailsManager(admin, doctor, analyst);
    }
}
