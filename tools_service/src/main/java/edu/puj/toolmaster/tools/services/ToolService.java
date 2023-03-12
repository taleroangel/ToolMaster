package edu.puj.toolmaster.tools.services;

import edu.puj.toolmaster.tools.entities.Tool;
import edu.puj.toolmaster.tools.persistance.ToolRepository;
import edu.puj.toolmaster.tools.services.exceptions.ToolNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    @Autowired
    ToolRepository repository;

    public List<Tool> getAllTools() {
        return repository.findAll();
    }

    public Tool getToolById(Integer id) throws ToolNotFoundException {
        return repository.findById(id).orElseThrow(ToolNotFoundException::new);
    }

    public Tool addTool(Tool tool) {
        return repository.save(tool);
    }

    public void deleteToolById(Integer id) {
    }

    public void updateToolById(Integer id, Tool tool) {
    }
}
