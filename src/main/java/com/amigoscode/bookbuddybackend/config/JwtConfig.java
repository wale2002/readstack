//package com.amigoscode.bookbuddybackend.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ConfigurationProperties(prefix = "jwt")
//public class JwtConfig {
//
//    private String secret;
//    private long expirationMs;
//
//    public String getSecret() {
//        return secret;
//    }
//
//    public void setSecret(String secret) {
//        this.secret = secret;
//    }
//
//    public long getExpirationMs() {
//        return expirationMs;
//    }
//
//    public void setExpirationMs(long expirationMs) {
//        this.expirationMs = expirationMs;
//    }
//}

// src/main/java/com/amigoscode/bookbuddybackend/config/JwtConfig.java
package com.amigoscode.bookbuddybackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data  // ← THIS IS THE KEY! You removed this → app died
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secret;
    private long expirationMs = 86400000L; // default fallback

    // Lombok @Data generates getters/setters automatically
}