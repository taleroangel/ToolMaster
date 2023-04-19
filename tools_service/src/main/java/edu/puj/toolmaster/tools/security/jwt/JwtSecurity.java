package edu.puj.toolmaster.tools.security.jwt;

import edu.puj.toolmaster.tools.security.TokenValidator;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Clase que controla la seguridad mediante tokens de JWT
 */
@Component
public class JwtSecurity implements TokenValidator {
    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    /**
     * Validar un token JWT
     * @param token Token a validar
     * @return Usuario dueño del token
     * @throws RuntimeException El token no es válido
     */
    @Override
    public String validateToken(String token) throws RuntimeException {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
