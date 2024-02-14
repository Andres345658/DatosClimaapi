package com.datosclima.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.datosclima.api.security.jwt.JwtEntryPonit;
import com.datosclima.api.security.jwt.JwtTokenFilter;
import com.datosclima.api.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MainSecurity{

     @Autowired
    UserDetailsServiceImpl userDetailsService; 

    @Autowired
    JwtEntryPonit jwtEntryPonit;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public JwtTokenFilter jsJwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrfConfig-> csrfConfig.disable())
            .exceptionHandling(ex->{
                ex.authenticationEntryPoint(jwtEntryPonit);
            })
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jsJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authConfig ->{
               authConfig.requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
                authConfig.anyRequest().authenticated();
            })
            .sessionManagement(sessionMangConfg -> sessionMangConfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            return http.build();
    }

    
}
