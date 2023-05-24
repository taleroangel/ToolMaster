package edu.puj.toolmaster.users.persistance;

import edu.puj.toolmaster.users.entities.auth.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio encargado de hacer la conexión con la base de datos para obtener información de la tabla Auth
 */
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByUsername(String username);
}
