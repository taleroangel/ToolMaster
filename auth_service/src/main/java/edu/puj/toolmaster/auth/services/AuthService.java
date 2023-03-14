package edu.puj.toolmaster.auth.services;

import edu.puj.toolmaster.auth.entities.Auth;
import edu.puj.toolmaster.auth.entities.AuthDetails;
import edu.puj.toolmaster.auth.persistance.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
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
