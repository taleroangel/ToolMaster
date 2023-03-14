package edu.puj.toolmaster.tools.security;

public interface TokenValidator {
    String validateToken(String token) throws Exception;
}
