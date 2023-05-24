package edu.puj.toolmaster.users.persistance;

import edu.puj.toolmaster.users.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio encargado de hacer la conexión con la base de datos para obtener información de la tabla City
 */
public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByName(String name);
}
