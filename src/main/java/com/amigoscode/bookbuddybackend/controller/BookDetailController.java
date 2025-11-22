//package com.amigoscode.bookbuddybackend.controller;
//
//import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
//import com.amigoscode.bookbuddybackend.service.ExternalBookService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/books")
//public class BookDetailController {
//
//    private final ExternalBookService externalBookService;
//
//    public BookDetailController(ExternalBookService externalBookService) {
//        this.externalBookService = externalBookService;
//    }
//
//    @GetMapping(value = "/{bookId}", produces = "application/json")
//    public ResponseEntity<BookSearchResponse> getBookDetails(@PathVariable String bookId) {
//
//        BookSearchResponse response = externalBookService.getBookDetails(bookId);
//
//        // Handle case: book not found
//        if (response.getTitle() == null && response.getBookId() == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(response);
//    }
//}


package com.amigoscode.bookbuddybackend.controller;

import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
import com.amigoscode.bookbuddybackend.service.ExternalBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookDetailController {

    private final ExternalBookService externalBookService;

    public BookDetailController(ExternalBookService externalBookService) {
        this.externalBookService = externalBookService;
    }

    // GET detailed book info by ID (Work ID or ISBN)
    @GetMapping(value = "/{bookId}", produces = "application/json")
    public ResponseEntity<BookDetailResponse> getBookDetails(@PathVariable String bookId) {

        BookDetailResponse response = externalBookService.getBookDetails(bookId);

        // Handle case: book not found
        if (response.getTitle() == null && response.getBookId() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}
