package br.com.livrariapapaya.papaya.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AuthFilter authFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http.csrf( csrf->  csrf.disable()) // desabilita o csrf pois eu que irei tratar.
                .cors(cors -> cors.disable())
                .sessionManagement( sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // desativa a tela de login default do spring
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET,"/hello").permitAll() //Endpoint liberado
                .anyRequest().authenticated()) //Todos os endpoints que não estejam listados acima precisam ser autenticados
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}
