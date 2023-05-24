package edu.puj.toolmaster.auth.security.jwt;

import edu.puj.toolmaster.auth.entities.AuthDetails;
import edu.puj.toolmaster.auth.security.TokenGenerator;
import edu.puj.toolmaster.auth.security.TokenValidator;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Clase que implementa la verificación y creación de tokens JWT
 */
@Component
public class JwtSecurity implements TokenValidator, TokenGenerator {

    // 24 Hour expire duration
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;
    @Value("${security.jwt.secret}")
    private String SECRET_KEY;

    @Override
    public String generateAccessToken(UserDetails user) throws RuntimeException {
        AuthDetails auth = (AuthDetails) user;
        return Jwts.builder()
                       .setSubject(auth.getUsername())
                       .setIssuedAt(new Date())
                       .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                       .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                       .compact();
    }

    @Override
    public String validateToken(String token) throws RuntimeException {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
