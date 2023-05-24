package edu.puj.toolmaster.tools.services;

import edu.puj.toolmaster.tools.entities.Brand;
import edu.puj.toolmaster.tools.entities.City;
import edu.puj.toolmaster.tools.entities.Tool;
import edu.puj.toolmaster.tools.persistance.BrandRepository;
import edu.puj.toolmaster.tools.persistance.CityRepository;
import edu.puj.toolmaster.tools.persistance.ToolRepository;
import edu.puj.toolmaster.tools.exceptions.EntityAlreadyExistsException;
import edu.puj.toolmaster.tools.exceptions.ResourceBadRequestException;
import edu.puj.toolmaster.tools.exceptions.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ToolServiceTest {

    private ToolService toolService;

    @MockBean
    private ToolRepository toolRepository;

    @MockBean
    private BrandRepository brandRepository;

    @MockBean
    private CityRepository cityRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        toolService = new ToolService();

        ReflectionTestUtils.setField(toolService, "toolRepository", toolRepository);
        ReflectionTestUtils.setField(toolService, "brandRepository", brandRepository);
        ReflectionTestUtils.setField(toolService, "cityRepository", cityRepository);
    }

    @Test
    public void testGetAllTools() {
        Tool tool1 = new Tool().withId(1).withName("Tool 1");
        Tool tool2 = new Tool().withId(2).withName("Tool 2");

        Pageable pageable = PageRequest.of(0, 5);
        when(toolRepository.findAll(pageable)).thenReturn(new PageImpl<>(Arrays.asList(tool1, tool2)));

        Page<Tool> result = toolService.getAllTools(pageable);

        assertEquals(2, result.getContent().size());
        assertEquals("Tool 1", result.getContent().get(0).getName());
        assertEquals("Tool 2", result.getContent().get(1).getName());
    }

    @Test
    public void testGetToolById() throws ResourceNotFoundException {
        Tool tool1 = new Tool().withId(1).withName("Tool 1");
        when(toolRepository.findById(1)).thenReturn(Optional.of(tool1));
        Tool result = toolService.getToolById(1);
        assertEquals("Tool 1", result.getName());
    }

    @Test
    public void testAddNewTool() throws ResourceBadRequestException, EntityAlreadyExistsException {
        Brand brand = new Brand(1, "Brand");
        City city = new City(1, "Bogotá");
        Tool tool1 = new Tool().withName("Tool 1").withBrand(brand).withCities(List.of(city));

        when(toolRepository.save(Mockito.any(Tool.class))).thenReturn(tool1);
        when(brandRepository.findById(Mockito.any())).thenReturn(Optional.of(brand));
        when(cityRepository.findById(Mockito.any())).thenReturn(Optional.of(city));


        Tool result = toolService.addNewTool(tool1);

        assertEquals("Tool 1", result.getName());
    }

    @Test
    public void testAddNewTool_AlreadyExists() throws ResourceBadRequestException, EntityAlreadyExistsException {
        Brand brand = new Brand(1, "Brand");
        City city = new City(1, "Bogotá");
        Tool tool1 = new Tool().withName("Tool 1").withBrand(brand).withCities(List.of(city));

        when(toolRepository.save(Mockito.any(Tool.class))).thenThrow(new EntityAlreadyExistsException(tool1));

        assertThrows(EntityAlreadyExistsException.class, () -> {
            toolService.addNewTool(tool1);
        });
    }

    @Test
    public void testToolByNameike() {
        Tool tool1 = new Tool().withId(1).withName("Tool 1");

        Pageable pageable = PageRequest.of(0, 5);
        when(toolRepository.findAll(Mockito.any(Specification.class), Mockito.eq(pageable))).thenReturn(new PageImpl<>(Collections.singletonList(tool1)));

        Page<Tool> result = toolService.toolByNameLike("Tool 1", pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("Tool 1", result.getContent().get(0).getName());
    }

    @Test
    public void testDeleteToolById() throws ResourceNotFoundException {
        Tool tool1 = new Tool();

        when(toolRepository.findById(Mockito.any())).thenReturn(Optional.of(tool1));
        doNothing().when(toolRepository).deleteById(1);

        assertDoesNotThrow(() -> toolService.deleteToolById(1));
    }

    @Test
    public void testDeleteToolById_ResourceNotFound() {
        when(toolRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> toolService.deleteToolById(1));
    }

    @Test
    public void testUpdateToolById() throws ResourceBadRequestException {

        Brand brand = new Brand(1, "Brand");
        City city = new City(1, "Bogotá");
        Tool tool1 = new Tool().withName("Tool 1").withBrand(brand).withCities(List.of(city));

        when(toolRepository.findById(1)).thenReturn(Optional.of(tool1));
        when(toolRepository.save(Mockito.any(Tool.class))).thenReturn(tool1);

        Tool result = toolService.updateToolById(1, tool1);

        assertEquals("Tool 1", result.getName());
    }

    @Test
    public void testUpdateToolById_ResourceNotFound() throws ResourceBadRequestException {

        Brand brand = new Brand(1, "Brand");
        City city = new City(1, "Bogotá");
        Tool tool1 = new Tool().withName("Tool 1").withBrand(brand).withCities(List.of(city));

        when(toolRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> toolService.updateToolById(1, tool1));
    }

    @Test
    public void testPartialToolUpdateById() {
        // Given
        Tool tool = new Tool().withId(1).withName("Tool 1").withBrand(new Brand(1, "Brand 1")).withCities(List.of(new City(1, "City 1")));
        Tool updatedTool = new Tool().withId(2).withName("Updated Tool").withBrand(new Brand(2, "Brand 2")).withCities(List.of(new City(2, "City 2")));

        // When
        when(toolRepository.findById(1)).thenReturn(Optional.of(tool));
        when(toolRepository.save(Mockito.any(Tool.class))).thenReturn(updatedTool);

        // Then
        try {
            Tool result = toolService.partialToolUpdateById(1, updatedTool);
            assertEquals("Updated Tool", result.getName());
            assertEquals("Brand 2", result.getBrand().getName());
            assertEquals("City 2", result.getCities().get(0).getName());

        } catch (ResourceBadRequestException e) {
            fail("ResourceBadRequestException thrown");
        }
    }

    @Test
    public void testToolByBrand() {
        Brand brand = new Brand(1, "Brand");
        City city = new City(1, "Bogotá");
        Tool tool1 = new Tool().withName("Tool 1").withBrand(brand).withCities(List.of(city));

        when(toolRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(new PageImpl(List.of(tool1)));

        var tools = toolService.toolByBrand("Brand", Pageable.ofSize(1));

        assertTrue(tools.getContent().contains(tool1));
    }

    @Test
    public void parseToolBrand_withNullId() {

        var brand = new Brand(null, "Brand");
        var tool = new Tool().withBrand(brand);
        var expectBrand = new Brand(1, "Brand");

        when(brandRepository.findByName("Brand")).thenReturn(Optional.of(expectBrand));

        assertEquals(toolService.parseToolBrand(tool), expectBrand);
    }

    @Test
    public void parseToolCity_withNullId() {

        var city = new City(null, "City");
        var tool = new Tool().withCities(List.of(city));
        var expectCity = new City(1, "City");

        when(cityRepository.findByName("City")).thenReturn(Optional.of(expectCity));

        assertTrue(toolService.parseToolCities(tool).contains(expectCity));
    }

    @Test
    public void addNewTool_throwsEntityAlreadyExistsException() {
        var tool = new Tool().withBrand(new Brand(1, "Brand")).withCities(List.of(new City(1, "City")));

        when(toolRepository.findOne(Mockito.any())).thenReturn(Optional.of(tool));

        assertThrows(EntityAlreadyExistsException.class, () -> toolService.addNewTool(tool));
    }

    @Test
    public void addNewTool_throwsResourceBadRequestException() {
        var tool = new Tool();
        assertThrows(ResourceBadRequestException.class, () -> toolService.addNewTool(tool));
    }

    @Test
    public void updateToolById_throwsResourceBadRequestException() {
        var tool = new Tool().withId(1);
        when(toolRepository.findById(1)).thenReturn(Optional.of(tool));
        assertThrows(ResourceBadRequestException.class, () -> toolService.updateToolById(1, tool));
    }

    @Test
    public void partialToolUpdateById_throwsResourceBadRequestException() {
        var tool = new Tool().withId(1);
        when(toolRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceBadRequestException.class, () -> toolService.partialToolUpdateById(1, tool));
    }

}