package edu.puj.toolmaster.users.security;

/**
 * Validador de tokens
 */
public interface TokenValidator {
    String validateToken(String token) throws Exception;
}
