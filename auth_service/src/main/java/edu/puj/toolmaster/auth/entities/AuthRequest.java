package edu.puj.toolmaster.auth.entities;

import lombok.Value;

@Value
public class AuthRequest {
    String username;
    String password;
}
