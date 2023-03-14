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

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtSecurity jwt;

    @GetMapping("/check")
    public String check() {
        return "Successfull authentication";
    }


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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
