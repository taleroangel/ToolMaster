package edu.puj.toolmaster.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa una solicitud de autenticación
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    String username;
    String password;
}
