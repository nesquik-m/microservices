package com.example.security;

import com.example.dto.response.ValidateTokenResponse;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    private final UserService userService;

    public String generateToken(UUID id, Duration expiration) {
        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .setClaims(getClaimsForToken(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration.toMillis()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private Map<String, Object> getClaimsForToken(UUID id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        return claims;
    }

    public Long getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return (claims.getExpiration().getTime() - System.currentTimeMillis()) / 1000;
    }

    public ValidateTokenResponse validateToken(String token) {
        try {
            token = token.replace("Bearer ", "");

            DefaultClaims claims = (DefaultClaims) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

            if (claims.containsKey("id")) {
                UUID userId = UUID.fromString(claims.get("id").toString());
                User user = userService.getUserById(userId);
                return new ValidateTokenResponse(userId, user.getRoles());
            } else {
                log.warn("JWT token doesn't contain userId: {}", token);
                return null;
            }
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return null;
    }

}