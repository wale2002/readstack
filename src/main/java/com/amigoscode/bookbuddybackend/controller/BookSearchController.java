//package com.amigoscode.bookbuddybackend.controller;
//
//import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
//import com.amigoscode.bookbuddybackend.service.ExternalBookService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/books")
//public class BookSearchController {
//
//    private final ExternalBookService externalBookService;
//
//    public BookSearchController(ExternalBookService externalBookService) {
//        this.externalBookService = externalBookService;
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<BookSearchResponse> searchBooks(@RequestParam("q") String query) {
//        return ResponseEntity.ok(externalBookService.searchBooks(query));
//    }
//}

package com.amigoscode.bookbuddybackend.controller;import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
import com.amigoscode.bookbuddybackend.service.ExternalBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;@RestController

@RequestMapping
        ("/api/books")
public class BookSearchController {private final ExternalBookService externalBookService;

    public BookSearchController(ExternalBookService externalBookService) {
        this.externalBookService = externalBookService;
    }

    @GetMapping("/search")
    public ResponseEntity<BookSearchResponse> searchBooks(@RequestParam("q") String query) {
        return ResponseEntity.ok(externalBookService.searchBooks(query));
    }}

