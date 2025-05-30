package marln.corp.ai.service.marln_user_service.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JwtUtil {
    private final String secret = "secretKeyForJwtSigningWhichIsAtLeast256BitsLong";
    private final Key signingKey = Keys.hmacShaKeyFor(secret.getBytes());

    public String generateToken(String email, Set<String> roles) {
        Claims claims = (Claims) claims();
        claims.put("roles", roles);

        return builder()
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1-hour validity
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(signingKey) // Set the signing key
                .build() // Build the parser
                .parseClaimsJws(token) // Parse and validate the token
                .getBody(); // Get claims from the validated token
    }
}