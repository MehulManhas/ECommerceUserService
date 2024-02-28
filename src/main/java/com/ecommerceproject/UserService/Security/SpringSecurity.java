package com.ecommerceproject.UserService.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().disable();
        httpSecurity.csrf().disable();
//        httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers("/auth/*").permitAll());
//        httpSecurity.authorizeHttpRequests(authorize -> authorize.requestMatchers("/order/*").permitAll());
        httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        return httpSecurity.build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
