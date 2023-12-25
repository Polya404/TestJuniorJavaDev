package org.example.test.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public boolean isInvalidToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}

