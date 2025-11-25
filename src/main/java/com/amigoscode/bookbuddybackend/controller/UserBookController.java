////////////package com.amigoscode.bookbuddybackend.controller;
////////////
////////////import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
////////////import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
////////////import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
////////////import com.amigoscode.bookbuddybackend.service.UserLibraryService;
////////////import org.springframework.http.ResponseEntity;
////////////import org.springframework.security.core.Authentication;
////////////import org.springframework.web.bind.annotation.*;
////////////
////////////@RestController
////////////@RequestMapping("/api/user-books")
////////////public class UserBookController {
////////////
////////////    private final UserLibraryService userLibraryService;
////////////
////////////    public UserBookController(UserLibraryService userLibraryService) {
////////////        this.userLibraryService = userLibraryService;
////////////    }
////////////
////////////    @PostMapping("/add")
////////////    public ResponseEntity<Void> addBook(@RequestBody AddBookRequest request, Authentication authentication) {
////////////        String userId = authentication.getName();
////////////        userLibraryService.addBookToShelf(userId, request);
////////////        return ResponseEntity.ok().build();
////////////    }
////////////
////////////    @PutMapping("/progress")
////////////    public ResponseEntity<Void> updateProgress(@RequestBody UpdateProgressRequest request, Authentication authentication) {
////////////        String userId = authentication.getName();
////////////        userLibraryService.updateProgress(userId, request);
////////////        return ResponseEntity.ok().build();
////////////    }
////////////
////////////    @PutMapping("/move-shelf/{bookId}")
////////////    public ResponseEntity<Void> moveShelf(@PathVariable String bookId, @RequestParam ShelfType newShelf, Authentication authentication) {
////////////        String userId = authentication.getName();
////////////        userLibraryService.moveBookToShelf(userId, bookId, newShelf);
////////////        return ResponseEntity.ok().build();
////////////    }
////////////}
//////////
//////////package com.amigoscode.bookbuddybackend.controller;
//////////
//////////import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
//////////import com.amigoscode.bookbuddybackend.dto.request.MoveShelfRequest;     // ← New DTO
//////////import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
//////////import com.amigoscode.bookbuddybackend.service.UserLibraryService;
//////////import jakarta.validation.Valid;
//////////import org.springframework.http.ResponseEntity;
//////////import org.springframework.security.core.Authentication;
//////////import org.springframework.web.bind.annotation.*;
//////////
//////////@RestController
//////////@RequestMapping("/api/user-books")
//////////public class UserBookController<MoveShelfRequest> {
//////////
//////////    private final UserLibraryService userLibraryService;
//////////
//////////    public UserBookController(UserLibraryService userLibraryService) {
//////////        this.userLibraryService = userLibraryService;
//////////    }
//////////
//////////    @PostMapping("/add")
//////////    public ResponseEntity<Void> addBook(@RequestBody AddBookRequest request, Authentication authentication) {
//////////        String userId = authentication.getName();
//////////        userLibraryService.addBookToShelf(userId, request);
//////////        return ResponseEntity.ok().build();
//////////    }
//////////
//////////    @PutMapping("/progress")
//////////    public ResponseEntity<Void> updateProgress(@RequestBody UpdateProgressRequest request, Authentication authentication) {
//////////        String userId = authentication.getName();
//////////        userLibraryService.updateProgress(userId, request);
//////////        return ResponseEntity.ok().build();
//////////    }
//////////
//////////    // ← NEW CLEAN SIGNATURE →
//////////    @PutMapping("/move-shelf/{bookId}")
//////////    public ResponseEntity<Void> moveShelf(
//////////            @PathVariable String bookId,
//////////            @Valid @RequestBody MoveShelfRequest request,
//////////            Authentication authentication) {
//////////
//////////        String userId = authentication.getName();
//////////        userLibraryService.moveBookToShelf(userId, bookId, request.newShelf());
//////////        return ResponseEntity.ok().build();
//////////    }
//////////}
////////
////////
////////package com.amigoscode.bookbuddybackend.controller;
////////
////////import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
////////import com.amigoscode.bookbuddybackend.dto.request.MoveShelfRequest;
////////import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
////////import com.amigoscode.bookbuddybackend.service.UserLibraryService;
////////import jakarta.validation.Valid;
////////import org.springframework.http.ResponseEntity;
////////import org.springframework.security.core.Authentication;
////////import org.springframework.web.bind.annotation.*;
////////
////////@RestController
////////@RequestMapping("/api/user-books")
////////public class UserBookController {
////////
////////    private final UserLibraryService userLibraryService;
////////
////////    public UserBookController(UserLibraryService userLibraryService) {
////////        this.userLibraryService = userLibraryService;
////////    }
////////
////////    @PostMapping("/add")
////////    public ResponseEntity<Void> addBook(@RequestBody AddBookRequest request, Authentication authentication) {
////////        String userId = authentication.getName();
////////        userLibraryService.addBookToShelf(userId, request);
////////        return ResponseEntity.ok().build();
////////    }
////////
////////    @PutMapping("/progress")
////////    public ResponseEntity<Void> updateProgress(@RequestBody UpdateProgressRequest request, Authentication authentication) {
////////        String userId = authentication.getName();
////////        userLibraryService.updateProgress(userId, request);
////////        return ResponseEntity.ok().build();
////////    }
////////
////////    @PutMapping("/move-shelf/{bookId}")
////////    public ResponseEntity<Void> moveShelf(
////////            @PathVariable String bookId,
////////            @Valid @RequestBody MoveShelfRequest request,
////////            Authentication authentication) {
////////
////////        String userId = authentication.getName();
////////        userLibraryService.moveBookToShelf(userId, bookId, request.newShelf());
////////        return ResponseEntity.ok().build();
////////    }
////////}
//////
//////
//////package com.amigoscode.bookbuddybackend.controller;
//////
//////import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
//////import com.amigoscode.bookbuddybackend.dto.request.MoveShelfRequest;
//////import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
//////import com.amigoscode.bookbuddybackend.service.UserLibraryService;
//////import jakarta.validation.Valid;
//////import lombok.extern.slf4j.Slf4j;
//////import org.springframework.http.ResponseEntity;
//////import org.springframework.security.core.Authentication;
//////import org.springframework.web.bind.annotation.*;
//////
//////@Slf4j
//////@RestController
//////@RequestMapping("/api/user-books")
//////public class UserBookController {
//////
//////    private final UserLibraryService userLibraryService;
//////
//////    public UserBookController(UserLibraryService userLibraryService) {
//////        this.userLibraryService = userLibraryService;
//////    }
//////
//////    @PostMapping("/add")
//////    public ResponseEntity<Void> addBook(@RequestBody AddBookRequest request, Authentication authentication) {
//////        String userId = authentication.getName();
//////        log.debug("📥 Adding book for userId {}: {}", userId, request);
//////
//////        try {
//////            userLibraryService.addBookToShelf(userId, request);
//////            log.debug("✅ Book added successfully for userId {}", userId);
//////            return ResponseEntity.ok().build();
//////        } catch (Exception e) {
//////            log.error("❌ Failed to add book for userId {}: {}", userId, e.getMessage(), e);
//////            return ResponseEntity.status(500).build();
//////        }
//////    }
//////
//////    @PutMapping("/progress")
//////    public ResponseEntity<Void> updateProgress(@RequestBody UpdateProgressRequest request, Authentication authentication) {
//////        String userId = authentication.getName();
//////        log.debug("📥 Updating progress for userId {}: {}", userId, request);
//////
//////        try {
//////            userLibraryService.updateProgress(userId, request);
//////            log.debug("✅ Progress updated successfully for userId {}", userId);
//////            return ResponseEntity.ok().build();
//////        } catch (Exception e) {
//////            log.error("❌ Failed to update progress for userId {}: {}", userId, e.getMessage(), e);
//////            return ResponseEntity.status(500).build();
//////        }
//////    }
//////
//////    @PutMapping("/move-shelf/{bookId}")
//////    public ResponseEntity<Void> moveShelf(
//////            @PathVariable String bookId,
//////            @Valid @RequestBody MoveShelfRequest request,
//////            Authentication authentication) {
//////
//////        String userId = authentication.getName();
//////        log.debug("📥 Moving book {} to shelf {} for userId {}", bookId, request.newShelf(), userId);
//////
//////        try {
//////            userLibraryService.moveBookToShelf(userId, bookId, request.newShelf());
//////            log.debug("✅ Book {} moved successfully for userId {}", bookId, userId);
//////            return ResponseEntity.ok().build();
//////        } catch (Exception e) {
//////            log.error("❌ Failed to move book {} for userId {}: {}", bookId, userId, e.getMessage(), e);
//////            return ResponseEntity.status(500).build();
//////        }
//////    }
//////}
////
////
////package com.amigoscode.bookbuddybackend.controller;
////
////import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
////import com.amigoscode.bookbuddybackend.dto.request.MoveShelfRequest;
////import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
////import com.amigoscode.bookbuddybackend.service.UserLibraryService;
////import jakarta.validation.Valid;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.security.core.Authentication;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.Map;
////
////@Slf4j
////@RestController
////@RequestMapping("/api/user-books")
////public class UserBookController {
////
////    private final UserLibraryService userLibraryService;
////
////    public UserBookController(UserLibraryService userLibraryService) {
////        this.userLibraryService = userLibraryService;
////    }
////
////    @PostMapping("/add")
////    public ResponseEntity<?> addBook(@RequestBody AddBookRequest request, Authentication authentication) {
////        String userId = authentication.getName();
////        log.debug("📥 Adding book for userId {}: {}", userId, request);
////
////        try {
////            userLibraryService.addBookToShelf(userId, request); // still void
////            log.debug("✅ Book added successfully for userId {}", userId);
////
////            return ResponseEntity.status(HttpStatus.CREATED)
////                    .body(Map.of(
////                            "message", "Book added successfully",
////                            "title", request.getTitle(),
////                            "bookId", request.getBookId(),
////                            "shelf", request.getShelf()
////                    ));
////        } catch (Exception e) {
////            log.error("❌ Failed to add book for userId {}: {}", userId, e.getMessage(), e);
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of(
////                            "error", "Failed to add book",
////                            "details", e.getMessage()
////                    ));
////        }
////    }
////
////    @PutMapping("/progress")
////    public ResponseEntity<?> updateProgress(@RequestBody UpdateProgressRequest request, Authentication authentication) {
////        String userId = authentication.getName();
////        log.debug("📥 Updating progress for userId {}: {}", userId, request);
////
////        try {
////            userLibraryService.updateProgress(userId, request);
////            log.debug("✅ Progress updated successfully for userId {}", userId);
////
////            return ResponseEntity.ok(Map.of(
////                    "message", "Progress updated successfully",
////                    "bookId", request.getBookId(),
////                    "progress", request.getProgress()
////            ));
////        } catch (Exception e) {
////            log.error("❌ Failed to update progress for userId {}: {}", userId, e.getMessage(), e);
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of(
////                            "error", "Failed to update progress",
////                            "details", e.getMessage()
////                    ));
////        }
////    }
////
////    @PutMapping("/move-shelf/{bookId}")
////    public ResponseEntity<?> moveShelf(
////            @PathVariable String bookId,
////            @Valid @RequestBody MoveShelfRequest request,
////            Authentication authentication) {
////
////        String userId = authentication.getName();
////        log.debug("📥 Moving book {} to shelf {} for userId {}", bookId, request.newShelf(), userId);
////
////        try {
////            userLibraryService.moveBookToShelf(userId, bookId, request.newShelf());
////            log.debug("✅ Book {} moved successfully for userId {}", bookId, userId);
////
////            return ResponseEntity.ok(Map.of(
////                    "message", "Book moved successfully",
////                    "bookId", bookId,
////                    "newShelf", request.newShelf()
////            ));
////        } catch (Exception e) {
////            log.error("❌ Failed to move book {} for userId {}: {}", bookId, userId, e.getMessage(), e);
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
////                    .body(Map.of(
////                            "error", "Failed to move book",
////                            "bookId", bookId,
////                            "details", e.getMessage()
////                    ));
////        }
////    }
////}
//
//package com.amigoscode.bookbuddybackend.controller;
//
//import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
//import com.amigoscode.bookbuddybackend.dto.request.MoveShelfRequest;
//import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
//import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
//import com.amigoscode.bookbuddybackend.service.UserLibraryService;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/user-books")
//public class UserBookController {
//
//    private final UserLibraryService userLibraryService;
//
//    public UserBookController(UserLibraryService userLibraryService) {
//        this.userLibraryService = userLibraryService;
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<?> addBook(@RequestBody AddBookRequest request, Authentication authentication) {
//        String userId = authentication.getName();
//        log.debug("📥 Adding book for userId {}: {}", userId, request);
//
//        try {
//            userLibraryService.addBookToShelf(userId, request); // still void
//            log.debug("✅ Book added successfully for userId {}", userId);
//
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .body(Map.of(
//                            "message", "Book added successfully",
//                            "bookId", request.getBookId(),
//                            "shelf", request.getShelfType()  // corrected
//                    ));
//        } catch (Exception e) {
//            log.error("❌ Failed to add book for userId {}: {}", userId, e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "error", "Failed to add book",
//                            "details", e.getMessage()
//                    ));
//        }
//    }
//
//    @PutMapping("/progress")
//    public ResponseEntity<?> updateProgress(@RequestBody UpdateProgressRequest request, Authentication authentication) {
//        String userId = authentication.getName();
//        log.debug("📥 Updating progress for userId {}: {}", userId, request);
//
//        try {
//            userLibraryService.updateProgress(userId, request);
//            log.debug("✅ Progress updated successfully for userId {}", userId);
//
//            return ResponseEntity.ok(Map.of(
//                    "message", "Progress updated successfully",
//                    "bookId", request.getBookId(),
//                    "progress", request.getProgress()
//            ));
//        } catch (Exception e) {
//            log.error("❌ Failed to update progress for userId {}: {}", userId, e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "error", "Failed to update progress",
//                            "details", e.getMessage()
//                    ));
//        }
//    }
//
//    @PutMapping("/move-shelf/{bookId}")
//    public ResponseEntity<?> moveShelf(
//            @PathVariable String bookId,
//            @Valid @RequestBody MoveShelfRequest request,
//            Authentication authentication) {
//
//        String userId = authentication.getName();
//        ShelfType newShelf = request.getNewShelf(); // corrected
//        log.debug("📥 Moving book {} to shelf {} for userId {}", bookId, newShelf, userId);
//
//        try {
//            userLibraryService.moveBookToShelf(userId, bookId, newShelf);
//            log.debug("✅ Book {} moved successfully for userId {}", bookId, userId);
//
//            return ResponseEntity.ok(Map.of(
//                    "message", "Book moved successfully",
//                    "bookId", bookId,
//                    "newShelf", newShelf
//            ));
//        } catch (Exception e) {
//            log.error("❌ Failed to move book {} for userId {}: {}", bookId, userId, e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of(
//                            "error", "Failed to move book",
//                            "bookId", bookId,
//                            "details", e.getMessage()
//                    ));
//        }
//    }
//}
//

package com.amigoscode.bookbuddybackend.controller;


import com.amigoscode.bookbuddybackend.service.UserLibraryService;
import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
import com.amigoscode.bookbuddybackend.dto.request.MoveShelfRequest;
import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
import com.amigoscode.bookbuddybackend.service.UserLibraryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user-books")
public class UserBookController {

    private final UserLibraryService userLibraryService;

    public UserBookController(UserLibraryService userLibraryService) {
        this.userLibraryService = userLibraryService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(
            @Valid @RequestBody AddBookRequest request,
            Authentication authentication) {

        String userId = authentication.getName();
        String bookId = request.bookId();   // record getter

        log.debug("Adding book {} to WANT_TO_READ shelf for userId {}", bookId, userId);

        try {
            // THIS IS THE CORRECT CALL NOW
            userLibraryService.addBookToWantToRead(userId, bookId);

            log.debug("Book {} successfully added to Want to Read for userId {}", bookId, userId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Book added successfully to your Want to Read shelf",
                            "bookId", bookId,
                            "shelf", ShelfType.WANT_TO_READ.name()
                    ));

        } catch (Exception e) {
            log.error("Failed to add book {} for userId {}: {}", bookId, userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to add book",
                            "bookId", bookId,
                            "details", e.getMessage()
                    ));
        }
    }

    @PutMapping("/progress")
    public ResponseEntity<?> updateProgress(
            @Valid @RequestBody UpdateProgressRequest request,
            Authentication authentication) {

        String userId = authentication.getName();

        log.debug("Updating progress for book {} (userId: {})", request.getBookId(), userId);

        try {
            userLibraryService.updateProgress(userId, request);

            return ResponseEntity.ok(Map.of(
                    "message", "Reading progress updated successfully",
                    "bookId", request.getBookId(),
                    "progress", request.getProgress()
            ));
        } catch (Exception e) {
            log.error("Failed to update progress for userId {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to update progress",
                            "details", e.getMessage()
                    ));
        }
    }
    // Add this method to your existing UserBookController.java



    @PutMapping("/move-shelf/{bookId}")
    public ResponseEntity<?> moveShelf(
            @PathVariable String bookId,
            @Valid @RequestBody MoveShelfRequest request,
            Authentication authentication) {

        String userId = authentication.getName();
        ShelfType newShelf = request.getNewShelf();

        log.debug("Moving book {} to shelf {} (userId: {})", bookId, newShelf, userId);

        try {
            userLibraryService.moveBookToShelf(userId, bookId, newShelf);

            return ResponseEntity.ok(Map.of(
                    "message", "Book moved successfully",
                    "bookId", bookId,
                    "newShelf", newShelf.name()
            ));
        } catch (Exception e) {
            log.error("Failed to move book {} for userId {}: {}", bookId, userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to move book",
                            "bookId", bookId,
                            "details", e.getMessage()
                    ));
        }
    }
}