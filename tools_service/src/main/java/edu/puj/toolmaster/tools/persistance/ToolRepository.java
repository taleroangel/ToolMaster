package edu.puj.toolmaster.tools.persistance;

import edu.puj.toolmaster.tools.entities.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Allows for database access achieving Dependency Inversion
 */
public interface ToolRepository extends JpaRepository<Tool, Integer> {
    Page<Tool> findAll(Specification<Tool> spec, Pageable pageable);
}
