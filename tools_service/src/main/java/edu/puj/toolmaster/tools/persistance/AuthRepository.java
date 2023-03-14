package edu.puj.toolmaster.tools.persistance;

import edu.puj.toolmaster.tools.entities.auth.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByUsername(String username);
}
