package crmfitness.project.jwt.service;

import crmfitness.project.jwt.data.JWTToken;
import crmfitness.project.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public Long extractUserId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public JWTToken generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public JWTToken generateToken(Map<String, Object> extraClaims, User userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    private JWTToken buildToken(Map<String, Object> extraClaims, User userDetails, long expiration) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);

        return new JWTToken(
                Jwts.builder()
                        .setClaims(extraClaims)
                        .setSubject(String.valueOf(userDetails.getId()))
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(expirationDate)
                        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                        .compact(),
                expirationDate.getTime());
    }

    public boolean isTokenValid(String token, User user) {
        final Long userId = extractUserId(token);
        return userId != null && userId.equals(user.getId()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
