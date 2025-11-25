//package com.amigoscode.bookbuddybackend.config;
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Map;
//
//@Configuration
//public class CloudinaryConfig {
//
//    @Value("${cloudinary.cloud-name}")
//    private String cloudName;
//
//    @Value("${cloudinary.api-key}")
//    private String apiKey;
//
//    @Value("${cloudinary.api-secret}")
//    private String apiSecret;
//
//    @Bean
//    public Cloudinary cloudinary() {
//        Map<String, Object> config = ObjectUtils.asMap(
//                "cloud_name", cloudName,
//                "api_key", apiKey,
//                "api_secret", apiSecret
//        );
//        return new Cloudinary(config);
//    }
//}

// src/main/java/com/amigoscode/bookbuddybackend/config/CloudinaryConfig.java

package com.amigoscode.bookbuddybackend.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Value("${cloudinary.upload-preset:cmp_projects}") // fallback if not set
    private String uploadPreset;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        config.put("secure", true);           // Forces HTTPS
        config.put("cdn_subdomain", true);    // Better performance
        // config.put("upload_preset", uploadPreset); // optional – usually set on frontend

        return new Cloudinary(config);
    }
}