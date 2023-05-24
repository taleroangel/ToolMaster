package edu.puj.toolmaster.auth.security;

import io.jsonwebtoken.Jwts;

/**
 * Verificador de tokens
 */
public interface TokenValidator {
    String validateToken(String token) throws Exception;
}
