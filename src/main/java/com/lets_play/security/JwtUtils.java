package com.lets_play.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    // In a real app, keep this in application.properties!
    private final String jwtSecret = "P6KeU8oGNyubb2J6J0qRuJrLVF9WKIYJdTBfVWxWkMg";
    private final int jwtExpirationMs = 86400000; // 24 hours

    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Log the error (expired, invalid, etc.)
        }
        return false;
    }
}