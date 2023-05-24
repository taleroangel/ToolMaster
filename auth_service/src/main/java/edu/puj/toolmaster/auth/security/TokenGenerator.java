package edu.puj.toolmaster.auth.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Generador de tokens
 */
public interface TokenGenerator {
    String generateAccessToken(UserDetails user) throws Exception;
}
