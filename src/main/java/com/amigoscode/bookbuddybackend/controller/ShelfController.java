//package com.amigoscode.bookbuddybackend.controller;
//
//import com.amigoscode.bookbuddybackend.dto.response.ShelfResponse;
//import com.amigoscode.bookbuddybackend.service.UserLibraryService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/shelves")
//public class ShelfController {
//
//    private final UserLibraryService userLibraryService;
//
//    public ShelfController(UserLibraryService userLibraryService) {
//        this.userLibraryService = userLibraryService;
//    }
//
//    @GetMapping
//    public ResponseEntity<ShelfResponse> getShelves(Authentication authentication) {
//        String userId = authentication.getName(); // Assuming JWT subject is userId
//        return ResponseEntity.ok(userLibraryService.getShelves(userId));
//    }
//}

package com.amigoscode.bookbuddybackend.controller;

import com.amigoscode.bookbuddybackend.dto.response.ShelfResponse;
import com.amigoscode.bookbuddybackend.service.UserLibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/shelves")
public class ShelfController {

    private final UserLibraryService userLibraryService;

    public ShelfController(UserLibraryService userLibraryService) {
        this.userLibraryService = userLibraryService;
    }

    @GetMapping
    public ResponseEntity<ShelfResponse> getShelves(Authentication authentication) {
        String userId = authentication.getName(); // JWT subject as userId
        log.debug("📥 /api/shelves called by userId: {}", userId);

        try {
            ShelfResponse response = userLibraryService.getShelves(userId);
            if (response == null) {
                log.warn("⚠️ No shelves found for userId: {}", userId);
                return ResponseEntity.notFound().build();
            }
            log.debug("✅ Shelves retrieved successfully for userId: {}", userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("❌ Error fetching shelves for userId {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
}
