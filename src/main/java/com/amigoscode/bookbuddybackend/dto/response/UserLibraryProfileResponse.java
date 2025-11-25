// File: src/main/java/com/amigoscode/bookbuddybackend/dto/response/UserLibraryProfileResponse.java
package com.amigoscode.bookbuddybackend.dto.response;

import java.util.Map;

public record UserLibraryProfileResponse(
        String userId,
        String displayName,                    // optional: username or email
        Map<String, Integer> shelvesCount,     // e.g. "WANT_TO_READ": 12
        CurrentlyReading currentlyReading,     // optional detailed current book
        LibraryStats stats
) {
    public record CurrentlyReading(
            String bookId,
            String title,
            String author,
            String coverImageUrl,
            int progressPercentage,
            int currentPage,
            int totalPages
    ) {}

    public record LibraryStats(
            int totalBooks,
            int booksRead,
            int pagesRead,
            double averageRatingGiven,  // if you store user ratings
            String favoriteGenre
    ) {}
}