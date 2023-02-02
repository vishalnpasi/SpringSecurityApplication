package com.SpringSecurityApplication.SpringConfiguration;
//@Configuration

import com.SpringSecurityApplication.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableMethodSecurity
public class SpringConfiguration {

    @Autowired
    private JwtAuthFilter authFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(){
//        UserDetails normalUser = User.withUsername("vishal")
//                .password(passwordEncoder().encode("vishal"))
//                .roles("NORMAL")
//                .build();
//        UserDetails adminUser = User.withUsername("rahul")
//                .password(passwordEncoder().encode("rahul"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
        return new UserInfoUserDetailsService();

    }
     @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

         return  httpSecurity.csrf().disable()
                 .authorizeHttpRequests()
                 .requestMatchers("/public","/user","/authenticate")
                 .permitAll()
                 .requestMatchers("/admin")
                 .hasRole("ADMIN")
                 .requestMatchers("/normal")
                 .hasRole("NORMAL")
//                 .anyRequest()
//                 .authenticated()
                 .and()
                 .sessionManagement()
                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                 .authenticationProvider(authenticationProvider())
                 .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                 .build();
//        return  httpSecurity.csrf().disable()
//                 .authorizeHttpRequests()
//                 .requestMatchers("/admin")
//                 .hasRole("ADMIN")
//                 .requestMatchers("/normal")
//                 .hasRole("NORMAL")
//                 .requestMatchers("/public","/user","/authenticate")
//                 .permitAll()
//                 .anyRequest()
//                 .authenticated()
//                 .and()
//
//                 .sessionManagement()
//                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                 .and()
//                 .authenticationProvider(authenticationProvider())
//                 .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();

//                 .formLogin().and().build();
////         return httpSecurity.build();
     }
     @Bean
     public AuthenticationProvider authenticationProvider(){
         DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
         authenticationProvider.setUserDetailsService(userDetailsService());
         authenticationProvider.setPasswordEncoder(passwordEncoder());
         return authenticationProvider;
     }
     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
     }
}