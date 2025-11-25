////package com.amigoscode.bookbuddybackend.controller;
////
////import com.amigoscode.bookbuddybackend.model.Book;
////import com.amigoscode.bookbuddybackend.service.BookService;
////import lombok.RequiredArgsConstructor;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.PostMapping;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RequestParam;
////import org.springframework.web.bind.annotation.RestController;
////import org.springframework.web.multipart.MultipartFile;
////
////import java.io.IOException;
////import java.time.LocalDate;
////
////@RestController
////@RequestMapping("/api/books")
////@RequiredArgsConstructor
////public class BookUploadController {
////
////    private final BookService bookService;
////
////    @PostMapping("/upload")
////    public ResponseEntity<Book> uploadBook(
////            @RequestParam("title") String title,
////            @RequestParam("author") String author,
////            @RequestParam("genre") String genre,
////            @RequestParam(value = "cover", required = false) MultipartFile cover,
////            @RequestParam("publicationDate") LocalDate publicationDate,
////            @RequestParam("language") String language,
////            @RequestParam("format") String format,
////            @RequestParam("isbn") String isbn,
////            @RequestParam("description") String description,
////            @RequestParam("link") String link) throws IOException {
////
////        Book savedBook = bookService.uploadBook(title, author, genre, cover, publicationDate, language, format, isbn, description, link);
////        return ResponseEntity.ok(savedBook);
////    }
////}
//
//// src/main/java/com/amigoscode/bookbuddybackend/controller/BookUploadController.java
//
//package com.amigoscode.bookbuddybackend.controller;
//
//import com.amigoscode.bookbuddybackend.model.Book;
//import com.amigoscode.bookbuddybackend.service.BookService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.UUID;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/books")
//@RequiredArgsConstructor
//public class BookUploadController {
//
//    private final BookService bookService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<Book> uploadBook(
//            @RequestParam("title") String title,
//            @RequestParam("author") String author,
//            @RequestParam("genre") String genre,
//            @RequestParam(value = "cover", required = false) MultipartFile cover,
//            @RequestParam("publicationDate") LocalDate publicationDate,
//            @RequestParam("language") String language,
//            @RequestParam("format") String format,
//            @RequestParam("isbn") String isbn,
//            @RequestParam("description") String description,
//            @RequestParam("link") String link,
//            Authentication authentication) throws IOException {  // ← Get logged-in user
//
//        String userId = authentication.getName(); // JWT subject = userId
//        log.debug("User {} uploading book: {}", userId, title);
//
//        // Use provided isbn, fallback to UUID if blank
//        String finalIsbn = (isbn == null || isbn.trim().isEmpty())
//                ? "custom-" + UUID.randomUUID().toString()
//                : isbn.trim();
//
//        Book savedBook = bookService.uploadBook(
//                title, author, genre, cover, publicationDate,
//                language, format, finalIsbn, description, link, userId);
//
//        return ResponseEntity.ok(savedBook);
//    }
//}


// src/main/java/com/amigoscode/bookbuddybackend/controller/BookUploadController.java

package com.amigoscode.bookbuddybackend.controller;

import com.amigoscode.bookbuddybackend.model.Book;
import com.amigoscode.bookbuddybackend.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookUploadController {

    private final BookService bookService;

    @PostMapping("/upload")
    public ResponseEntity<Book> uploadBook(
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("genre") String genre,
            @RequestParam(value = "cover", required = false) MultipartFile cover,
            @RequestParam("publicationDate") LocalDate publicationDate,
            @RequestParam("language") String language,
            @RequestParam("format") String format,
            @RequestParam("isbn") String isbn,
            @RequestParam("description") String description,
            @RequestParam("link") String link,
            Authentication authentication) throws IOException {

        String userId = authentication.getName();
        String cleanIsbn = isbn != null && !isbn.trim().isEmpty()
                ? isbn.trim()
                : "custom-" + UUID.randomUUID().toString();

        log.info("User {} uploading book with ISBN: {}", userId, cleanIsbn);

        Book savedBook = bookService.uploadBook(
                title, author, genre, cover, publicationDate,
                language, format, cleanIsbn, description, link, userId);

        return ResponseEntity.ok(savedBook);
    }
}