package edu.puj.toolmaster.tools.services;

import edu.puj.toolmaster.tools.entities.auth.Auth;
import edu.puj.toolmaster.tools.entities.auth.AuthDetails;
import edu.puj.toolmaster.tools.persistance.AuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;

    private AuthService authService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(authRepository);
    }

    @Test
    public void testGetUserByUsername() {
        String testUsername = "test";
        Auth auth = new Auth();
        auth = auth.withUsername(testUsername);
        when(authRepository.findByUsername(testUsername)).thenReturn(Optional.of(auth));

        Optional<Auth> returnedAuth = authService.getUserByUsername(testUsername);

        assertTrue(returnedAuth.isPresent());
        assertEquals(auth.getUsername(), returnedAuth.get().getUsername());
        verify(authRepository, times(1)).findByUsername(testUsername);
    }

    @Test
    public void testLoadUserByUsername() {
        String testUsername = "test";
        Auth auth = new Auth();
        auth = auth.withUsername(testUsername);
        when(authRepository.findByUsername(testUsername)).thenReturn(Optional.of(auth));

        AuthDetails authDetails = (AuthDetails) authService.loadUserByUsername(testUsername);

        assertNotNull(authDetails);
        assertEquals(auth.getUsername(), authDetails.getUsername());
        verify(authRepository, times(1)).findByUsername(testUsername);
    }

    @Test
    public void testLoadUserByUsernameThrowsException() {
        String testUsername = "test";
        when(authRepository.findByUsername(testUsername)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            authService.loadUserByUsername(testUsername);
        });

        verify(authRepository, times(1)).findByUsername(testUsername);
    }
}