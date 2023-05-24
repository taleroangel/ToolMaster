package edu.puj.toolmaster.tools.services;

import edu.puj.toolmaster.tools.entities.City;
import edu.puj.toolmaster.tools.persistance.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CityServiceTest {

    private CityService cityService;

    @MockBean
    private CityRepository cityRepository;

    @BeforeEach
    public void setUp() {
        cityService = new CityService(cityRepository);
    }

    @Test
    public void testGetAllCities() {
        City city1 = new City(1, "City 1");
        City city2 = new City(2, "City 2");

        when(cityRepository.findAll()).thenReturn(Arrays.asList(city1, city2));

        List<City> result = cityService.getAllCities();

        assertEquals(2, result.size());
        assertEquals("City 1", result.get(0).getName());
        assertEquals("City 2", result.get(1).getName());
    }
}