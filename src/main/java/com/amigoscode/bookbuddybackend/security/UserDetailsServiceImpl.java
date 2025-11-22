//package com.amigoscode.bookbuddybackend.security;
//
//import com.amigoscode.bookbuddybackend.model.User;
//import com.amigoscode.bookbuddybackend.repository.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public UserDetailsServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        log.debug("🔍 UserDetailsService: Attempting to load user by email: {}", username);
//
//        try {
//            User user = userRepository.findByEmail(username)
//                    .orElseThrow(() -> {
//                        log.warn("❗ User not found in DB for email: {}", username);
//                        return new UsernameNotFoundException("User not found with email: " + username);
//                    });
//
//            log.debug("✅ User found: {} (id: {})", user.getEmail(), user.getId());
//
//            return org.springframework.security.core.userdetails.User
//                    .withUsername(user.getEmail())
//                    .password(user.getPassword())
//                    .authorities("USER")
//                    .build();
//
//        } catch (UsernameNotFoundException e) {
//            throw e;  // already logged above
//
//        } catch (Exception e) {
//            log.error("❌ Unexpected error in loadUserByUsername: {}", e.getMessage());
//            throw new UsernameNotFoundException("Error loading user: " + username, e);
//        }
//    }
//}



package com.amigoscode.bookbuddybackend.security;

import com.amigoscode.bookbuddybackend.model.User;
import com.amigoscode.bookbuddybackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("🔍 UserDetailsService: Attempting to load user by email: {}", username);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    log.warn("❗ User not found in DB for email: {}", username);
                    return new UsernameNotFoundException("User not found with email: " + username);
                });

        log.debug("✅ User found: {} (id: {})", user.getEmail(), user.getId());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}
