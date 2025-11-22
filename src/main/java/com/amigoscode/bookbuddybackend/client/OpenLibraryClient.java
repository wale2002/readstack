package com.amigoscode.bookbuddybackend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "open-library", url = "https://openlibrary.org")
public interface OpenLibraryClient {

    @GetMapping("/search.json")
    String searchBooks(@RequestParam("q") String query);

    @GetMapping("/works/{workId}.json")
    String getBookByWorkId(@PathVariable("workId") String workId);

    @GetMapping("/isbn/{isbn}.json")
    String getBookByIsbn(@PathVariable("isbn") String isbn);
}