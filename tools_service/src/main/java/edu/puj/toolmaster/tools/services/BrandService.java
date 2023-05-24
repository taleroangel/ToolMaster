package edu.puj.toolmaster.tools.services;

import edu.puj.toolmaster.tools.entities.Brand;
import edu.puj.toolmaster.tools.persistance.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de marcas
 */
@Service
public class BrandService {
    final private BrandRepository repository;

    public BrandService(BrandRepository repository) {
        this.repository = repository;
    }

    /**
     * Obtener todas las marcas
     * @return Lista con todas las marcas
     */
    public List<Brand> getAllBrands() {
        return repository.findAll();
    }
}
