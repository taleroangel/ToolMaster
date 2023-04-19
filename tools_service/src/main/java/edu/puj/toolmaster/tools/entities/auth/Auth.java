package edu.puj.toolmaster.tools.entities.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Clase que representa el modelo de autenticación en la base de datos
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Getter
public class Auth {
    @Id
    private Long userId;

    private String username;

    private String password;
}
