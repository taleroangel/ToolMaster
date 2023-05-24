package edu.puj.toolmaster.users;

import edu.puj.toolmaster.users.entities.auth.Auth;
import edu.puj.toolmaster.users.persistance.AuthRepository;
import edu.puj.toolmaster.users.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class AuthServiceTest {

    @InjectMocks
    AuthService authService;

    @Mock
    AuthRepository authRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Esta prueba verifica que el método getUserByUsername del servicio devuelve
     * un objeto Optional<Auth> que contiene el usuario esperado. Creamos un objeto
     * Auth y configuramos Mockito para que devuelva ese objeto cuando se llama al
     * método findByUsername del repositorio. Luego, llamamos a getUserByUsername
     * y verificamos que el resultado contiene el usuario que esperamos.
     */
    @Test
    public void testGetUserByUsername() {
        Auth auth = new Auth();
        when(authRepository.findByUsername(anyString())).thenReturn(Optional.of(auth));
        Optional<Auth> result = authService.getUserByUsername("test");
        assertTrue(result.isPresent());
        assertEquals(auth, result.get());
    }

    /**
     * Esta prueba verifica que el método loadUserByUsername lanza una excepción
     * UsernameNotFoundException cuando no se encuentra ningún usuario.
     * Configuramos Mockito para que devuelva un Optional.empty() cuando se llama
     * a findByUsername. Luego, llamamos a loadUserByUsername y verificamos que se
     * lanza una excepción UsernameNotFoundException
     */
    @Test
    public void testLoadUserByUsernameNotFound() {
        when(authRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> authService.loadUserByUsername("test"));
    }
}