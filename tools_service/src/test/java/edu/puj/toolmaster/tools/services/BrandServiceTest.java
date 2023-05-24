package edu.puj.toolmaster.tools.services;

import edu.puj.toolmaster.tools.entities.Brand;
import edu.puj.toolmaster.tools.persistance.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BrandServiceTest {

    private BrandService brandService;

    @MockBean
    private BrandRepository brandRepository;

    @BeforeEach
    public void setUp() {
        brandService = new BrandService(brandRepository);
    }

    @Test
    public void testGetAllBrands() {
        Brand brand1 = new Brand(1, "Brand 1");
        Brand brand2 = new Brand(2, "Brand 2");

        when(brandRepository.findAll()).thenReturn(Arrays.asList(brand1, brand2));

        List<Brand> result = brandService.getAllBrands();

        assertEquals(2, result.size());
        assertEquals("Brand 1", result.get(0).getName());
        assertEquals("Brand 2", result.get(1).getName());
    }
}