package edu.puj.toolmaster.auth.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Representa una entidad de autenticación
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
