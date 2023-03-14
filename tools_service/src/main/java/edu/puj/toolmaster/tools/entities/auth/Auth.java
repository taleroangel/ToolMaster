package edu.puj.toolmaster.tools.entities.auth;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

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
