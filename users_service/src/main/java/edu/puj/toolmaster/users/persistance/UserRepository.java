package edu.puj.toolmaster.users.persistance;

import edu.puj.toolmaster.users.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByActiveTrue(Pageable pageable);
    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
