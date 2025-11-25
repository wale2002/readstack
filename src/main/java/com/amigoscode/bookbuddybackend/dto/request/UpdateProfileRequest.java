//// src/main/java/com/amigoscode/bookbuddybackend/dto/request/UpdateProfileRequest.java
//package com.amigoscode.bookbuddybackend.dto.request;
//
//import jakarta.validation.constraints.Size;
//
//public record UpdateProfileRequest(
//
//        @Size(max = 50, message = "Name cannot exceed 50 characters")
//        String name,
//
//        @Size(max = 160, message = "Bio cannot exceed 160 characters")
//        String bio,
//
//        String avatarUrl // optional: URL to profile picture
//
//) { }