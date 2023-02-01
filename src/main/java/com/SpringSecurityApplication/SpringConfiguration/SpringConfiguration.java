package com.SpringSecurityApplication.SpringConfiguration;
//@Configuration

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
//@EnableMethodSecurity
public class SpringConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails normalUser = User.withUsername("vishal")
                .password(passwordEncoder().encode("vishal"))
                .roles("NORMAL")
                .build();
        UserDetails adminUser = User.withUsername("rahul")
                .password(passwordEncoder().encode("rahul"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(normalUser,adminUser);

    }
     @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.csrf().disable()
                 .authorizeHttpRequests()
                 .requestMatchers("/admin")
                 .hasRole("ADMIN")
                 .requestMatchers("/normal")
                 .hasRole("NORMAL")
                 .requestMatchers("/public")
                 .permitAll()
                 .anyRequest()
                 .authenticated()
                 .and()
                 .formLogin();
         return httpSecurity. build();
     }
}
