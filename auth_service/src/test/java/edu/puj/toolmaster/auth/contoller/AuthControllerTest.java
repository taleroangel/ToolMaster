package edu.puj.toolmaster.auth.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.puj.toolmaster.auth.entities.Auth;
import edu.puj.toolmaster.auth.entities.AuthDetails;
import edu.puj.toolmaster.auth.entities.AuthRequest;
import edu.puj.toolmaster.auth.security.jwt.JwtSecurity;
import edu.puj.toolmaster.auth.controller.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AuthControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JwtSecurity jwtSecurity;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testLoginSuccess() throws Exception {
        AuthRequest authRequest =
                new AuthRequest("username", "password");

        when(authManager.authenticate(new UsernamePasswordAuthenticationToken("username", "password"))).thenReturn(authentication);
        when(jwtSecurity.generateAccessToken(any())).thenReturn("testToken");
        when(authentication.getPrincipal()).thenReturn(new AuthDetails(new Auth(1L, "username", "password")));

        mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(authRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginFailed() throws Exception {
        AuthRequest authRequest = new AuthRequest("username", "password");
        when(authManager.authenticate(new UsernamePasswordAuthenticationToken("username", "password"))).thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(authRequest)))
                .andExpect(status().isUnauthorized());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}