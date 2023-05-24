package edu.puj.toolmaster.users.persistance;

import edu.puj.toolmaster.users.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio encargado de hacer la conexión con la base de datos para obtener información de la tabla User
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByActiveTrue(Pageable pageable);
    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
