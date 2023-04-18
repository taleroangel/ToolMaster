package edu.puj.toolmaster.tools.controller;

import edu.puj.toolmaster.tools.entities.City;
import edu.puj.toolmaster.tools.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService service;

    @GetMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<City> getAllCities() {
        return service.getAllCities();
    }
}
