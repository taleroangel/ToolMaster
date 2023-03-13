package edu.puj.toolmaster.tools.services;

import edu.puj.toolmaster.tools.entities.Brand;
import edu.puj.toolmaster.tools.persistance.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository repository;

    public List<Brand> getAllBrands() {
        return repository.findAll();
    }
}
