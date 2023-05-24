package edu.puj.toolmaster.auth.services;

import edu.puj.toolmaster.auth.entities.Auth;
import edu.puj.toolmaster.auth.entities.AuthDetails;
import edu.puj.toolmaster.auth.persistance.AuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthRepository authRepository;

    private Auth auth;

    @BeforeEach
    public void setup() {
        auth = new Auth().withUsername("username");
    }

    @Test
    public void getUserByUsernameTest() {
        when(authRepository.findByUsername("username")).thenReturn(Optional.of(auth));
        Optional<Auth> result = authService.getUserByUsername("username");

        assertEquals(result.get().getUsername(), auth.getUsername());
    }

    @Test
    public void loadUserByUsernameTest() {
        when(authRepository.findByUsername("username")).thenReturn(Optional.of(auth));
        AuthDetails result = (AuthDetails) authService.loadUserByUsername("username");

        assertEquals(result.getUsername(), auth.getUsername());
    }

    @Test
    public void loadUserByUsernameNotFoundTest() {
        when(authRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.loadUserByUsername("username"));
    }
}