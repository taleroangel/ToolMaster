package edu.puj.toolmaster.auth.persistance;

import edu.puj.toolmaster.auth.entities.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio que trae la información de la tabla Auth de la base de datos
 */
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByUsername(String username);
}
