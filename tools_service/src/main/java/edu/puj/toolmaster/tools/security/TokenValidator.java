package edu.puj.toolmaster.tools.security;

/**
 * Interfaz mediante la cuál se implementa un validador de tokens
 */
public interface TokenValidator {
    String validateToken(String token) throws Exception;
}
