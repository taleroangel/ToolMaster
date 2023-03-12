package edu.puj.toolmaster.tools.persistance;

import edu.puj.toolmaster.tools.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Allows for database access achieving Dependency Inversion
 */
public interface ToolRepository extends JpaRepository<Tool, Integer> {
}
