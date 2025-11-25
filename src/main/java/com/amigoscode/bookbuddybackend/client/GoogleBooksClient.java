////package com.amigoscode.bookbuddybackend.client;
////
////import org.springframework.cloud.openfeign.FeignClient;
////import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.PathVariable;
////import org.springframework.web.bind.annotation.RequestParam;
////
////@FeignClient(name = "open-library", url = "https://openlibrary.org")
////public interface OpenLibraryClient {
////
////    @GetMapping("/search.json")
////    String searchBooks(@RequestParam("q") String query);
////
////    @GetMapping("/works/{workId}.json")
////    String getBookByWorkId(@PathVariable("workId") String workId);
////
////    @GetMapping("/isbn/{isbn}.json")
////    String getBookByIsbn(@PathVariable("isbn") String isbn);
////}
//
//package com.amigoscode.bookbuddybackend.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(name = "google-books", url = "https://www.googleapis.com/books/v1")
//public interface GoogleBooksClient {
//
//    // Search books (q=keyword)
//    @GetMapping("/volumes")
//    String searchBooks(@RequestParam("q") String query);
//
//    // Get book by Google Books ID
//    @GetMapping("/volumes/{id}")
//    String getBookById(@PathVariable("id") String id);
//
//    // Search by ISBN (q=isbn:xxxx)
//    @GetMapping("/volumes")
//    String getBookByIsbn(@RequestParam("q") String isbnQuery);
//}


package com.amigoscode.bookbuddybackend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "google-books", url = "https://www.googleapis.com/books/v1")
public interface GoogleBooksClient {

    // Search books (q=keyword)
    @GetMapping("/volumes")
    String searchBooks(@RequestParam("q") String query);

    // Get book by Google Books ID
    @GetMapping("/volumes/{id}")
    String getBookById(@PathVariable("id") String id);

    // Search by ISBN (q=isbn:xxxx)
    @GetMapping("/volumes")
    String getBookByIsbn(@RequestParam("q") String isbnQuery);
}