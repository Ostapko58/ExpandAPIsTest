package com.onyshkiv.expandapistest.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${secret_key}")
    private String secret_key;
    @Value("${token.access.expiration}")
    private long accessTokenExpiration;

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String generateToken(UserDetails userDetails) {
        return buildToken(userDetails, accessTokenExpiration);
    }



    private String buildToken(UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.printf("Invalid JWT signature: {%s}\n", e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.printf("Invalid JWT token: {%s}\n", e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.printf("JWT token is expired: {%s}\n", e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.printf("JWT token is unsupported: {%s}\n", e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.printf("JWT claims string is empty: {%s}\n", e.getMessage());
        }
        return false;
    }

    public boolean isValidUser(String token, UserDetails userDetails) {
        validateJwtToken(token);
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));

    }

    private SecretKey getSecretKey() {
        byte[] bytes = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(bytes);
    }


}
