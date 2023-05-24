package edu.puj.toolmaster.users.entities.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Entidad encargada de guardar los datos de autenticación
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
