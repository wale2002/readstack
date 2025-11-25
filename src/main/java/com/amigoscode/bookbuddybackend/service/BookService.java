// src/main/java/com/amigoscode/bookbuddybackend/service/BookService.java
package com.amigoscode.bookbuddybackend.service;

import com.amigoscode.bookbuddybackend.model.Book;
import com.amigoscode.bookbuddybackend.model.User;
import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
import com.amigoscode.bookbuddybackend.repository.BookRepository;
import com.amigoscode.bookbuddybackend.repository.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final Cloudinary cloudinary;

    public Book uploadBook(
            String title, String author, String genre, MultipartFile cover,
            LocalDate publicationDate, String language, String format,
            String isbn, String description, String link,
            String uploadedByUserId) throws IOException {

        if (cover != null && !cover.isEmpty()) {
            String type = cover.getContentType();
            if (type == null || !type.startsWith("image/")) {
                throw new IllegalArgumentException("Cover must be an image");
            }
            String ext = type.toLowerCase().split("/")[1];
            if (!List.of("jpeg", "jpg", "png", "webp").contains(ext)) {
                throw new IllegalArgumentException("Cover must be JPEG, PNG or WEBP");
            }
            if (cover.getSize() > 300 * 1024) {
                throw new IllegalArgumentException("Cover must be ≤ 300KB");
            }
        }

        String coverUrl = null;
        if (cover != null && !cover.isEmpty()) {
            @SuppressWarnings("unchecked")
            Map<String, Object> result = cloudinary.uploader().upload(
                    cover.getBytes(),
                    ObjectUtils.asMap("upload_preset", "cmp_projects")
            );
            coverUrl = (String) result.get("secure_url");
        }

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setCoverUrl(coverUrl);
        book.setPublicationDate(publicationDate);
        book.setLanguage(language);
        book.setFormat(format);
        book.setDescription(description);
        book.setLink(link);

        // CRITICAL FIX: Always ensure ISBN exists
        String finalIsbn = (isbn != null && !isbn.trim().isBlank())
                ? isbn.trim()
                : "CUSTOM-" + new ObjectId().toHexString();  // e.g. CUSTOM-6718a1b2c9e4d8f109c8e7a1

        if (bookRepository.findByIsbn(finalIsbn) != null) {
            throw new IllegalArgumentException("A book with this identifier already exists");
        }

        book.setIsbn(finalIsbn);

        Book savedBook = bookRepository.save(book);
        log.info("Custom book saved with ISBN: {}", savedBook.getIsbn());

        addToUserLibrary(uploadedByUserId, savedBook);

        return savedBook;
    }

    private void addToUserLibrary(String userId, Book book) {
        if (userId == null || userId.isBlank()) return;

        userRepository.findById(userId).ifPresent(user -> {
            String isbn = book.getIsbn();

            boolean exists = user.getUserBooks().stream()
                    .anyMatch(ub -> isbn.equals(ub.getBookId()));

            if (!exists) {
                UserBook ub = new UserBook();
                ub.setBookId(isbn);                    // ← Now always ISBN!
                ub.setShelfType(ShelfType.WANT_TO_READ);
                ub.setTitle(book.getTitle());
                ub.setCoverImageUrl(book.getCoverUrl());

                user.getUserBooks().add(ub);
                userRepository.save(user);
                log.info("Custom book '{}' (ISBN: {}) added to user {}'s shelf", book.getTitle(), isbn, userId);
            }
        });
    }
}