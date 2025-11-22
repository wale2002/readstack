//////package com.amigoscode.bookbuddybackend.controller;
//////
//////import com.amigoscode.bookbuddybackend.dto.request.ReviewRequest;
//////import com.amigoscode.bookbuddybackend.service.UserLibraryService;
//////import org.springframework.http.ResponseEntity;
//////import org.springframework.security.core.Authentication;
//////import org.springframework.web.bind.annotation.*;
//////
//////@RestController
//////@RequestMapping("/api/reviews")
//////public class ReviewController {
//////
//////    private final UserLibraryService userLibraryService;
//////
//////    public ReviewController(UserLibraryService userLibraryService) {
//////        this.userLibraryService = userLibraryService;
//////    }
//////
//////    @PostMapping
//////    public ResponseEntity<Void> addReview(@RequestBody ReviewRequest request, Authentication authentication) {
//////        String userId = authentication.getName();
//////        userLibraryService.addReview(userId, request);
//////        return ResponseEntity.ok().build();
//////    }
//////
//////    @PutMapping("/{reviewId}")
//////    public ResponseEntity<Void> updateReview(@PathVariable String reviewId, @RequestBody ReviewRequest request, Authentication authentication) {
//////        String userId = authentication.getName();
//////        userLibraryService.updateReview(userId, reviewId, request);
//////        return ResponseEntity.ok().build();
//////    }
//////
//////    @DeleteMapping("/{reviewId}")
//////    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId, Authentication authentication) {
//////        String userId = authentication.getName();
//////        userLibraryService.deleteReview(userId, reviewId);
//////        return ResponseEntity.ok().build();
//////    }
//////}
////
////
////package com.amigoscode.bookbuddybackend.controller;
////
////import com.amigoscode.bookbuddybackend.dto.request.ReviewRequest;
////import com.amigoscode.bookbuddybackend.service.UserLibraryService;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.http.ResponseEntity;
////import org.springframework.security.core.Authentication;
////import org.springframework.web.bind.annotation.*;
////
////@Slf4j
////@RestController
////@RequestMapping("/api/reviews")
////public class ReviewController {
////
////    private final UserLibraryService userLibraryService;
////
////    public ReviewController(UserLibraryService userLibraryService) {
////        this.userLibraryService = userLibraryService;
////    }
////
////    @PostMapping
////    public ResponseEntity<Void> addReview(@RequestBody ReviewRequest request, Authentication authentication) {
////        String userId = authentication.getName();
////        log.debug("📥 Add review called by userId: {} for bookId: {}", userId, request.getBookId());
////        try {
////            userLibraryService.addReview(userId, request);
////            log.debug("✅ Review added by userId: {} for bookId: {}", userId, request.getBookId());
////            return ResponseEntity.ok().build();
////        } catch (Exception e) {
////            log.error("❌ Failed to add review by userId {}: {}", userId, e.getMessage(), e);
////            return ResponseEntity.status(500).build();
////        }
////    }
////
////    @PutMapping("/{reviewId}")
////    public ResponseEntity<Void> updateReview(@PathVariable String reviewId,
////                                             @RequestBody ReviewRequest request,
////                                             Authentication authentication) {
////        String userId = authentication.getName();
////        log.debug("📥 Update review called by userId: {} for reviewId: {}", userId, reviewId);
////        try {
////            userLibraryService.updateReview(userId, reviewId, request);
////            log.debug("✅ Review updated by userId: {} for reviewId: {}", userId, reviewId);
////            return ResponseEntity.ok().build();
////        } catch (Exception e) {
////            log.error("❌ Failed to update reviewId {} by userId {}: {}", reviewId, userId, e.getMessage(), e);
////            return ResponseEntity.status(500).build();
////        }
////    }
////
////    @DeleteMapping("/{reviewId}")
////    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId, Authentication authentication) {
////        String userId = authentication.getName();
////        log.debug("📥 Delete review called by userId: {} for reviewId: {}", userId, reviewId);
////        try {
////            userLibraryService.deleteReview(userId, reviewId);
////            log.debug("✅ Review deleted by userId: {} for reviewId: {}", userId, reviewId);
////            return ResponseEntity.ok().build();
////        } catch (Exception e) {
////            log.error("❌ Failed to delete reviewId {} by userId {}: {}", reviewId, userId, e.getMessage(), e);
////            return ResponseEntity.status(500).build();
////        }
////    }
////}
//
//
//
//package com.amigoscode.bookbuddybackend.controller;
//
//import com.amigoscode.bookbuddybackend.dto.request.ReviewRequest;
//import com.amigoscode.bookbuddybackend.service.UserLibraryService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/reviews")
//public class ReviewController {
//
//    private final UserLibraryService userLibraryService;
//
//    public ReviewController(UserLibraryService userLibraryService) {
//        this.userLibraryService = userLibraryService;
//    }
//
//    @PostMapping
//    public ResponseEntity<Void> addReview(@RequestBody ReviewRequest request, Authentication authentication) {
//        String userId = authentication.getName();
//        log.debug("📥 Add review called by userId: {} for bookId: {}", userId, request.getBookId());
//        try {
//            userLibraryService.addReview(userId, request);
//            log.debug("✅ Review added by userId: {} for bookId: {}", userId, request.getBookId());
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            log.error("❌ Failed to add review by userId {}: {}", userId, e.getMessage(), e);
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//    @PutMapping("/{reviewId}")
//    public ResponseEntity<Void> updateReview(@PathVariable String reviewId,
//                                             @RequestBody ReviewRequest request,
//                                             Authentication authentication) {
//        String userId = authentication.getName();
//        log.debug("📥 Update review called by userId: {} for reviewId: {}", userId, reviewId);
//        try {
//            userLibraryService.updateReview(userId, reviewId, request);
//            log.debug("✅ Review updated by userId: {} for reviewId: {}", userId, reviewId);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            log.error("❌ Failed to update reviewId {} by userId {}: {}", reviewId, userId, e.getMessage(), e);
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//    @DeleteMapping("/{reviewId}")
//    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId, Authentication authentication) {
//        String userId = authentication.getName();
//        log.debug("📥 Delete review called by userId: {} for reviewId: {}", userId, reviewId);
//        try {
//            userLibraryService.deleteReview(userId, reviewId);
//            log.debug("✅ Review deleted by userId: {} for reviewId: {}", userId, reviewId);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            log.error("❌ Failed to delete reviewId {} by userId {}: {}", reviewId, userId, e.getMessage(), e);
//            return ResponseEntity.status(500).build();
//        }
//    }
//}


package com.amigoscode.bookbuddybackend.controller;

import com.amigoscode.bookbuddybackend.dto.request.ReviewRequest;
import com.amigoscode.bookbuddybackend.service.UserLibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final UserLibraryService userLibraryService;

    public ReviewController(UserLibraryService userLibraryService) {
        this.userLibraryService = userLibraryService;
    }

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest request, Authentication authentication) {
        String userId = authentication.getName();
        log.debug("📥 Add review called by userId: {} for bookId: {}", userId, request.getBookId());

        try {
            userLibraryService.addReview(userId, request);
            log.debug("✅ Review added by userId: {} for bookId: {}", userId, request.getBookId());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Review added successfully",
                            "bookId", request.getBookId(),
                            "rating", request.getRating(),
                            "review", request.getReviewText()
                    ));
        } catch (Exception e) {
            log.error("❌ Failed to add review by userId {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to add review",
                            "details", e.getMessage()
                    ));
        }
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable String reviewId,
                                          @RequestBody ReviewRequest request,
                                          Authentication authentication) {
        String userId = authentication.getName();
        log.debug("📥 Update review called by userId: {} for reviewId: {}", userId, reviewId);

        try {
            userLibraryService.updateReview(userId, reviewId, request);
            log.debug("✅ Review updated by userId: {} for reviewId: {}", userId, reviewId);

            return ResponseEntity.ok(Map.of(
                    "message", "Review updated successfully",
                    "reviewId", reviewId,
                    "bookId", request.getBookId(),
                    "rating", request.getRating(),
                    "review", request.getReviewText()
            ));
        } catch (Exception e) {
            log.error("❌ Failed to update reviewId {} by userId {}: {}", reviewId, userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to update review",
                            "reviewId", reviewId,
                            "details", e.getMessage()
                    ));
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable String reviewId, Authentication authentication) {
        String userId = authentication.getName();
        log.debug("📥 Delete review called by userId: {} for reviewId: {}", userId, reviewId);

        try {
            userLibraryService.deleteReview(userId, reviewId);
            log.debug("✅ Review deleted by userId: {} for reviewId: {}", userId, reviewId);

            return ResponseEntity.ok(Map.of(
                    "message", "Review deleted successfully",
                    "reviewId", reviewId
            ));
        } catch (Exception e) {
            log.error("❌ Failed to delete reviewId {} by userId {}: {}", reviewId, userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to delete review",
                            "reviewId", reviewId,
                            "details", e.getMessage()
                    ));
        }
    }
}
