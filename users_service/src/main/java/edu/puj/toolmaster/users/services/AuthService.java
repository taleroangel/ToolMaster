package edu.puj.toolmaster.users.services;

import edu.puj.toolmaster.users.entities.auth.Auth;
import edu.puj.toolmaster.users.entities.auth.AuthDetails;
import edu.puj.toolmaster.users.persistance.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio encargado de obtener los recursos del repositorio de autenticación
 */
@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthRepository repository;

    /**
     * Obtener un usuario por su nombre de usuario
     * @param username Nombre de usuario
     * @return Usuario encontrado
     */
    public Optional<Auth> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    /**
     * Cargar un usuario por su nombre de usuario
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
