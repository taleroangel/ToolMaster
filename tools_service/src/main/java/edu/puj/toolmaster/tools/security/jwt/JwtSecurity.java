package edu.puj.toolmaster.tools.security.jwt;

import edu.puj.toolmaster.tools.security.TokenValidator;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtSecurity implements TokenValidator {
    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    @Override
    public String validateToken(String token) throws RuntimeException {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
