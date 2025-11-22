////package com.amigoscode.bookbuddybackend.security;
////
////import jakarta.servlet.FilterChain;
////import jakarta.servlet.ServletException;
////import jakarta.servlet.http.HttpServletRequest;
////import jakarta.servlet.http.HttpServletResponse;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.security.core.userdetails.UserDetails;
////import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
////import org.springframework.stereotype.Component;
////import org.springframework.web.filter.OncePerRequestFilter;
////
////import java.io.IOException;
////
////@Slf4j
////@Component
////public class JwtAuthenticationFilter extends OncePerRequestFilter {
////
////    private final JwtService jwtService;
////    private final UserDetailsServiceImpl userDetailsService;
////
////    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
////        this.jwtService = jwtService;
////        this.userDetailsService = userDetailsService;
////    }
////
////    @Override
////    protected void doFilterInternal(HttpServletRequest request,
////                                    HttpServletResponse response,
////                                    FilterChain filterChain)
////            throws ServletException, IOException {
////
////        String authHeader = request.getHeader("Authorization");
////
////        // 🔵 Log incoming request
////        log.debug("➡ Incoming request: {} {}", request.getMethod(), request.getRequestURI());
////
////        if (authHeader == null) {
////            log.debug("❗ No Authorization header found");
////            filterChain.doFilter(request, response);
////            return;
////        }
////
////        if (!authHeader.startsWith("Bearer ")) {
////            log.debug("❗ Authorization header found but does NOT start with Bearer");
////            filterChain.doFilter(request, response);
////            return;
////        }
////
////        String jwt = authHeader.substring(7);
////        log.debug("🔐 JWT received: {}", jwt);
////
////        String email = null;
////        try {
////            email = jwtService.extractEmail(jwt);
////            log.debug("📨 Extracted email from JWT: {}", email);
////        } catch (Exception e) {
////            log.error("❌ Failed to extract email from token: {}", e.getMessage());
////            filterChain.doFilter(request, response);
////            return;
////        }
////
////        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////
////            try {
////                log.debug("🔎 Loading user by email: {}", email);
////                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
////
////                if (jwtService.isTokenValid(jwt, userDetails)) {
////                    log.debug("✅ Token is valid — authentication set for {}", email);
////
////                    UsernamePasswordAuthenticationToken authToken =
////                            new UsernamePasswordAuthenticationToken(
////                                    userDetails,
////                                    null,
////                                    userDetails.getAuthorities()
////                            );
////
////                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////                    SecurityContextHolder.getContext().setAuthentication(authToken);
////                } else {
////                    log.warn("⚠ Token is NOT valid for user: {}", email);
////                }
////
////            } catch (Exception e) {
////                log.error("❌ Authentication error: {}", e.getMessage());
////            }
////        } else {
////            if (email == null) {
////                log.warn("⚠ Email extracted from token is NULL");
////            }
////            if (SecurityContextHolder.getContext().getAuthentication() != null) {
////                log.debug("ℹ SecurityContext already has authentication — skipping");
////            }
////        }
////
////        filterChain.doFilter(request, response);
////    }
////}
//
//
//package com.amigoscode.bookbuddybackend.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UserDetailsServiceImpl userDetailsService;
//
//    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
//        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String jwt = authHeader.substring(7);
//
//        String userId = jwtService.extractUserId(jwt);
//        String email  = jwtService.extractEmail(jwt);
//
//        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//
//            if (jwtService.isTokenValid(jwt, userDetails)) {
//
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                userDetails.getAuthorities()
//                        );
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}


package com.amigoscode.bookbuddybackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        String userId = jwtService.extractUserId(jwt);
        String email = jwtService.extractEmail(jwt);

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                // Store DB userId in request attribute for controllers
                request.setAttribute("userId", userId);
            }
        }

        filterChain.doFilter(request, response);
    }
}
