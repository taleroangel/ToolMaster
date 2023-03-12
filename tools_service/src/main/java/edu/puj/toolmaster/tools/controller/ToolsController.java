package edu.puj.toolmaster.tools.controller;

import edu.puj.toolmaster.tools.entities.Tool;
import edu.puj.toolmaster.tools.services.ToolService;
import edu.puj.toolmaster.tools.services.exceptions.EntityAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

/**
 * Tools API endpoint
 */
@RestController
@RequestMapping("/api/tools")
public class ToolsController {
    @Autowired
    ToolService service;

    /**
     * Get all the tools presented as a Page
     *
     * @param page Number of page, first is 0 (by default)
     * @param size Size of the page, 10 by default
     * @param sort Sorting criteria field (name by default)
     * @return Page with tools
     */
    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    Page<Tool> getAllTools(@RequestParam(defaultValue = "0") Integer page,
                           @RequestParam(defaultValue = "10") Integer size,
                           @RequestParam(defaultValue = "name") String sort) {
        return service.getAllTools(PageRequest.of(page, size, Sort.by(sort)));
    }

    /**
     * Get a tool by its id
     *
     * @param id ID of the tool as path variable
     * @return Tool or 404
     */
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    Tool getToolById(@PathVariable Integer id) {
        return service.getToolById(id);
    }

    /**
     * Post a new tool
     *
     * @param tool New tool
     * @return Tool added
     */
    @PostMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Tool> createNewTool(@RequestBody Tool tool) {
        try {
            return new ResponseEntity<Tool>(service.addNewTool(tool), HttpStatus.CREATED);
        } catch (EntityAlreadyExistsException e) {
            // Use Liskov Substitution principle to return a tool from a DomainEntity
            return new ResponseEntity<Tool>((Tool) e.getResource(), HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteToolById(@PathVariable Integer id) {
        service.deleteToolById(id);
    }

    @PutMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Tool updateToolById(@PathVariable Integer id, @RequestBody Tool tool) {
        return service.updateToolById(id, tool);
    }

    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Tool partialToolUpdate(@PathVariable Integer id, @RequestBody Tool tool) {
        return service.partialToolUpdateById(id, tool);
    }
}
