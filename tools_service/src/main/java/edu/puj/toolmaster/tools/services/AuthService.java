package edu.puj.toolmaster.tools.services;
import edu.puj.toolmaster.tools.entities.auth.Auth;
import edu.puj.toolmaster.tools.entities.auth.AuthDetails;
import edu.puj.toolmaster.tools.persistance.AuthRepository;
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

    private final AuthRepository repository;

    public AuthService(AuthRepository repository) {
        this.repository = repository;
    }

    public Optional<Auth> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    /**
     * Obtener los detalles de un usuario dado su nombre de usuario
     * @param username Nombre de usuario
     * @return Detalles del usuario
     * @throws UsernameNotFoundException El usuario no existe
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AuthDetails(repository.findByUsername(username)
                                       .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
