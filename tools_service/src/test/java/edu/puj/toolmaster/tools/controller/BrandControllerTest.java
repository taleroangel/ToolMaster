package edu.puj.toolmaster.tools.controller;

import edu.puj.toolmaster.tools.controller.BrandController;
import edu.puj.toolmaster.tools.entities.Brand;
import edu.puj.toolmaster.tools.services.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService service;

    private List<Brand> brands;

    @BeforeEach
    public void setUp() {
        this.brands = Arrays.asList(
                new Brand(1, "Brand 1"),
                new Brand(2, "Brand 2")
        );
    }

    @Test
    public void testGetAllBrands() throws Exception {
        given(service.getAllBrands()).willReturn(brands);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/brands/")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\": 1,\"name\": \"Brand 1\"},{\"id\": 2,\"name\": \"Brand 2\"}]"))
                .andDo(print());
    }
}