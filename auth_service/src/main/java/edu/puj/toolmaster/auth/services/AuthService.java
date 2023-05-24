package edu.puj.toolmaster.auth.services;

import edu.puj.toolmaster.auth.entities.Auth;
import edu.puj.toolmaster.auth.entities.AuthDetails;
import edu.puj.toolmaster.auth.persistance.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio de autenticación
 */
@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthRepository repository;

    /**
     * Obtener información de autenticación de un usuario por su nombre de usuario
     * @param username Nombre de usuario
     * @return Información de autenticación
     */
    public Optional<Auth> getAuthByUsername(String username) {
        return repository.findByUsername(username);
    }

    /**
     * Cargar datos del usuario dado su nombre de usuario
     * @param username Nombre de usuario
     * @return Datos del usuario
     * @throws UsernameNotFoundException Nombre de usuario no encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AuthDetails(repository.findByUsername(username)
                                       .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
