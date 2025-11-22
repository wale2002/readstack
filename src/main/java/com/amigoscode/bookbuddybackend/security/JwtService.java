package com.amigoscode.bookbuddybackend.security;

import com.amigoscode.bookbuddybackend.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    private final JwtConfig jwtConfig;
    private final SecretKey key;
    private final JwtParser jwtParser;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        log.debug("🔐 Loading JWT Secret...");
        this.key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());

        log.debug("🔧 Building JWT Parser...");
        this.jwtParser = Jwts.parser()
                .verifyWith(key)
                .build();

        log.info("✅ JwtService initialized successfully");
    }

    // Extract userId (subject)
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract email from claims
    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e) {
            log.error("❌ extractClaim() failed: {}", e.getMessage());
            return null;
        }
    }

    // Generate token using userId as subject, email as claim
    public String generateToken(String userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        log.debug("🛠 Generating token for userId: {}, email: {}", userId, email);
        try {
            String token = Jwts.builder()
                    .claims(claims)
                    .subject(userId)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + jwtConfig.getExpirationMs()))
                    .signWith(key)
                    .compact();

            log.debug("✅ Token generated successfully");
            return token;
        } catch (Exception e) {
            log.error("❌ Failed to generate token: {}", e.getMessage());
            return null;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String email = extractEmail(token);

            if (email == null) {
                log.warn("⚠ Token email is NULL — invalid token");
                return false;
            }
            if (!email.equals(userDetails.getUsername())) {
                log.warn("⚠ Token email ({}) does NOT match user email ({})", email, userDetails.getUsername());
                return false;
            }
            if (isTokenExpired(token)) {
                log.warn("⚠ Token has EXPIRED");
                return false;
            }

            log.debug("✅ Token is valid for {}", email);
            return true;
        } catch (Exception e) {
            log.error("❌ Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractClaim(token, Claims::getExpiration).before(new Date());
        } catch (ExpiredJwtException e) {
            log.warn("⌛ Token expired (ExpiredJwtException)");
            return true;
        } catch (Exception e) {
            log.error("❌ Failed to check expiration: {}", e.getMessage());
            return true;
        }
    }

    private Claims extractAllClaims(String token) {
        try {
            log.debug("🔍 Parsing JWT claims...");
            Claims claims = jwtParser.parseSignedClaims(token).getPayload();
            log.debug("📦 JWT Claims extracted successfully");
            return claims;
        } catch (ExpiredJwtException e) {
            log.warn("⌛ Token expired during claims extraction");
            throw e;
        } catch (JwtException e) {
            log.error("❌ Invalid JWT signature/structure: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("❌ Failed to parse JWT: {}", e.getMessage());
            throw e;
        }
    }
}