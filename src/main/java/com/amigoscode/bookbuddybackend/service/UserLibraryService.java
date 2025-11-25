//////////
//////////
//////////
//////////package com.amigoscode.bookbuddybackend.service;
//////////
//////////import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
//////////import com.amigoscode.bookbuddybackend.dto.request.ReviewRequest;
//////////import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
//////////import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
//////////import com.amigoscode.bookbuddybackend.dto.response.ShelfResponse;
//////////import com.amigoscode.bookbuddybackend.model.User;
//////////import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
//////////import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
//////////import com.amigoscode.bookbuddybackend.repository.UserRepository;
//////////import lombok.extern.slf4j.Slf4j;
//////////import org.springframework.stereotype.Service;
//////////
//////////import java.util.*;
//////////import java.util.stream.Collectors;
//////////
//////////@Slf4j
//////////@Service
//////////public class UserLibraryService {
//////////
//////////    private final UserRepository userRepository;
//////////    private final ExternalBookService externalBookService;
//////////
//////////    public UserLibraryService(UserRepository userRepository,
//////////                              ExternalBookService externalBookService) {
//////////        this.userRepository = userRepository;
//////////        this.externalBookService = externalBookService;
//////////    }
//////////
//////////    private User findUserByIdOrEmail(String idOrEmail) {
//////////        Optional<User> byId = userRepository.findById(idOrEmail);
//////////        if (byId.isPresent()) return byId.get();
//////////
//////////        Optional<User> byEmail = userRepository.findByEmail(idOrEmail);
//////////        if (byEmail.isPresent()) return byEmail.get();
//////////
//////////        throw new IllegalArgumentException("User not found: " + idOrEmail);
//////////    }
//////////
//////////    private String normalizeBookId(String bookId) {
//////////        if (bookId == null) return null;
//////////        return bookId.trim();
//////////    }
//////////
//////////    public ShelfResponse getShelves(String userId) {
//////////        User user = findUserByIdOrEmail(userId);
//////////
//////////        Map<ShelfType, List<UserBook>> grouped = user.getUserBooks().stream()
//////////                .filter(Objects::nonNull)
//////////                .collect(Collectors.groupingBy(ub -> ub.getShelfType() == null ? ShelfType.WANT_TO_READ : ub.getShelfType()));
//////////
//////////        ShelfResponse response = new ShelfResponse();
//////////        response.setCurrentlyReading(grouped.getOrDefault(ShelfType.CURRENTLY_READING, Collections.emptyList()));
//////////        response.setWantToRead(grouped.getOrDefault(ShelfType.WANT_TO_READ, Collections.emptyList()));
//////////        response.setRead(grouped.getOrDefault(ShelfType.READ, Collections.emptyList()));
//////////
//////////        return response;
//////////    }
//////////
//////////    public void addBookToShelf(String userId, AddBookRequest request) {
//////////        User user = findUserByIdOrEmail(userId);
//////////
//////////        if (request == null || request.getBookId() == null || request.getBookId().isBlank()) {
//////////            throw new IllegalArgumentException("bookId is required");
//////////        }
//////////
//////////        String normalizedBookId = normalizeBookId(request.getBookId().trim());
//////////
//////////        // Prevent duplicate books
//////////        Optional<UserBook> existingOpt = user.getUserBooks()
//////////                .stream()
//////////                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
//////////                .findFirst();
//////////
//////////        if (existingOpt.isPresent()) {
//////////            UserBook existing = existingOpt.get();
//////////            if (request.getShelfType() != null) existing.setShelfType(request.getShelfType());
//////////            if (request.getNotes() != null) existing.setNotes(request.getNotes());
//////////            userRepository.save(user);
//////////            log.debug("Updated existing UserBook {} for user {}", normalizedBookId, userId);
//////////            return;
//////////        }
//////////
//////////        // Fetch metadata from external service
//////////        BookDetailResponse metadata = externalBookService.getBookDetails(normalizedBookId);
//////////
//////////        UserBook ub = new UserBook();
//////////        ub.setBookId(metadata.getBookId() != null ? metadata.getBookId() : normalizedBookId);
//////////        ub.setShelfType(request.getShelfType() != null ? request.getShelfType() : ShelfType.WANT_TO_READ);
//////////        ub.setNotes(request.getNotes());
//////////        ub.setTitle(metadata.getTitle());
//////////        ub.setAuthors(metadata.getAuthors());
//////////        ub.setCoverImageUrl(metadata.getCoverImageUrl());
//////////        ub.setSmallCoverImageUrl(metadata.getSmallCoverImageUrl());
//////////        ub.setCategories(metadata.getCategories());
//////////
//////////        user.getUserBooks().add(ub);
//////////        userRepository.save(user);
//////////
//////////        log.info("Added book {} to user {} (title={})", ub.getBookId(), userId, ub.getTitle());
//////////    }
//////////
//////////    public void updateProgress(String userId, UpdateProgressRequest request) {
//////////        User user = findUserByIdOrEmail(userId);
//////////
//////////        String normalizedBookId = normalizeBookId(request.getBookId());
//////////
//////////        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
//////////                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
//////////                .findFirst();
//////////
//////////        if (userBookOpt.isPresent()) {
//////////            UserBook userBook = userBookOpt.get();
//////////            userBook.setProgress(Math.max(0, Math.min(100, request.getProgress())));
//////////            if (request.getProgress() >= 100) userBook.setShelfType(ShelfType.READ);
//////////            userRepository.save(user);
//////////            log.debug("Updated progress for user {} book {} -> {}", userId, normalizedBookId, request.getProgress());
//////////        } else {
//////////            throw new IllegalArgumentException("Book not found in user library: " + request.getBookId());
//////////        }
//////////    }
//////////
//////////    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) {
//////////        User user = findUserByIdOrEmail(userId);
//////////
//////////        String normalizedBookId = normalizeBookId(bookId);
//////////
//////////        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
//////////                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
//////////                .findFirst();
//////////
//////////        if (userBookOpt.isPresent()) {
//////////            userBookOpt.get().setShelfType(newShelf);
//////////            userRepository.save(user);
//////////            log.debug("Moved book {} to shelf {} for user {}", normalizedBookId, newShelf, userId);
//////////        } else {
//////////            throw new IllegalArgumentException("Book not found in user library: " + bookId);
//////////        }
//////////    }
//////////
//////////    public void addReview(String userId, ReviewRequest request) {
//////////        User user = findUserByIdOrEmail(userId);
//////////
//////////        String normalizedBookId = normalizeBookId(request.getBookId());
//////////
//////////        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
//////////                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
//////////                .findFirst();
//////////
//////////        if (userBookOpt.isPresent()) {
//////////            UserBook ub = userBookOpt.get();
//////////            ub.setReview(request.getReviewText());
//////////            ub.setRating(Math.max(0, request.getRating()));
//////////            userRepository.save(user);
//////////            log.debug("Added/updated review for user {} book {}", userId, normalizedBookId);
//////////        } else {
//////////            // Add book first if missing
//////////            AddBookRequest add = new AddBookRequest();
//////////            add.setBookId(normalizedBookId);
//////////            add.setShelfType(ShelfType.WANT_TO_READ);
//////////            addBookToShelf(userId, add);
//////////
//////////            User updated = findUserByIdOrEmail(userId);
//////////            Optional<UserBook> ub2 = updated.getUserBooks().stream()
//////////                    .filter(x -> normalizeBookId(x.getBookId()).equals(normalizedBookId))
//////////                    .findFirst();
//////////
//////////            if (ub2.isPresent()) {
//////////                ub2.get().setReview(request.getReviewText());
//////////                ub2.get().setRating(Math.max(0, request.getRating()));
//////////                userRepository.save(updated);
//////////            } else {
//////////                throw new IllegalStateException("Failed to add book + review for " + normalizedBookId);
//////////            }
//////////        }
//////////    }
//////////
//////////    public void updateReview(String userId, String reviewId, ReviewRequest request) {
//////////        addReview(userId, request);
//////////    }
//////////
//////////    public void deleteReview(String userId, String reviewId) {
//////////        User user = findUserByIdOrEmail(userId);
//////////
//////////        String normalizedBookId = normalizeBookId(reviewId);
//////////
//////////        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
//////////                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
//////////                .findFirst();
//////////
//////////        if (userBookOpt.isPresent()) {
//////////            UserBook ub = userBookOpt.get();
//////////            ub.setReview(null);
//////////            ub.setRating(0);
//////////            userRepository.save(user);
//////////            log.debug("Deleted review for user {} book {}", userId, normalizedBookId);
//////////        } else {
//////////            throw new IllegalArgumentException("Book not found in user library: " + reviewId);
//////////        }
//////////    }
//////////}
////////
////////
////////package com.amigoscode.bookbuddybackend.service;
////////
////////import com.amigoscode.bookbuddybackend.dto.request.*;
////////import com.amigoscode.bookbuddybackend.dto.response.*;
////////import com.amigoscode.bookbuddybackend.model.Book;
////////import com.amigoscode.bookbuddybackend.model.User;
////////import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
////////import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
////////import com.amigoscode.bookbuddybackend.repository.BookRepository;
////////import com.amigoscode.bookbuddybackend.repository.UserRepository;
////////import lombok.RequiredArgsConstructor;
////////import lombok.extern.slf4j.Slf4j;
////////import org.springframework.stereotype.Service;
////////
////////import java.util.*;
////////import java.util.function.Function;
////////import java.util.stream.Collectors;
////////
////////@Slf4j
////////@Service
////////@RequiredArgsConstructor
////////public class UserLibraryService {
////////
////////    private final UserRepository userRepository;
////////    private final BookRepository bookRepository;
////////    private final ExternalBookService externalBookService;
////////
////////    private User findUserByIdOrEmail(String idOrEmail) {
////////        return userRepository.findById(idOrEmail)
////////                .or(() -> userRepository.findByEmail(idOrEmail))
////////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + idOrEmail));
////////    }
////////
////////    private String normalizeBookId(String bookId) {
////////        return bookId == null ? null : bookId.trim();
////////    }
////////
////////    // MAIN METHOD – RICH SHELVES WITH FULL BOOK DATA
////////    public ShelfResponse getShelves(String userId) {
////////        User user = findUserByIdOrEmail(userId);
////////
////////        // 1. Extract all unique book IDs from user's library
////////        Set<String> bookIds = user.getUserBooks().stream()
////////                .filter(Objects::nonNull)
////////                .map(UserBook::getBookId)
////////                .map(this::normalizeBookId)
////////                .filter(Objects::nonNull)
////////                .collect(Collectors.toSet());
////////
////////        // 2. Batch load all persistent uploaded books (your own collection)
////////        Map<String, Book> persistentBookMap = bookIds.isEmpty() ? Map.of() :
////////                bookRepository.findByBookIdIn(new ArrayList<>(bookIds)).stream()
////////                        .collect(Collectors.toMap(
////////                                b -> normalizeBookId(Optional.ofNullable(b.getBookId()).orElse(b.getId())),
////////                                Function.identity(),
////////                                (existing, replacement) -> existing
////////                        ));
////////
////////        // 3. Group UserBooks by shelf
////////        Map<ShelfType, List<UserBook>> grouped = user.getUserBooks().stream()
////////                .filter(Objects::nonNull)
////////                .collect(Collectors.groupingBy(
////////                        ub -> ub.getShelfType() == null ? ShelfType.WANT_TO_READ : ub.getShelfType()
////////                ));
////////
////////        ShelfResponse response = new ShelfResponse();
////////
////////        response.setCurrentlyReading(buildShelfItems(
////////                grouped.getOrDefault(ShelfType.CURRENTLY_READING, List.of()),
////////                persistentBookMap
////////        ));
////////        response.setWantToRead(buildShelfItems(
////////                grouped.getOrDefault(ShelfType.WANT_TO_READ, List.of()),
////////                persistentBookMap
////////        ));
////////        response.setRead(buildShelfItems(
////////                grouped.getOrDefault(ShelfType.READ, List.of()),
////////                persistentBookMap
////////        ));
////////
////////        return response;
////////    }
////////
////////    private List<ShelfItemResponse> buildShelfItems(List<UserBook> userBooks,
////////                                                    Map<String, Book> persistentBookMap) {
////////        return userBooks.stream()
////////                .map(ub -> {
////////                    String bookId = normalizeBookId(ub.getBookId());
////////
////////                    // Priority 1: Real uploaded book from your DB
////////                    Book book = persistentBookMap.get(bookId);
////////                    if (book != null) {
////////                        return new ShelfItemResponse(book, ub);
////////                    }
////////
////////                    // Priority 2: Fresh data from external API
////////                    try {
////////                        BookDetailResponse external = externalBookService.getBookDetails(bookId);
////////                        Book fallback = externalToBook(external);
////////                        return new ShelfItemResponse(fallback, ub);
////////                    } catch (Exception e) {
////////                        log.debug("Failed to fetch external details for bookId: {} – {}", bookId, e.getMessage());
////////                    }
////////
////////                    // Priority 3: Use legacy data stored in UserBook (never breaks)
////////                    return new ShelfItemResponse(legacyUserBookToBook(ub), ub);
////////                })
////////                .toList();
////////    }
////////
////////    // Convert external API response → Book (fallback)
////////    private Book externalToBook(BookDetailResponse dto) {
////////        Book b = new Book();
////////        b.setBookId(dto.getBookId());
////////        b.setTitle(dto.getTitle());
////////        b.setAuthor(dto.getAuthors() != null && !dto.getAuthors().isEmpty()
////////                ? String.join(", ", dto.getAuthors())
////////                : "Unknown Author");
////////        b.setCoverUrl(dto.getCoverImageUrl());
////////        b.setDescription(dto.getDescription() != null ? dto.getDescription() : dto.getFallbackDescription());
////////        b.setGenre(dto.getCategories() != null && !dto.getCategories().isEmpty()
////////                ? String.join(", ", dto.getCategories())
////////                : null);
////////        b.setPublishedDate(dto.getPublishedDate() != null
////////                ? java.time.LocalDate.parse(dto.getPublishedDate().substring(0, 10))
////////                : null);
////////        b.setPageCount(dto.getPageCount() != null ? dto.getPageCount().toString() : null);
////////        return b;
////////    }
////////
////////    // Convert legacy UserBook → Book (final fallback)
////////    private Book legacyUserBookToBook(UserBook ub) {
////////        Book b = new Book();
////////        b.setBookId(normalizeBookId(ub.getBookId()));
////////        b.setTitle(ub.getTitle() != null ? ub.getTitle() : "Unknown Title");
////////        b.setAuthor(ub.getAuthors() != null && !ub.getAuthors().isEmpty()
////////                ? String.join(", ", ub.getAuthors())
////////                : "Unknown Author");
////////        b.setCoverUrl(ub.getCoverImageUrl());
////////        return b;
////////    }
////////
////////    // YOUR EXISTING METHODS – UNCHANGED & FULLY WORKING
////////
////////    public void addBookToShelf(String userId, AddBookRequest request) {
////////        User user = findUserByIdOrEmail(userId);
////////
////////        if (request == null || request.getBookId() == null || request.getBookId().isBlank()) {
////////            throw new IllegalArgumentException("bookId is required");
////////        }
////////
////////        String normalizedBookId = normalizeBookId(request.getBookId());
////////
////////        Optional<UserBook> existing = user.getUserBooks().stream()
////////                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
////////                .findFirst();
////////
////////        if (existing.isPresent()) {
////////            UserBook ub = existing.get();
////////            if (request.getShelfType() != null) ub.setShelfType(request.getShelfType());
////////            if (request.getNotes() != null) ub.setNotes(request.getNotes());
////////            userRepository.save(user);
////////            return;
////////        }
////////
////////        BookDetailResponse metadata = externalBookService.getBookDetails(normalizedBookId);
////////
////////        UserBook ub = new UserBook();
////////        ub.setBookId(metadata.getBookId() != null ? metadata.getBookId() : normalizedBookId);
////////        ub.setShelfType(request.getShelfType() != null ? request.getShelfType() : ShelfType.WANT_TO_READ);
////////        ub.setNotes(request.getNotes());
////////        ub.setTitle(metadata.getTitle());
////////        ub.setAuthors(metadata.getAuthors());
////////        ub.setCoverImageUrl(metadata.getCoverImageUrl());
////////        ub.setSmallCoverImageUrl(metadata.getSmallCoverImageUrl());
////////        ub.setCategories(metadata.getCategories());
////////
////////        user.getUserBooks().add(ub);
////////        userRepository.save(user);
////////
////////        log.info("Added book {} to user {} (title={})", ub.getBookId(), userId, ub.getTitle());
////////    }
////////
////////    public void updateProgress(String userId, UpdateProgressRequest request) {
////////        User user = findUserByIdOrEmail(userId);
////////        String normalizedBookId = normalizeBookId(request.getBookId());
////////
////////        user.getUserBooks().stream()
////////                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
////////                .findFirst()
////////                .ifPresentOrElse(
////////                        ub -> {
////////                            ub.setProgress(Math.max(0, Math.min(100, request.getProgress())));
////////                            if (request.getProgress() >= 100) ub.setShelfType(ShelfType.READ);
////////                            userRepository.save(user);
////////                        },
////////                        () -> { throw new IllegalArgumentException("Book not found: " + request.getBookId()); }
////////                );
////////    }
////////
////////    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) {
////////        User user = findUserByIdOrEmail(userId);
////////        String normalizedBookId = normalizeBookId(bookId);
////////
////////        user.getUserBooks().stream()
////////                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
////////                .findFirst()
////////                .ifPresentOrElse(
////////                        ub -> {
////////                            ub.setShelfType(newShelf);
////////                            userRepository.save(user);
////////                        },
////////                        () -> { throw new IllegalArgumentException("Book not found: " + bookId); }
////////                );
////////    }
////////
////////    public void addReview(String userId, ReviewRequest request) {
////////        User user = findUserByIdOrEmail(userId);
////////        String normalizedBookId = normalizeBookId(request.getBookId());
////////
////////        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
////////                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
////////                .findFirst();
////////
////////        if (userBookOpt.isPresent()) {
////////            UserBook ub = userBookOpt.get();
////////            ub.setReview(request.getReviewText());
////////            ub.setRating(Math.max(0, request.getRating()));
////////            userRepository.save(user);
////////        } else {
////////            AddBookRequest add = new AddBookRequest();
////////            add.setBookId(normalizedBookId);
////////            add.setShelfType(ShelfType.WANT_TO_READ);
////////            addBookToShelf(userId, add);
////////
////////            userRepository.findById(user.getId()).ifPresent(updated -> {
////////                updated.getUserBooks().stream()
////////                        .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
////////                        .findFirst()
////////                        .ifPresent(ub -> {
////////                            ub.setReview(request.getReviewText());
////////                            ub.setRating(Math.max(0, request.getRating()));
////////                            userRepository.save(updated);
////////                        });
////////            });
////////        }
////////    }
////////
////////    public void updateReview(String userId, String reviewId, ReviewRequest request) {
////////        request.setBookId(reviewId);
////////        addReview(userId, request);
////////    }
////////
////////    public void deleteReview(String userId, String reviewId) {
////////        User user = findUserByIdOrEmail(userId);
////////        String normalizedBookId = normalizeBookId(reviewId);
////////
////////        user.getUserBooks().stream()
////////                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
////////                .findFirst()
////////                .ifPresent(ub -> {
////////                    ub.setReview(null);
////////                    ub.setRating(0);
////////                    userRepository.save(user);
////////                });
////////    }
////////}
//////
//////package com.amigoscode.bookbuddybackend.service;
//////
//////import com.amigoscode.bookbuddybackend.dto.request.*;
//////import com.amigoscode.bookbuddybackend.dto.response.*;
//////import com.amigoscode.bookbuddybackend.model.Book;
//////import com.amigoscode.bookbuddybackend.model.User;
//////import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
//////import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
//////import com.amigoscode.bookbuddybackend.repository.BookRepository;
//////import com.amigoscode.bookbuddybackend.repository.UserRepository;
//////import lombok.RequiredArgsConstructor;
//////import lombok.extern.slf4j.Slf4j;
//////import org.springframework.stereotype.Service;
//////
//////import java.util.*;
//////import java.util.function.Function;
//////import java.util.stream.Collectors;
//////
//////@Slf4j
//////@Service
//////@RequiredArgsConstructor
//////public class UserLibraryService {
//////
//////    private final UserRepository userRepository;
//////    private final BookRepository bookRepository;
//////    private final ExternalBookService externalBookService;
//////
//////    private User findUserByIdOrEmail(String idOrEmail) {
//////        return userRepository.findById(idOrEmail)
//////                .or(() -> userRepository.findByEmail(idOrEmail))
//////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + idOrEmail));
//////    }
//////
//////    private String normalizeBookId(String bookId) {
//////        return bookId == null ? null : bookId.trim();
//////    }
//////
//////    // RICH SHELVES – MAIN UPGRADE
//////    public ShelfResponse getShelves(String userId) {
//////        User user = findUserByIdOrEmail(userId);
//////
//////        Set<String> bookIds = user.getUserBooks().stream()
//////                .filter(Objects::nonNull)
//////                .map(UserBook::getBookId)
//////                .map(this::normalizeBookId)
//////                .filter(Objects::nonNull)
//////                .collect(Collectors.toSet());
//////
//////        Map<String, Book> persistentBooks = bookIds.isEmpty() ? Map.of() :
//////                bookRepository.findByBookIdIn(new ArrayList<>(bookIds)).stream()
//////                        .collect(Collectors.toMap(
//////                                b -> normalizeBookId(b.getBookId() != null ? b.getBookId() : b.getIsbn()),
//////                                Function.identity(),
//////                                (a, b) -> a
//////                        ));
//////
//////        Map<ShelfType, List<UserBook>> grouped = user.getUserBooks().stream()
//////                .filter(Objects::nonNull)
//////                .collect(Collectors.groupingBy(
//////                        ub -> ub.getShelfType() == null ? ShelfType.WANT_TO_READ : ub.getShelfType()
//////                ));
//////
//////        ShelfResponse response = new ShelfResponse();
//////        response.setCurrentlyReading(buildShelfItems(grouped.getOrDefault(ShelfType.CURRENTLY_READING, List.of()), persistentBooks));
//////        response.setWantToRead(buildShelfItems(grouped.getOrDefault(ShelfType.WANT_TO_READ, List.of()), persistentBooks));
//////        response.setRead(buildShelfItems(grouped.getOrDefault(ShelfType.READ, List.of()), persistentBooks));
//////
//////        return response;
//////    }
//////
//////    private List<ShelfItemResponse> buildShelfItems(List<UserBook> userBooks, Map<String, Book> persistentBooks) {
//////        return userBooks.stream()
//////                .map(ub -> {
//////                    String bookId = normalizeBookId(ub.getBookId());
//////
//////                    // 1. Uploaded book
//////                    Book book = persistentBooks.get(bookId);
//////                    if (book != null) {
//////                        return new ShelfItemResponse(book, ub);
//////                    }
//////
//////                    // 2. External API
//////                    try {
//////                        BookDetailResponse external = externalBookService.getBookDetails(bookId);
//////                        return new ShelfItemResponse(externalToBook(external), ub);
//////                    } catch (Exception e) {
//////                        log.debug("External fetch failed for {}: {}", bookId, e.getMessage());
//////                    }
//////
//////                    // 3. Legacy
//////                    return new ShelfItemResponse(legacyUserBookToBook(ub), ub);
//////                })
//////                .toList();
//////    }
//////
//////    private Book externalToBook(BookDetailResponse dto) {
//////        Book b = new Book();
//////        b.setBookId(dto.getBookId());
//////        b.setTitle(dto.getTitle());
//////        b.setAuthor(dto.getAuthors() != null && !dto.getAuthors().isEmpty()
//////                ? String.join(", ", dto.getAuthors()) : "Unknown Author");
//////        b.setCoverUrl(dto.getCoverImageUrl());
//////        b.setDescription(dto.getDescription() != null ? dto.getDescription() : dto.getFallbackDescription());
//////        b.setGenre(dto.getCategories() != null ? String.join(", ", dto.getCategories()) : null);
//////        if (dto.getPublishedDate() != null && dto.getPublishedDate().length() >= 10) {
//////            try {
//////                b.setPublicationDate(java.time.LocalDate.parse(dto.getPublishedDate().substring(0, 10)));
//////            } catch (Exception ignored) {}
//////        }
//////        b.setFormat(dto.getPageCount() != null ? dto.getPageCount() + " pages" : null);
//////        return b;
//////    }
//////
//////    private Book legacyUserBookToBook(UserBook ub) {
//////        Book b = new Book();
//////        b.setBookId(normalizeBookId(ub.getBookId()));
//////        b.setTitle(ub.getTitle() != null ? ub.getTitle() : "Unknown Book");
//////        b.setAuthor(ub.getAuthors() != null && !ub.getAuthors().isEmpty()
//////                ? String.join(", ", ub.getAuthors()) : "Unknown Author");
//////        b.setCoverUrl(ub.getCoverImageUrl());
//////        return b;
//////    }
//////
//////    // ALL MISSING METHODS – RESTORED
//////
//////    public void addBookToShelf(String userId, AddBookRequest request) {
//////        User user = findUserByIdOrEmail(userId);
//////        String normalizedBookId = normalizeBookId(request.getBookId());
//////
//////        if (normalizedBookId == null || normalizedBookId.isBlank()) {
//////            throw new IllegalArgumentException("bookId is required");
//////        }
//////
//////        boolean exists = user.getUserBooks().stream()
//////                .anyMatch(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())));
//////
//////        if (exists) {
//////            user.getUserBooks().stream()
//////                    .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
//////                    .findFirst()
//////                    .ifPresent(ub -> {
//////                        if (request.getShelfType() != null) ub.setShelfType(request.getShelfType());
//////                        if (request.getNotes() != null) ub.setNotes(request.getNotes());
//////                    });
//////            userRepository.save(user);
//////            return;
//////        }
//////
//////        BookDetailResponse metadata = externalBookService.getBookDetails(normalizedBookId);
//////
//////        UserBook ub = new UserBook();
//////        ub.setBookId(metadata.getBookId() != null ? metadata.getBookId() : normalizedBookId);
//////        ub.setShelfType(request.getShelfType() != null ? request.getShelfType() : ShelfType.WANT_TO_READ);
//////        ub.setNotes(request.getNotes());
//////        ub.setTitle(metadata.getTitle());
//////        ub.setAuthors(metadata.getAuthors());
//////        ub.setCoverImageUrl(metadata.getCoverImageUrl());
//////        ub.setSmallCoverImageUrl(metadata.getSmallCoverImageUrl());
//////        ub.setCategories(metadata.getCategories());
//////
//////        user.getUserBooks().add(ub);
//////        userRepository.save(user);
//////        log.info("Added book {} to user {}", ub.getBookId(), userId);
//////    }
//////
//////    public void updateProgress(String userId, UpdateProgressRequest request) {
//////        User user = findUserByIdOrEmail(userId);
//////        String normalizedBookId = normalizeBookId(request.getBookId());
//////
//////        user.getUserBooks().stream()
//////                .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
//////                .findFirst()
//////                .ifPresentOrElse(
//////                        ub -> {
//////                            ub.setProgress(Math.max(0, Math.min(100, request.getProgress())));
//////                            if (request.getProgress() >= 100) ub.setShelfType(ShelfType.READ);
//////                            userRepository.save(user);
//////                        },
//////                        () -> { throw new IllegalArgumentException("Book not found in library: " + request.getBookId()); }
//////                );
//////    }
//////
//////    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) {
//////        User user = findUserByIdOrEmail(userId);
//////        String normalizedBookId = normalizeBookId(bookId);
//////
//////        user.getUserBooks().stream()
//////                .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
//////                .findFirst()
//////                .ifPresentOrElse(
//////                        ub -> {
//////                            ub.setShelfType(newShelf);
//////                            userRepository.save(user);
//////                        },
//////                        () -> { throw new IllegalArgumentException("Book not found: " + bookId); }
//////                );
//////    }
//////
//////    public void addReview(String userId, ReviewRequest request) {
//////        User user = findUserByIdOrEmail(userId);
//////        String normalizedBookId = normalizeBookId(request.getBookId());
//////
//////        Optional<UserBook> opt = user.getUserBooks().stream()
//////                .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
//////                .findFirst();
//////
//////        if (opt.isPresent()) {
//////            UserBook ub = opt.get();
//////            ub.setReview(request.getReviewText());
//////            ub.setRating(Math.max(0, request.getRating()));
//////            userRepository.save(user);
//////        } else {
//////            AddBookRequest add = new AddBookRequest();
//////            add.setBookId(normalizedBookId);
//////            addBookToShelf(userId, add);
//////
//////            userRepository.findById(user.getId()).ifPresent(u -> {
//////                u.getUserBooks().stream()
//////                        .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
//////                        .findFirst()
//////                        .ifPresent(ub -> {
//////                            ub.setReview(request.getReviewText());
//////                            ub.setRating(Math.max(0, request.getRating()));
//////                            userRepository.save(u);
//////                        });
//////            });
//////        }
//////    }
//////
//////    public void updateReview(String userId, String reviewId, ReviewRequest request) {
//////        request.setBookId(reviewId);
//////        addReview(userId, request);
//////    }
//////
//////    public void deleteReview(String userId, String reviewId) {
//////        User user = findUserByIdOrEmail(userId);
//////        String normalizedBookId = normalizeBookId(reviewId);
//////
//////        user.getUserBooks().stream()
//////                .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
//////                .findFirst()
//////                .ifPresent(ub -> {
//////                    ub.setReview(null);
//////                    ub.setRating(0);
//////                    userRepository.save(user);
//////                });
//////    }
//////}
////
////package com.amigoscode.bookbuddybackend.service;
////
////import com.amigoscode.bookbuddybackend.dto.request.*;
////import com.amigoscode.bookbuddybackend.dto.response.*;
////import com.amigoscode.bookbuddybackend.model.Book;
////import com.amigoscode.bookbuddybackend.model.User;
////import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
////import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
////import com.amigoscode.bookbuddybackend.repository.BookRepository;
////import com.amigoscode.bookbuddybackend.repository.UserRepository;
////import lombok.RequiredArgsConstructor;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.stereotype.Service;
////
////import java.util.*;
////import java.util.function.Function;
////import java.util.stream.Collectors;
////
////@Slf4j
////@Service
////@RequiredArgsConstructor
////public class UserLibraryService {
////
////    private final UserRepository userRepository;
////    private final BookRepository bookRepository;
////    private final ExternalBookService externalBookService;
////
////    private User findUserByIdOrEmail(String idOrEmail) {
////        return userRepository.findById(idOrEmail)
////                .or(() -> userRepository.findByEmail(idOrEmail))
////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + idOrEmail));
////    }
////
////    private String normalizeBookId(String bookId) {
////        return bookId == null ? null : bookId.trim();
////    }
////
////    // RICH SHELVES – MAIN UPGRADE
////    public ShelfResponse getShelves(String userId) {
////        User user = findUserByIdOrEmail(userId);
////
////        Set<String> bookIds = user.getUserBooks().stream()
////                .filter(Objects::nonNull)
////                .map(UserBook::getBookId)
////                .map(this::normalizeBookId)
////                .filter(Objects::nonNull)
////                .collect(Collectors.toSet());
////
////        // Changed: use ISBN as fallback key since Book has no bookId field
////        Map<String, Book> persistentBooks = bookIds.isEmpty() ? Map.of() :
////                bookRepository.findAllByIsbnIn(new ArrayList<>(bookIds)).stream() // fallback to ISBN
////                        .collect(Collectors.toMap(
////                                b -> normalizeBookId(b.getIsbn()),
////                                Function.identity(),
////                                (a, b) -> a
////                        ));
////
////        Map<ShelfType, List<UserBook>> grouped = user.getUserBooks().stream()
////                .filter(Objects::nonNull)
////                .collect(Collectors.groupingBy(
////                        ub -> ub.getShelfType() == null ? ShelfType.WANT_TO_READ : ub.getShelfType()
////                ));
////
////        ShelfResponse response = new ShelfResponse();
////        response.setCurrentlyReading(buildShelfItems(grouped.getOrDefault(ShelfType.CURRENTLY_READING, List.of()), persistentBooks));
////        response.setWantToRead(buildShelfItems(grouped.getOrDefault(ShelfType.WANT_TO_READ, List.of()), persistentBooks));
////        response.setRead(buildShelfItems(grouped.getOrDefault(ShelfType.READ, List.of()), persistentBooks));
////
////        return response;
////    }
////
////    private List<ShelfItemResponse> buildShelfItems(List<UserBook> userBooks, Map<String, Book> persistentBooks) {
////        return userBooks.stream()
////                .map(ub -> {
////                    String bookId = normalizeBookId(ub.getBookId());
////
////                    // 1. Try to match uploaded book by ISBN (since Book has no bookId field)
////                    Book book = persistentBooks.get(bookId != null ? bookId : ub.getBookId());
////                    if (book != null) {
////                        return new ShelfItemResponse(book, ub);
////                    }
////
////                    // 2. External Google Books API
////                    if (bookId != null) {
////                        try {
////                            BookDetailResponse external = externalBookService.getBookDetails(bookId);
////                            return new ShelfItemResponse(externalToBook(external), ub);
////                        } catch (Exception e) {
////                            log.debug("External fetch failed for {}: {}", bookId, e.getMessage());
////                        }
////                    }
////
////                    // 3. Legacy fallback
////                    return new ShelfItemResponse(legacyUserBookToBook(ub), ub);
////                })
////                .toList();
////    }
////
////    private Book externalToBook(BookDetailResponse dto) {
////        Book b = new Book();
////        // Removed setBookId() – field doesn't exist
////        b.setTitle(dto.getTitle());
////        b.setAuthor(dto.getAuthors() != null && !dto.getAuthors().isEmpty()
////                ? String.join(", ", dto.getAuthors()) : "Unknown Author");
////        b.setCoverUrl(dto.getCoverImageUrl());
////        b.setDescription(dto.getDescription() != null ? dto.getDescription() : dto.getFallbackDescription());
////        b.setGenre(dto.getCategories() != null ? String.join(", ", dto.getCategories()) : null);
////        if (dto.getPublishedDate() != null && dto.getPublishedDate().length() >= 10) {
////            try {
////                b.setPublicationDate(java.time.LocalDate.parse(dto.getPublishedDate().substring(0, 10)));
////            } catch (Exception ignored) {}
////        }
////        b.setFormat(dto.getPageCount() != null ? dto.getPageCount() + " pages" : null);
////        b.setIsbn(dto.getBookId()); // store Google volumeId in isbn column as fallback
////        return b;
////    }
////
////    private Book legacyUserBookToBook(UserBook ub) {
////        Book b = new Book();
////        // Removed setBookId()
////        b.setTitle(ub.getTitle() != null ? ub.getTitle() : "Unknown Book");
////        b.setAuthor(ub.getAuthors() != null && !ub.getAuthors().isEmpty()
////                ? String.join(", ", ub.getAuthors()) : "Unknown Author");
////        b.setCoverUrl(ub.getCoverImageUrl());
////        return b;
////    }
////
////    // ALL OTHER METHODS – unchanged (they only touch UserBook.bookId, which still exists)
////    public void addBookToShelf(String userId, AddBookRequest request) { /* unchanged – uses UserBook.bookId */ }
////    public void updateProgress(String userId, UpdateProgressRequest request) { /* unchanged */ }
////    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) { /* unchanged */ }
////    public void addReview(String userId, ReviewRequest request) { /* unchanged */ }
////    public void updateReview(String userId, String reviewId, ReviewRequest request) {
////        request.setBookId(reviewId);
////        addReview(userId, request);
////    }
////    public void deleteReview(String userId, String reviewId) { /* unchanged */ }
////
////    // copy-paste the original implementations of the methods above if you removed them
////    // (they are exactly the same as in your previous message – they only use UserBook.bookId)
////}
//
//
//package com.amigoscode.bookbuddybackend.service;
//
//import com.amigoscode.bookbuddybackend.dto.request.*;
//import com.amigoscode.bookbuddybackend.dto.response.*;
//import com.amigoscode.bookbuddybackend.model.Book;
//import com.amigoscode.bookbuddybackend.model.User;
//import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
//import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
//import com.amigoscode.bookbuddybackend.repository.BookRepository;
//import com.amigoscode.bookbuddybackend.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
////@Slf4j
////@Service
////@RequiredArgsConstructor
////public class UserLibraryService {
////
////    private final UserRepository userRepository;
////    private final BookRepository bookRepository;
////    private final ExternalBookService externalBookService;
////
////    private User findUserByIdOrEmail(String idOrEmail) {
////        return userRepository.findById(idOrEmail)
////                .or(() -> userRepository.findByEmail(idOrEmail))
////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + idOrEmail));
////    }
////
////    private String normalizeBookId(String bookId) {
////        return bookId == null ? null : bookId.trim();
////    }
////
////    // RICH SHELVES – MAIN UPGRADE
////    public ShelfResponse getShelves(String userId) {
////        User user = findUserByIdOrEmail(userId);
////
////        Set<String> bookIds = user.getUserBooks().stream()
////                .filter(Objects::nonNull)
////                .map(UserBook::getBookId)
////                .map(this::normalizeBookId)
////                .filter(Objects::nonNull)
////                .collect(Collectors.toSet());
////
////        // We store Google volume ID (or custom upload ID) in the "isbn" field
////        Map<String, Book> persistentBooks = bookIds.isEmpty() ? Map.of() :
////                bookRepository.findByIsbnIn(new ArrayList<>(bookIds)).stream()
////                        .collect(Collectors.toMap(
////                                Book::getIsbn,          // key = isbn field
////                                Function.identity(),
////                                (existing, replacement) -> existing
////                        ));
////
////        Map<ShelfType, List<UserBook>> grouped = user.getUserBooks().stream()
////                .filter(Objects::nonNull)
////                .collect(Collectors.groupingBy(
////                        ub -> ub.getShelfType() == null ? ShelfType.WANT_TO_READ : ub.getShelfType()
////                ));
////
////        ShelfResponse response = new ShelfResponse();
////        response.setCurrentlyReading(buildShelfItems(grouped.getOrDefault(ShelfType.CURRENTLY_READING, List.of()), persistentBooks));
////        response.setWantToRead(buildShelfItems(grouped.getOrDefault(ShelfType.WANT_TO_READ, List.of()), persistentBooks));
////        response.setRead(buildShelfItems(grouped.getOrDefault(ShelfType.READ, List.of()), persistentBooks));
////
////        return response;
////    }
////
////    private List<ShelfItemResponse> buildShelfItems(List<UserBook> userBooks, Map<String, Book> persistentBooks) {
////        return userBooks.stream()
////                .map(ub -> {
////                    String bookId = normalizeBookId(ub.getBookId());
////
////                    // 1. Try to find uploaded book (matched by isbn = bookId from UserBook)
////                    Book uploadedBook = persistentBooks.get(bookId);
////                    if (uploadedBook != null) {
////                        return new ShelfItemResponse(uploadedBook, ub);
////                    }
////
////                    // 2. Try external Google Books API
////                    if (bookId != null) {
////                        try {
////                            BookDetailResponse external = externalBookService.getBookDetails(bookId);
////                            return new ShelfItemResponse(externalToBook(external), ub);
////                        } catch (Exception e) {
////                            log.debug("Failed to fetch from external API for bookId {}: {}", bookId, e.getMessage());
////                        }
////                    }
////
////                    // 3. Fallback: use legacy data from UserBook
////                    return new ShelfItemResponse(legacyUserBookToBook(ub), ub);
////                })
////                .toList();
////    }
////
////    private Book externalToBook(BookDetailResponse dto) {
////        Book b = new Book();
////        b.setIsbn(dto.getBookId()); // Critical: Google volume ID goes into isbn field
////        b.setTitle(dto.getTitle());
////        b.setAuthor(dto.getAuthors() != null && !dto.getAuthors().isEmpty()
////                ? String.join(", ", dto.getAuthors()) : "Unknown Author");
////        b.setCoverUrl(dto.getCoverImageUrl());
////        b.setDescription(dto.getDescription() != null ? dto.getDescription() : dto.getFallbackDescription());
////        b.setGenre(dto.getCategories() != null ? String.join(", ", dto.getCategories()) : null);
////        if (dto.getPublishedDate() != null && dto.getPublishedDate().length() >= 10) {
////            try {
////                b.setPublicationDate(java.time.LocalDate.parse(dto.getPublishedDate().substring(0, 10)));
////            } catch (Exception ignored) {}
////        }
////        b.setFormat(dto.getPageCount() != null ? dto.getPageCount() + " pages" : null);
////        return b;
////    }
////
////    private Book legacyUserBookToBook(UserBook ub) {
////        Book b = new Book();
////        b.setTitle(ub.getTitle() != null ? ub.getTitle() : "Unknown Book");
////        b.setAuthor(ub.getAuthors() != null && !ub.getAuthors().isEmpty()
////                ? String.join(", ", ub.getAuthors()) : "Unknown Author");
////        b.setCoverUrl(ub.getCoverImageUrl());
////        return b;
////    }
////
////    // ———————— ALL OTHER METHODS (unchanged – they only use UserBook.bookId) ————————
////
////    public void addBookToShelf(String userId, AddBookRequest request) {
////        User user = findUserByIdOrEmail(userId);
////        String normalizedBookId = normalizeBookId(request.getBookId());
////
////        if (normalizedBookId == null || normalizedBookId.isBlank()) {
////            throw new IllegalArgumentException("bookId is required");
////        }
////
////        boolean exists = user.getUserBooks().stream()
////                .anyMatch(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())));
////
////        if (exists) {
////            user.getUserBooks().stream()
////                    .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
////                    .findFirst()
////                    .ifPresent(ub -> {
////                        if (request.getShelfType() != null) ub.setShelfType(request.getShelfType());
////                        if (request.getNotes() != null) ub.setNotes(request.getNotes());
////                    });
////            userRepository.save(user);
////            return;
////        }
////
////        BookDetailResponse metadata = externalBookService.getBookDetails(normalizedBookId);
////
////        UserBook ub = new UserBook();
////        ub.setBookId(metadata.getBookId() != null ? metadata.getBookId() : normalizedBookId);
////        ub.setShelfType(request.getShelfType() != null ? request.getShelfType() : ShelfType.WANT_TO_READ);
////        ub.setNotes(request.getNotes());
////        ub.setTitle(metadata.getTitle());
////        ub.setAuthors(metadata.getAuthors());
////        ub.setCoverImageUrl(metadata.getCoverImageUrl());
////        ub.setSmallCoverImageUrl(metadata.getSmallCoverImageUrl());
////        ub.setCategories(metadata.getCategories());
////
////        user.getUserBooks().add(ub);
////        userRepository.save(user);
////        log.info("Added book {} to user {}", ub.getBookId(), userId);
////    }
////
////    public void updateProgress(String userId, UpdateProgressRequest request) {
////        User user = findUserByIdOrEmail(userId);
////        String normalizedBookId = normalizeBookId(request.getBookId());
////
////        user.getUserBooks().stream()
////                .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
////                .findFirst()
////                .ifPresentOrElse(
////                        ub -> {
////                            ub.setProgress(Math.max(0, Math.min(100, request.getProgress())));
////                            if (request.getProgress() >= 100) ub.setShelfType(ShelfType.READ);
////                            userRepository.save(user);
////                        },
////                        () -> { throw new IllegalArgumentException("Book not found in library: " + request.getBookId()); }
////                );
////    }
////
////    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) {
////        User user = findUserByIdOrEmail(userId);
////        String normalizedBookId = normalizeBookId(bookId);
////
////        user.getUserBooks().stream()
////                .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
////                .findFirst()
////                .ifPresentOrElse(
////                        ub -> {
////                            ub.setShelfType(newShelf);
////                            userRepository.save(user);
////                        },
////                        () -> { throw new IllegalArgumentException("Book not found: " + bookId); }
////                );
////    }
////
////    public void addReview(String userId, ReviewRequest request) {
////        User user = findUserByIdOrEmail(userId);
////        String normalizedBookId = normalizeBookId(request.getBookId());
////
////        Optional<UserBook> opt = user.getUserBooks().stream()
////                .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
////                .findFirst();
////
////        if (opt.isPresent()) {
////            UserBook ub = opt.get();
////            ub.setReview(request.getReviewText());
////            ub.setRating(Math.max(0, request.getRating()));
////            userRepository.save(user);
////        } else {
////            AddBookRequest add = new AddBookRequest();
////            add.setBookId(normalizedBookId);
////            addBookToShelf(userId, add);
////
////            userRepository.findById(user.getId()).ifPresent(u -> {
////                u.getUserBooks().stream()
////                        .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
////                        .findFirst()
////                        .ifPresent(ub -> {
////                            ub.setReview(request.getReviewText());
////                            ub.setRating(Math.max(0, request.getRating()));
////                            userRepository.save(u);
////                        });
////            });
////        }
////    }
////
////    public void updateReview(String userId, String reviewId, ReviewRequest request) {
////        request.setBookId(reviewId);
////        addReview(userId, request);
////    }
////
////    public void deleteReview(String userId, String reviewId) {
////        User user = findUserByIdOrEmail(userId);
////        String normalizedBookId = normalizeBookId(reviewId);
////
////        user.getUserBooks().stream()
////                .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
////                .findFirst()
////                .ifPresent(ub -> {
////                    ub.setReview(null);
////                    ub.setRating(0);
////                    userRepository.save(user);
////                });
////    }
////}
//// src/main/java/com/amigoscode/bookbuddybackend/service/UserLibraryService.java
//
//// ... imports ...
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class UserLibraryService {
//
//    private final UserRepository userRepository;
//    private final BookRepository bookRepository;
//    private final ExternalBookService externalBookService;
//
//    // ... findUserByIdOrEmail(), normalizeBookId() ...
//
//    public ShelfResponse getShelves(String userId) {
//        User user = findUserByIdOrEmail(userId);
//
//        Set<String> bookIds = user.getUserBooks().stream()
//                .filter(Objects::nonNull)
//                .map(UserBook::getBookId)
//                .filter(Objects::nonNull)
//                .map(String::trim)
//                .collect(Collectors.toSet());
//
//        log.info("User {} has {} books in library: {}", userId, bookIds.size(), bookIds);
//
//        // Fetch uploaded books (case-insensitive match)
//        List<Book> uploadedBooks = bookRepository.findByIsbnInIgnoreCase(new ArrayList<>(bookIds));
//
//        log.info("Found {} uploaded books in DB", uploadedBooks.size());
//
//        Map<String, Book> persistentBooks = uploadedBooks.stream()
//                .filter(b -> b.getIsbn() != null)
//                .collect(Collectors.toMap(
//                        b -> b.getIsbn().trim(),
//                        Function.identity(),
//                        (a, b) -> a
//                ));
//
//        Map<ShelfType, List<UserBook>> grouped = user.getUserBooks().stream()
//                .filter(Objects::nonNull)
//                .collect(Collectors.groupingBy(
//                        ub -> ub.getShelfType() == null ? ShelfType.WANT_TO_READ : ub.getShelfType()
//                ));
//
//        ShelfResponse response = new ShelfResponse();
//        response.setCurrentlyReading(buildShelfItems(grouped.getOrDefault(ShelfType.CURRENTLY_READING, List.of()), persistentBooks));
//        response.setWantToRead(buildShelfItems(grouped.getOrDefault(ShelfType.WANT_TO_READ, List.of()), persistentBooks));
//        response.setRead(buildShelfItems(grouped.getOrDefault(ShelfType.READ, List.of()), persistentBooks));
//
//        return response;
//    }
//
//    private List<ShelfItemResponse> buildShelfItems(List<UserBook> userBooks, Map<String, Book> persistentBooks) {
//        return userBooks.stream()
//                .map(ub -> {
//                    String bookId = ub.getBookId() != null ? ub.getBookId().trim() : null;
//
//                    // 1. Uploaded book
//                    Book uploaded = persistentBooks.get(bookId);
//                    if (uploaded != null) {
//                        return new ShelfItemResponse(uploaded, ub);
//                    }
//
//                    // 2. External Google Book
//                    if (bookId != null) {
//                        try {
//                            BookDetailResponse external = externalBookService.getBookDetails(bookId);
//                            return new ShelfItemResponse(externalToBook(external), ub);
//                        } catch (Exception e) {
//                            log.debug("External book failed: {}", e.getMessage());
//                        }
//                    }
//
//                    // 3. Legacy
//                    return new ShelfItemResponse(legacyUserBookToBook(ub), ub);
//                })
//                .toList();
//    }
//
//    // ... rest of your methods (addBookToShelf, etc.) unchanged ...
//}


package com.amigoscode.bookbuddybackend.service;

import com.amigoscode.bookbuddybackend.dto.request.*;
import com.amigoscode.bookbuddybackend.dto.response.*;
import com.amigoscode.bookbuddybackend.model.Book;
import com.amigoscode.bookbuddybackend.model.User;
import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
import com.amigoscode.bookbuddybackend.repository.BookRepository;
import com.amigoscode.bookbuddybackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserLibraryService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ExternalBookService externalBookService;

    private User findUserByIdOrEmail(String idOrEmail) {
        return userRepository.findById(idOrEmail)
                .or(() -> userRepository.findByEmail(idOrEmail))
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + idOrEmail));
    }

    private String normalizeBookId(String bookId) {
        return bookId == null ? null : bookId.trim();
    }

    // ===================================================================
    // NEW: Clean method used by controller — always adds to WANT_TO_READ
    // ===================================================================
    public void addBookToWantToRead(String userId, String bookId) {
        User user = findUserByIdOrEmail(userId);
        String normalizedBookId = normalizeBookId(bookId);

        if (normalizedBookId == null || normalizedBookId.isBlank()) {
            throw new IllegalArgumentException("bookId is required");
        }

        // Check if already in library
        boolean alreadyExists = user.getUserBooks().stream()
                .anyMatch(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())));

        if (alreadyExists) {
            log.debug("Book {} already in user's library (userId: {})", normalizedBookId, userId);
            return; // idempotent — no error, just do nothing
        }

        // Fetch metadata from Google Books
        BookDetailResponse metadata;
        try {
            metadata = externalBookService.getBookDetails(normalizedBookId);
        } catch (Exception e) {
            log.warn("Failed to fetch book details for {}: {}", normalizedBookId, e.getMessage());
            // Still allow adding even if external API fails — use minimal data
            metadata = null;
        }

        UserBook userBook = new UserBook();
        userBook.setBookId(normalizedBookId);
        userBook.setShelfType(ShelfType.WANT_TO_READ); // Always start here

        if (metadata != null) {
            userBook.setTitle(metadata.getTitle());
            userBook.setCoverImageUrl(metadata.getCoverImageUrl());
            userBook.setSmallCoverImageUrl(metadata.getSmallCoverImageUrl());
            userBook.setCategories(metadata.getCategories());
        } else {
            // Fallback: minimal info
            userBook.setTitle("Unknown Book");
        }

        user.getUserBooks().add(userBook);
        userRepository.save(user);

        log.info("Book {} added to Want to Read for user {}", normalizedBookId, userId);
    }

    // Optional: Keep old method for backward compatibility (can be removed later)
    @Deprecated
    public void addBookToShelf(String userId, AddBookRequest request) {
        addBookToWantToRead(userId, request.bookId());
    }

    // ===================================================================
    // Rest of your methods (unchanged logic, just minor cleanups)
    // ===================================================================

    public ShelfResponse getShelves(String userId) {
        User user = findUserByIdOrEmail(userId);

        Set<String> bookIds = user.getUserBooks().stream()
                .filter(Objects::nonNull)
                .map(UserBook::getBookId)
                .map(this::normalizeBookId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        log.info("User {} has {} book(s) in library", userId, bookIds.size());

        List<Book> uploadedBooks = bookIds.isEmpty()
                ? List.of()
                : bookRepository.findByIsbnIn(new ArrayList<>(bookIds));

        Map<String, Book> persistentBooks = uploadedBooks.stream()
                .filter(b -> b.getIsbn() != null)
                .collect(Collectors.toMap(
                        b -> normalizeBookId(b.getIsbn()),
                        Function.identity(),
                        (a, b) -> a
                ));

        Map<ShelfType, List<UserBook>> grouped = user.getUserBooks().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        ub -> ub.getShelfType() == null ? ShelfType.WANT_TO_READ : ub.getShelfType()
                ));

        ShelfResponse response = new ShelfResponse();
        response.setCurrentlyReading(buildShelfItems(grouped.getOrDefault(ShelfType.CURRENTLY_READING, List.of()), persistentBooks));
        response.setWantToRead(buildShelfItems(grouped.getOrDefault(ShelfType.WANT_TO_READ, List.of()), persistentBooks));
        response.setRead(buildShelfItems(grouped.getOrDefault(ShelfType.READ, List.of()), persistentBooks));

        return response;
    }

    private List<ShelfItemResponse> buildShelfItems(List<UserBook> userBooks, Map<String, Book> persistentBooks) {
        return userBooks.stream()
                .map(ub -> {
                    String bookId = normalizeBookId(ub.getBookId());

                    // 1. Try uploaded book from DB
                    Book uploadedBook = persistentBooks.get(bookId);
                    if (uploadedBook != null) {
                        return new ShelfItemResponse(uploadedBook, ub);
                    }

                    // 2. Try external API
                    if (bookId != null) {
                        try {
                            BookDetailResponse external = externalBookService.getBookDetails(bookId);
                            return new ShelfItemResponse(externalToBook(external), ub);
                        } catch (Exception e) {
                            log.debug("External API failed for bookId {}: {}", bookId, e.getMessage());
                        }
                    }

                    // 3. Fallback to legacy data in UserBook
                    return new ShelfItemResponse(legacyUserBookToBook(ub), ub);
                })
                .toList();
    }

    private Book externalToBook(BookDetailResponse dto) {
        Book b = new Book();
        b.setIsbn(dto.getBookId());
        b.setTitle(dto.getTitle());
        b.setAuthor(dto.getAuthors() != null && !dto.getAuthors().isEmpty()
                ? String.join(", ", dto.getAuthors()) : "Unknown Author");
        b.setCoverUrl(dto.getCoverImageUrl());
        b.setDescription(dto.getDescription() != null ? dto.getDescription() : dto.getFallbackDescription());
        b.setGenre(dto.getCategories() != null ? String.join(", ", dto.getCategories()) : null);
        if (dto.getPublishedDate() != null && dto.getPublishedDate().length() >= 10) {
            try {
                b.setPublicationDate(java.time.LocalDate.parse(dto.getPublishedDate().substring(0, 10)));
            } catch (Exception ignored) {}
        }
        b.setFormat(dto.getPageCount() != null ? dto.getPageCount() + " pages" : null);
        return b;
    }

    private Book legacyUserBookToBook(UserBook ub) {
        Book b = new Book();
        b.setTitle(ub.getTitle() != null ? ub.getTitle() : "Unknown Book");
        b.setCoverUrl(ub.getCoverImageUrl());
        return b;
    }

    public void updateProgress(String userId, UpdateProgressRequest request) {
        User user = findUserByIdOrEmail(userId);
        String normalizedBookId = normalizeBookId(request.getBookId());

        user.getUserBooks().stream()
                .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
                .findFirst()
                .ifPresentOrElse(
                        ub -> {
                            ub.setProgress(Math.max(0, Math.min(100, request.getProgress())));
                            if (request.getProgress() >= 100) {
                                ub.setShelfType(ShelfType.READ);
                            }
                            userRepository.save(user);
                        },
                        () -> { throw new IllegalArgumentException("Book not found in your library: " + request.getBookId()); }
                );
    }

    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) {
        User user = findUserByIdOrEmail(userId);
        String normalizedBookId = normalizeBookId(bookId);

        user.getUserBooks().stream()
                .filter(ub -> normalizedBookId.equals(normalizeBookId(ub.getBookId())))
                .findFirst()
                .ifPresentOrElse(
                        ub -> {
                            ub.setShelfType(newShelf);
                            userRepository.save(user);
                        },
                        () -> { throw new IllegalArgumentException("Book not found: " + bookId); }
                );
    }


    // Review methods unchanged (safe to keep as-is)
//    public void addReview(String userId, ReviewRequest request) { /* ... unchanged ... */ }
//    public void updateReview(String userId, String reviewId, ReviewRequest request) { /* ... unchanged ... */ }
//    public void deleteReview(String userId, String reviewId) { /* ... unchanged ... */ }
    // Inside UserLibraryService.java — REPLACE the placeholder methods

    public void addReview(String userId, ReviewRequest request) {
        User user = findUserByIdOrEmail(userId);
        String bookId = normalizeBookId(request.getBookId());

        if (bookId == null || bookId.isBlank()) {
            throw new IllegalArgumentException("bookId is required");
        }

        // Find or create the UserBook entry
        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
                .filter(ub -> bookId.equals(normalizeBookId(ub.getBookId())))
                .findFirst();

        UserBook userBook;
        if (userBookOpt.isPresent()) {
            userBook = userBookOpt.get();
        } else {
            // Auto-add the book if it doesn't exist
            addBookToWantToRead(userId, bookId);
            user = findUserByIdOrEmail(userId); // reload
            userBook = user.getUserBooks().stream()
                    .filter(ub -> bookId.equals(normalizeBookId(ub.getBookId())))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Failed to add book"));
        }

        // Save review + rating
        userBook.setReview(request.getReviewText());
        userBook.setRating(request.getRating());


//        // Optional: auto-move to READ when rating/review is added
//        if (request.getNewShelf() != null) {
//            userBook.setShelfType(request.getNewShelf());
//        } else if (request.getRating() != null && request.getRating() > 0) {
//            userBook.setShelfType(ShelfType.READ); // common behavior
//        }

        userRepository.save(user);
        log.info("Review added for book {} by user {}: {} stars", bookId, userId, request.getRating());
    }

    public void updateReview(String userId, String bookId, ReviewRequest request) {
        request.setBookId(bookId);
        addReview(userId, request); // same logic
    }

    public void deleteReview(String userId, String bookId) {
        User user = findUserByIdOrEmail(userId);
        String normalized = normalizeBookId(bookId);

        user.getUserBooks().stream()
                .filter(ub -> normalized.equals(normalizeBookId(ub.getBookId())))
                .findFirst()
                .ifPresent(ub -> {
                    ub.setReview(null);
                    ub.setRating(0);
                    userRepository.save(user);
                });
    }
}