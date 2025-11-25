// src/main/java/com/amigoscode/bookbuddybackend/repository/BookRepository.java
package com.amigoscode.bookbuddybackend.repository;

import com.amigoscode.bookbuddybackend.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByIsbnIn(List<String> isbnList);
    Book findByIsbn(String isbn);
}