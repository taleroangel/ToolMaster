package edu.puj.toolmaster.tools.persistance;

import edu.puj.toolmaster.tools.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Integer> {
}
