package edu.puj.toolmaster.auth.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenGenerator {
    String generateAccessToken(UserDetails user) throws Exception;
}
