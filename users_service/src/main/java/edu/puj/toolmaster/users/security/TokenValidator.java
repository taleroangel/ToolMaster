package edu.puj.toolmaster.users.security;

public interface TokenValidator {
    String validateToken(String token) throws Exception;
}
