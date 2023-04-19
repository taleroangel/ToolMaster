package edu.puj.toolmaster.tools.services;

import edu.puj.toolmaster.tools.entities.*;
import edu.puj.toolmaster.tools.persistance.BrandRepository;
import edu.puj.toolmaster.tools.persistance.CityRepository;
import edu.puj.toolmaster.tools.persistance.ToolRepository;
import edu.puj.toolmaster.tools.services.exceptions.EntityAlreadyExistsException;
import edu.puj.toolmaster.tools.services.exceptions.ResourceBadRequestException;
import edu.puj.toolmaster.tools.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.criteria.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Interacts with database to retrieve Tools data achieving Single Responsibility
 */
@Service
public class ToolService {

    @Autowired
    ToolRepository toolRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CityRepository cityRepository;

    /**
     * Get a paged list of all tools
     *
     * @return Page with tools
     */
    public Page<Tool> getAllTools(Pageable pageable) {
        return toolRepository.findAll(pageable);
    }

    /**
     * Obtener las herramientas cuyo nombre coincida o contenga el especificado
     * @param name Nombre de la herramientas
     * @param pageable Criterio de paginación
     * @return Herramientas paginadas
     */
    public Page<Tool> toolByNameLike(String name, Pageable pageable) {
        String matchName = "%" + name + "%";
        Specification<Tool> spec = Specification.where((root, query, cb) -> cb.like(root.get(Tool_.name), matchName));
        return toolRepository.findAll(spec, pageable);
    }

    /**
     * Obtener las herramientas que tengan una marca cuyo nombre coincida o contenga el especificado
     * @param brand Nombre de la marca
     * @param pageable Criterio de paginación
     * @return Herramientas paginadas
     */
    public Page<Tool> toolByBrand(String brand, Pageable pageable) {
        Specification<Tool> spec = Specification.where((root, query, cb) -> {
            Join<Tool, Brand> brandJoin = root.join(Tool_.brand, JoinType.LEFT);
            return cb.like(brandJoin.get(Brand_.name), brand);
        });
        return toolRepository.findAll(spec, pageable);
    }


    /**
     * Get a tool by its id
     *
     * @param id ID to search
     * @return Fetched tool
     * @throws ResourceNotFoundException Tool doesn't exist
     */
    public Tool getToolById(Integer id) throws ResourceNotFoundException {
        return toolRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Parse tool to parse cities and brand names
     *
     * @param tool Tool to parse
     * @return Parsed tool
     * @throws ResourceBadRequestException, cities or brand had invalid values
     * @see edu.puj.toolmaster.tools.services.ToolService#parseToolBrand
     * @see edu.puj.toolmaster.tools.services.ToolService#parseToolCities
     */
    public Tool parseTool(Tool tool) throws ResourceBadRequestException {
        return tool.withBrand(parseToolBrand(tool)).withCities(parseToolCities(tool));
    }

    /**
     * Parse tools brands with either name or id
     *
     * @param tool Tool with brand
     * @return Parsed Brand
     */
    public Brand parseToolBrand(Tool tool) {
        return
                ((tool.getBrand().getId() == null ?
                          brandRepository.findByName(tool.getBrand().getName()) :
                          brandRepository.findById(tool.getBrand().getId()))
                ).orElseGet(
                        () -> brandRepository.save(tool.getBrand())
                );
    }

    /**
     * Parse tool cities from either name or id
     *
     * @param tool Tool with cities
     * @return List of parsed cities
     */
    public List<City> parseToolCities(Tool tool) throws ResourceBadRequestException {
        return tool.getCities().stream().map(
                        e -> (e.getId() == null ?
                                      cityRepository.findByName(e.getName())
                                      : cityRepository.findById(e.getId())
                        ).orElseGet(() -> cityRepository.save(e)))
                       .toList();
    }

    /**
     * Add a new tool to the database
     * {@link edu.puj.toolmaster.tools.entities.Brand} and {@link edu.puj.toolmaster.tools.entities.City}
     * can be either specify an ID or a name, if record doesn't exist it will be created using name
     *
     * @param tool New tool to be added
     * @return Tool added or null if not a valid tool
     * @throws ResourceBadRequestException  Bad resource description
     * @throws EntityAlreadyExistsException The entity could not be added because it already exists
     */
    public Tool addNewTool(@NonNull Tool tool) throws ResourceBadRequestException, EntityAlreadyExistsException {
        try {
            // Get the parsed tool
            Tool fixedTool = parseTool(tool);

            // Find if tool already exists
            Optional<Tool> findTool = toolRepository.findOne(
                    Example.of(fixedTool, ExampleMatcher.matching()
                                                  .withIgnorePaths("id")));

            if (findTool.isPresent()) {
                throw new EntityAlreadyExistsException(findTool.get());
            }

            // Store it
            return toolRepository.save(fixedTool);

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            // Throws null pointer exception if brand or city could not be created
            throw new ResourceBadRequestException();
        }
    }

    /**
     * Delete a tool by it's id
     *
     * @param id ID of the tool to be deleted
     * @throws ResourceNotFoundException Resource doesn't exist
     */
    public void deleteToolById(Integer id) throws ResourceNotFoundException {
        toolRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        toolRepository.deleteById(id);
    }

    /**
     * Update a tool by it's ID
     *
     * @param id   ID of the tool to be updated
     * @param tool Tool with non-null fields
     * @return Updated tool
     * @throws ResourceBadRequestException Tool had null fields or parsing failed
     */
    public Tool updateToolById(Integer id, Tool tool) throws ResourceBadRequestException {
        try {
            // Find already existing tool
            Tool findTool = toolRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
            // Parse the new tool
            Tool parsedTool = parseTool(tool);
            // Save the changes
            return toolRepository.save(parsedTool.withId(findTool.getId()));
        } catch (Exception e) {
            // Throws null pointer exception if brand or city could not be created
            throw new ResourceBadRequestException();
        }
    }

    public Tool partialToolUpdateById(Integer id, Tool tool) throws ResourceBadRequestException {
        try {
            // Find already existing tool
            Tool findTool = toolRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
            // Build new tool overriding old fields
            Tool overrideTool = findTool.overrideWith(tool);
            // Parse the new tool
            if (tool.getCities() != null) {
                overrideTool = overrideTool.withCities(parseToolCities(overrideTool));
            }
            if (tool.getBrand() != null) {
                overrideTool = overrideTool.withBrand(parseToolBrand(overrideTool));
            }
            // Save the changes
            return toolRepository.save(overrideTool);
        } catch (Exception e) {
            // Throws null pointer exception if brand or city could not be created
            throw new ResourceBadRequestException();
        }
    }
}
