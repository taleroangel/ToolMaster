package edu.puj.toolmaster.tools.security;

import edu.puj.toolmaster.tools.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable CSRF
        http.csrf().disable();
        // Cors Setup
        http.cors().configurationSource(
                request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedMethods(List.of(new String[]{"GET", "POST", "PUT", "DELETE", "PATCH"}));
                    return cors.applyPermitDefaultValues();
                }
        );
        // Do not create session cookies
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests(
                        e -> e.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                     .anyRequest().authenticated().shouldFilterAllDispatcherTypes(false)
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
