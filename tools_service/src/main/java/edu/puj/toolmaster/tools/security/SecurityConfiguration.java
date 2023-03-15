package edu.puj.toolmaster.tools.security;

import edu.puj.toolmaster.tools.security.jwt.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        e -> e.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                     .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {

                    var stack = Arrays.stream(authException.getStackTrace())
                                        .map(StackTraceElement::getFileName)
                                        .filter(Objects::nonNull)
                                        .filter(e -> e.equals("JwtFilter.java")).toList();

                    if (stack.isEmpty()) {
                        response.sendError(response.getStatus());
                    } else {
                        response.sendError(HttpStatus.UNAUTHORIZED.value());
                    }
                });

        // Disable CSRF
        http.csrf().disable();
        // Do not create session cookies
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
