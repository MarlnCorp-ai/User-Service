package marln.corp.ai.service.marln_user_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    public static final long JWT_TWOKEN_VALIDITY = 5*60*60;
    public static final String SECRET = "1234567890987654321IamSantiagoMunezandiliketoeat5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
   // private final Key secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String getUserNameFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
    {
      final Claims claims = getAllClaimsFromToken(token);
      return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(getSignKey()) // Use the secure signing key
                .build()                   // Build the parser
                .parseClaimsJws(token)     // Parse and validate the token
                .getBody();                // Retrieve claims from the token
    }

    private Boolean isTokenExpired(String token)
    {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails)
    {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ JWT_TWOKEN_VALIDITY * 1000))
                .signWith(getSignKey(),SignatureAlgorithm.HS512).compact();
    }

    public
    Boolean validateToken(String token, UserDetails userDetails)
    {
       // final String userName = getUserNameFromToken(token);
        return(/*userName.equals(userDetails.getUsername()) && */!isTokenExpired(token));
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

