package com.amigoscode.bookbuddybackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String title;
    private String author;
    private String genre;
    private String coverUrl; // URL from Cloudinary
    private LocalDate publicationDate;
    private String language;
    private String format; // e.g., "517 pages, hardcover"
    private String isbn;
    private String description;
    private String link; // External link
}