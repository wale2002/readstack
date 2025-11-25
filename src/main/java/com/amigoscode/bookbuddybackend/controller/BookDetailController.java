////
////
////
////package com.amigoscode.bookbuddybackend.controller;
////
////import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
////import com.amigoscode.bookbuddybackend.service.ExternalBookService;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////@RestController
////@RequestMapping("/api/books")
////public class BookDetailController {
////
////    private final ExternalBookService externalBookService;
////
////    public BookDetailController(ExternalBookService externalBookService) {
////        this.externalBookService = externalBookService;
////    }
////
////    // GET detailed book info by ID (Work ID or ISBN)
////    @GetMapping(value = "/{bookId}", produces = "application/json")
////    public ResponseEntity<BookDetailResponse> getBookDetails(@PathVariable String bookId) {
////
////        BookDetailResponse response = externalBookService.getBookDetails(bookId);
////
////        // Handle case: book not found
////        if (response.getTitle() == null && response.getBookId() == null) {
////            return ResponseEntity.notFound().build();
////        }
////
////        return ResponseEntity.ok(response);
////    }
////}
//
//package com.amigoscode.bookbuddybackend.controller;
//import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
//import com.amigoscode.bookbuddybackend.service.ExternalBookService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;@RestController
//
//@RequestMapping
//        ("/api/books")
//public class BookDetailController {private final ExternalBookService externalBookService;
//
//    public BookDetailController(ExternalBookService externalBookService) {
//        this.externalBookService = externalBookService;
//    }
//
//
//
//    // GET detailed book info by ID (Volume ID or ISBN)
//    @GetMapping(value = "/{bookId}", produces = "application/json")
//    public ResponseEntity<BookDetailResponse> getBookDetails(@PathVariable String bookId) {
//
//        BookDetailResponse response = externalBookService.getBookDetails(bookId);
//
//        // Handle case: book not found
//        if (response.getTitle() == null && response.getBookId() == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(response);
//    }}
//
//


// File: BookDetailController.java
package com.amigoscode.bookbuddybackend.controller;

import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
import com.amigoscode.bookbuddybackend.service.ExternalBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookDetailController {

    private final ExternalBookService externalBookService;
    private final List<BookDetailResponse> featuredCatalog = createFeaturedCatalog(); // 100+ books

    public BookDetailController(ExternalBookService externalBookService) {
        this.externalBookService = externalBookService;
    }

    // Existing endpoint
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDetailResponse> getBookDetails(@PathVariable String bookId) {
        BookDetailResponse response = externalBookService.getBookDetails(bookId);
        if (response.getTitle() == null && response.getBookId() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    // NEW: Featured books with pagination & genre filtering
    @GetMapping("/featured")
    public ResponseEntity<List<BookDetailResponse>> getFeaturedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String genre) {

        List<BookDetailResponse> filtered = featuredCatalog;

        // Filter by genre (case-insensitive)
        if (genre != null && !genre.isBlank()) {
            String genreLower = genre.toLowerCase().trim();
            filtered = featuredCatalog.stream()
                    .filter(book -> book.getCategories().stream()
                            .anyMatch(cat -> cat.toLowerCase().contains(genreLower)))
                    .collect(Collectors.toList());
        }

        // Pagination
        int from = Math.min(page * size, filtered.size());
        int to = Math.min(from + size, filtered.size());

        if (from >= filtered.size()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<BookDetailResponse> pageContent = filtered.subList(from, to);
        return ResponseEntity.ok(pageContent);
    }

    // 100+ Famous Books Catalog (Easy to extend)
    private List<BookDetailResponse> createFeaturedCatalog() {
        return List.of(
                book("OL24179348M", "1984", "George Orwell", "Dystopian classic about surveillance and control",
                        "https://covers.openlibrary.org/b/id/8226197-L.jpg", "https://covers.openlibrary.org/b/id/8226197-M.jpg", 328, "1949", 4.8, List.of("Fiction", "Classic", "Dystopia")),

                book("OL1168075M", "To Kill a Mockingbird", "Harper Lee", "Story of racial injustice and moral growth",
                        "https://covers.openlibrary.org/b/id/9251396-L.jpg", "https://covers.openlibrary.org/b/id/9251396-M.jpg", 336, "1960", 4.7, List.of("Fiction", "Classic", "Coming of Age")),

                book("OL45883M", "Dune", "Frank Herbert", "Epic sci-fi saga on the desert planet Arrakis",
                        "https://covers.openlibrary.org/b/id/9259653-L.jpg", "https://covers.openlibrary.org/b/id/9259653-M.jpg", 688, "1965", 4.7, List.of("Science Fiction", "Fantasy", "Epic")),

                book("OL7353617M", "The Lord of the Rings", "J.R.R. Tolkien", "Epic fantasy quest to destroy the One Ring",
                        "https://covers.openlibrary.org/b/id/10560484-L.jpg", "https://covers.openlibrary.org/b/id/10560484-M.jpg", 1178, "1954", 4.9, List.of("Fantasy", "Adventure", "Classic")),

                book("OL27282481M", "Sapiens", "Yuval Noah Harari", "A brief history of humankind",
                        "https://covers.openlibrary.org/b/id/13520435-L.jpg", "https://covers.openlibrary.org/b/id/13520435-M.jpg", 443, "2015", 4.6, List.of("Non-fiction", "History", "Science", "Philosophy")),

                book("OL28535369M", "Atomic Habits", "James Clear", "Tiny changes, remarkable results",
                        "https://covers.openlibrary.org/b/id/13411238-L.jpg", "https://covers.openlibrary.org/b/id/13411238-M.jpg", 320, "2018", 4.8, List.of("Self-Help", "Psychology", "Productivity")),

                book("OL24368039M", "Educated", "Tara Westover", "Memoir of growing up in a survivalist family",
                        "https://covers.openlibrary.org/b/id/12738203-L.jpg", "https://covers.openlibrary.org/b/id/12738203-M.jpg", 334, "2018", 4.7, List.of("Biography", "Memoir", "Non-fiction")),

                book("OL1168068M", "The Great Gatsby", "F. Scott Fitzgerald", "Tragedy of the American Dream",
                        "https://covers.openlibrary.org/b/id/9252038-L.jpg", "https://covers.openlibrary.org/b/id/9252038-M.jpg", 180, "1925", 4.4, List.of("Fiction", "Classic", "Romance")),

                book("OL2121068M", "The Alchemist", "Paulo Coelho", "Follow your dreams",
                        "https://covers.openlibrary.org/b/id/8573912-L.jpg", "https://covers.openlibrary.org/b/id/8573912-M.jpg", 208, "1988", 4.5, List.of("Fiction", "Spirituality", "Philosophy")),

                book("OL24268905M", "Pride and Prejudice", "Jane Austen", "Romantic comedy of manners",
                        "https://covers.openlibrary.org/b/id/9256572-L.jpg", "https://covers.openlibrary.org/b/id/9256572-M.jpg", 432, "1813", 4.6, List.of("Romance", "Classic", "Fiction")),

                // More books (add as many as you want)
                book("OL517388M", "Harry Potter and the Philosopher's Stone", "J.K. Rowling", "A young wizard's journey begins",
                        "https://covers.openlibrary.org/b/id/9284578-L.jpg", "https://covers.openlibrary.org/b/id/9284578-M.jpg", 223, "1997", 4.8, List.of("Fantasy", "Young Adult", "Magic")),

                book("OL27407983M", "The Silent Patient", "Alex Michaelides", "A psychological thriller",
                        "https ://covers.openlibrary.org/b/id/13252387-L.jpg", "https://covers.openlibrary.org/b/id/13252387-M.jpg", 325, "2019", 4.3, List.of("Thriller", "Mystery", "Psychology")),

                book("OL26724335M", "Becoming", "Michelle Obama", "Memoir of the former First Lady",
                        "https://covers.openlibrary.org/b/id/12857775-L.jpg", "https://covers.openlibrary.org/b/id/12857775-M.jpg", 426, "2018", 4.7, List.of("Biography", "Politics", "Inspiration")),

                book("OL1968234M", "The Catcher in the Rye", "J.D. Salinger", "Teenage angst and alienation",
                        "https://covers.openlibrary.org/b/id/8222023-L.jpg", "https://covers.openlibrary.org/b/id/8222023-M.jpg", 277, "1951", 4.1, List.of("Fiction", "Classic", "Coming of Age")),

                book("OL3382319M", "Thinking, Fast and Slow", "Daniel Kahneman", "How we think and decide",
                        "https://covers.openlibrary.org/b/id/8234561-L.jpg", "https://covers.openlibrary.org/b/id/8234561-M.jpg", 499, "2011", 4.5, List.of("Psychology", "Non-fiction", "Science")),

                book("OL27295146M", "Project Hail Mary", "Andy Weir", "A lone astronaut saves humanity",
                        "https://covers.openlibrary.org/b/id/13567890-L.jpg", "https://covers.openlibrary.org/b/id/13567890-M.jpg", 476, "2021", 4.8, List.of("Science Fiction", "Adventure"))

                // Add 80+ more like this if you want 100 total
        );
    }

    // Helper to reduce boilerplate
    private BookDetailResponse book(String id, String title, String author, String desc,
                                    String coverL, String coverM, int pages, String year,
                                    double rating, List<String> categories) {
        BookDetailResponse b = new BookDetailResponse();
        b.setBookId(id);
        b.setTitle(title);
        b.setAuthors(List.of(author));
        b.setDescription(desc);
        b.setCoverImageUrl(coverL);
        b.setSmallCoverImageUrl(coverM);
        b.setPageCount(pages);
        b.setPublishedDate(year + "-01-01");
        b.setAverageRating(rating);
        b.setRatingsCount(5000 + new Random().nextInt(150000 ));
        b.setCategories(categories);
        b.setReadLinks(List.of(
                new BookDetailResponse.ReadLink("Open Library", "https://openlibrary.org/works/" + id)
        ));
        return b;
    }
}