package edu.puj.toolmaster.auth.controller;

import edu.puj.toolmaster.auth.entities.AuthDetails;
import edu.puj.toolmaster.auth.entities.AuthRequest;
import edu.puj.toolmaster.auth.security.jwt.JwtSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST de autenticación
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtSecurity jwt;

    /**
     * Verificar si está correctamente autenticado
     *
     * @return Siempre retorna éxito, el filtro de seguridad denegará la respuesta pues esta ruta está protegida
     */
    @GetMapping("/check")
    public String check() {
        return "Successful authentication";
    }


    /**
     * Generar un token JWT mediante el login
     *
     * @param request petición de autenticación con nombre de usuario y contraseña
     * @return Repuesta HTTP
     */
    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // Generar autenticación
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            AuthDetails user = (AuthDetails) auth.getPrincipal();
            String accessToken = jwt.generateAccessToken(user);
            return ResponseEntity.ok(new Object() {
                public final String username = user.getUsername();
                public final String token = accessToken;
            });
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
