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

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthRepository repository;

    public Optional<Auth> getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new AuthDetails(repository.findByUsername(username)
                                       .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
