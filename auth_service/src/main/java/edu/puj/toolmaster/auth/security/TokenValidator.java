package edu.puj.toolmaster.auth.security;

import io.jsonwebtoken.Jwts;

public interface TokenValidator {
    String validateToken(String token) throws Exception;
}
