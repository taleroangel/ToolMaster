package edu.puj.toolmaster.tools.services;

import edu.puj.toolmaster.tools.entities.City;
import edu.puj.toolmaster.tools.persistance.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    final private CityRepository repository;

    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public List<City> getAllCities() {
        return repository.findAll();
    }
}
