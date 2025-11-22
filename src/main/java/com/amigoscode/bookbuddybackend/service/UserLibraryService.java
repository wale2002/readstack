//////////package com.amigoscode.bookbuddybackend.service;
//////////
//////////import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
//////////import com.amigoscode.bookbuddybackend.dto.request.ReviewRequest;
//////////import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
//////////import com.amigoscode.bookbuddybackend.dto.response.ShelfResponse;
//////////import com.amigoscode.bookbuddybackend.model.User;
//////////import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
//////////import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
//////////import com.amigoscode.bookbuddybackend.repository.UserRepository;
//////////import org.springframework.stereotype.Service;
//////////
//////////import java.util.List;
//////////import java.util.Optional;
//////////
//////////@Service
//////////public class UserLibraryService {
//////////
//////////    private final UserRepository userRepository;
//////////
//////////    public UserLibraryService(UserRepository userRepository) {
//////////        this.userRepository = userRepository;
//////////    }
//////////
//////////    public ShelfResponse getShelves(String userId) {
//////////        User user = userRepository.findById(userId).orElseThrow();
//////////        // Logic to group UserBooks by ShelfType and return ShelfResponse
//////////        return new ShelfResponse(); // Placeholder
//////////    }
//////////
//////////    public void addBookToShelf(String userId, AddBookRequest request) {
//////////        User user = userRepository.findById(userId).orElseThrow();
//////////        UserBook userBook = new UserBook();
//////////        userBook.setBookId(request.getBookId());
//////////        userBook.setShelfType(request.getShelfType());
//////////        // Set other fields
//////////        user.getUserBooks().add(userBook);
//////////        userRepository.save(user);
//////////    }
//////////
//////////    public void updateProgress(String userId, UpdateProgressRequest request) {
//////////        User user = userRepository.findById(userId).orElseThrow();
//////////        Optional<UserBook> userBookOpt = user.getUserBooks().stream().filter(ub -> ub.getBookId().equals(request.getBookId())).findFirst();
//////////        if (userBookOpt.isPresent()) {
//////////            UserBook userBook = userBookOpt.get();
//////////            userBook.setProgress(request.getProgress());
//////////            if (request.getProgress() >= 100) {
//////////                userBook.setShelfType(ShelfType.READ);
//////////            }
//////////            userRepository.save(user);
//////////        }
//////////    }
//////////
//////////    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) {
//////////        User user = userRepository.findById(userId).orElseThrow();
//////////        Optional<UserBook> userBookOpt = user.getUserBooks().stream().filter(ub -> ub.getBookId().equals(bookId)).findFirst();
//////////        if (userBookOpt.isPresent()) {
//////////            userBookOpt.get().setShelfType(newShelf);
//////////            userRepository.save(user);
//////////        }
//////////    }
//////////
//////////    public void addReview(String userId, ReviewRequest request) {
//////////        User user = userRepository.findById(userId).orElseThrow();
//////////        Optional<UserBook> userBookOpt = user.getUserBooks().stream().filter(ub -> ub.getBookId().equals(request.getBookId())).findFirst();
//////////        if (userBookOpt.isPresent()) {
//////////            UserBook userBook = userBookOpt.get();
//////////            userBook.setReview(request.getReviewText());
//////////            userBook.setRating(request.getRating());
//////////            userRepository.save(user);
//////////        }
//////////    }
//////////
//////////    public void updateReview(String userId, String reviewId, ReviewRequest request) {
//////////        // Assuming reviewId is bookId for simplicity, as reviews are per book per user
//////////        addReview(userId, request); // Reuse logic
//////////    }
//////////
//////////    public void deleteReview(String userId, String reviewId) {
//////////        User user = userRepository.findById(userId).orElseThrow();
//////////        Optional<UserBook> userBookOpt = user.getUserBooks().stream().filter(ub -> ub.getBookId().equals(reviewId)).findFirst();
//////////        if (userBookOpt.isPresent()) {
//////////            UserBook userBook = userBookOpt.get();
//////////            userBook.setReview(null);
//////////            userBook.setRating(0);
//////////            userRepository.save(user);
//////////        }
//////////    }
//////////}
////////
////////package com.amigoscode.bookbuddybackend.service;
////////
////////import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
////////import com.amigoscode.bookbuddybackend.dto.request.ReviewRequest;
////////import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
////////import com.amigoscode.bookbuddybackend.dto.response.ShelfResponse;
////////import com.amigoscode.bookbuddybackend.model.User;
////////import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
////////import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
////////import com.amigoscode.bookbuddybackend.repository.UserRepository;
////////import org.springframework.stereotype.Service;
////////
////////import java.util.Optional;
////////
////////@Service
////////public class UserLibraryService {
////////
////////    private final UserRepository userRepository;
////////
////////    public UserLibraryService(UserRepository userRepository) {
////////        this.userRepository = userRepository;
////////    }
////////
////////    public ShelfResponse getShelves(String userId) {
////////        User user = userRepository.findById(userId)
////////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
////////        return new ShelfResponse(); // TODO: implement actual grouping
////////    }
////////
////////    public void addBookToShelf(String userId, AddBookRequest request) {
////////        User user = userRepository.findById(userId)
////////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
////////
////////        UserBook userBook = new UserBook();
////////        userBook.setBookId(request.getBookId());
////////        userBook.setShelfType(request.getShelfType());
////////        user.getUserBooks().add(userBook);
////////
////////        userRepository.save(user);
////////    }
////////
////////    public void updateProgress(String userId, UpdateProgressRequest request) {
////////        User user = userRepository.findById(userId)
////////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
////////
////////        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
////////                .filter(ub -> ub.getBookId().equals(request.getBookId()))
////////                .findFirst();
////////
////////        userBookOpt.ifPresent(userBook -> {
////////            userBook.setProgress(request.getProgress());
////////            if (request.getProgress() >= 100) userBook.setShelfType(ShelfType.READ);
////////            userRepository.save(user);
////////        });
////////    }
////////
////////    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) {
////////        User user = userRepository.findById(userId)
////////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
////////
////////        user.getUserBooks().stream()
////////                .filter(ub -> ub.getBookId().equals(bookId))
////////                .findFirst()
////////                .ifPresent(userBook -> {
////////                    userBook.setShelfType(newShelf);
////////                    userRepository.save(user);
////////                });
////////    }
////////
////////    public void addReview(String userId, ReviewRequest request) {
////////        User user = userRepository.findById(userId)
////////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
////////
////////        user.getUserBooks().stream()
////////                .filter(ub -> ub.getBookId().equals(request.getBookId()))
////////                .findFirst()
////////                .ifPresent(userBook -> {
////////                    userBook.setReview(request.getReviewText());
////////                    userBook.setRating(request.getRating());
////////                    userRepository.save(user);
////////                });
////////    }
////////
////////    public void deleteReview(String userId, String bookId) {
////////        User user = userRepository.findById(userId)
////////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
////////
////////        user.getUserBooks().stream()
////////                .filter(ub -> ub.getBookId().equals(bookId))
////////                .findFirst()
////////                .ifPresent(userBook -> {
////////                    userBook.setReview(null);
////////                    userBook.setRating(0);
////////                    userRepository.save(user);
////////                });
////////    }
////////}
////////
//////
//////
//////package com.amigoscode.bookbuddybackend.service;
//////
//////import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
//////import com.amigoscode.bookbuddybackend.dto.request.ReviewRequest;
//////import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
//////import com.amigoscode.bookbuddybackend.dto.response.ShelfResponse;
//////import com.amigoscode.bookbuddybackend.model.User;
//////import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
//////import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
//////import com.amigoscode.bookbuddybackend.repository.UserRepository;
//////import org.springframework.stereotype.Service;
//////
//////import java.util.Optional;
//////
//////@Service
//////public class UserLibraryService {
//////
//////    private final UserRepository userRepository;
//////
//////    public UserLibraryService(UserRepository userRepository) {
//////        this.userRepository = userRepository;
//////    }
//////
//////    public ShelfResponse getShelves(String userId) {
//////        User user = userRepository.findById(userId)
//////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
//////        return new ShelfResponse(); // TODO: implement actual grouping
//////    }
//////
//////    public void addBookToShelf(String userId, AddBookRequest request) {
//////        User user = userRepository.findById(userId)
//////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
//////
//////        UserBook userBook = new UserBook();
//////        userBook.setBookId(request.getBookId());
//////        userBook.setShelfType(request.getShelfType());
//////        user.getUserBooks().add(userBook);
//////
//////        userRepository.save(user);
//////    }
//////
//////    public void updateProgress(String userId, UpdateProgressRequest request) {
//////        User user = userRepository.findById(userId)
//////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
//////
//////        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
//////                .filter(ub -> ub.getBookId().equals(request.getBookId()))
//////                .findFirst();
//////
//////        userBookOpt.ifPresent(userBook -> {
//////            userBook.setProgress(request.getProgress());
//////            if (request.getProgress() >= 100) userBook.setShelfType(ShelfType.READ);
//////            userRepository.save(user);
//////        });
//////    }
//////
//////    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) {
//////        User user = userRepository.findById(userId)
//////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
//////
//////        user.getUserBooks().stream()
//////                .filter(ub -> ub.getBookId().equals(bookId))
//////                .findFirst()
//////                .ifPresent(userBook -> {
//////                    userBook.setShelfType(newShelf);
//////                    userRepository.save(user);
//////                });
//////    }
//////
//////    public void addReview(String userId, ReviewRequest request) {
//////        User user = userRepository.findById(userId)
//////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
//////
//////        user.getUserBooks().stream()
//////                .filter(ub -> ub.getBookId().equals(request.getBookId()))
//////                .findFirst()
//////                .ifPresent(userBook -> {
//////                    userBook.setReview(request.getReviewText());
//////                    userBook.setRating(request.getRating());
//////                    userRepository.save(user);
//////                });
//////    }
//////
//////    public void updateReview(String userId, String reviewId, ReviewRequest request) {
//////        User user = userRepository.findById(userId)
//////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
//////
//////        user.getUserBooks().stream()
//////                .filter(ub -> ub.getBookId().equals(reviewId))  // Assuming reviewId == bookId
//////                .findFirst()
//////                .ifPresent(userBook -> {
//////                    userBook.setReview(request.getReviewText());
//////                    userBook.setRating(request.getRating());
//////                    userRepository.save(user);
//////                });
//////    }
//////
//////    public void deleteReview(String userId, String bookId) {
//////        User user = userRepository.findById(userId)
//////                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
//////
//////        user.getUserBooks().stream()
//////                .filter(ub -> ub.getBookId().equals(bookId))
//////                .findFirst()
//////                .ifPresent(userBook -> {
//////                    userBook.setReview(null);
//////                    userBook.setRating(0);
//////                    userRepository.save(user);
//////                });
//////    }
//////}
////
////
////package com.amigoscode.bookbuddybackend.service;
////
////import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
////import com.amigoscode.bookbuddybackend.dto.request.ReviewRequest;
////import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
////import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
////import com.amigoscode.bookbuddybackend.dto.response.ShelfResponse;
////import com.amigoscode.bookbuddybackend.model.User;
////import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
////import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
////import com.amigoscode.bookbuddybackend.repository.UserRepository;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.stereotype.Service;
////
////import java.util.*;
////import java.util.stream.Collectors;
////
////@Slf4j
////@Service
////public class UserLibraryService {
////
////    private final UserRepository userRepository;
////    private final ExternalBookService externalBookService;
////
////    public UserLibraryService(UserRepository userRepository,
////                              ExternalBookService externalBookService) {
////        this.userRepository = userRepository;
////        this.externalBookService = externalBookService;
////    }
////
////    /**
////     * Try to find the user by id first; if not found, try by email.
////     * Throws IllegalArgumentException if not found.
////     */
////    private User findUserByIdOrEmail(String idOrEmail) {
////        Optional<User> byId = userRepository.findById(idOrEmail);
////        if (byId.isPresent()) return byId.get();
////
////        Optional<User> byEmail = userRepository.findByEmail(idOrEmail);
////        if (byEmail.isPresent()) return byEmail.get();
////
////        throw new IllegalArgumentException("User not found: " + idOrEmail);
////    }
////
////    public ShelfResponse getShelves(String userId) {
////        User user = findUserByIdOrEmail(userId);
////
////        // Example grouping into a ShelfResponse (build depending on your DTO structure)
////        ShelfResponse response = new ShelfResponse();
////        Map<ShelfType, List<UserBook>> grouped = user.getUserBooks().stream()
////                .filter(Objects::nonNull)
////                .collect(Collectors.groupingBy(ub -> ub.getShelfType() == null ? ShelfType.WANT_TO_READ : ub.getShelfType()));
////
////        response.setCurrentlyReading(grouped.getOrDefault(ShelfType.CURRENTLY_READING, Collections.emptyList()));
////        response.setWantToRead(grouped.getOrDefault(ShelfType.WANT_TO_READ, Collections.emptyList()));
////        response.setRead(grouped.getOrDefault(ShelfType.READ, Collections.emptyList()));
////
////        return response;
////    }
////
////    public void addBookToShelf(String userId, AddBookRequest request) {
////        User user = findUserByIdOrEmail(userId);
////
////        if (request == null || request.getBookId() == null || request.getBookId().isBlank()) {
////            throw new IllegalArgumentException("bookId is required");
////        }
////
////        String requestedBookId = request.getBookId().trim();
////
////        // If the book already exists in user's books, update shelf/notes instead of duplicating
////        Optional<UserBook> existingOpt = user.getUserBooks()
////                .stream()
////                .filter(ub -> requestedBookId.equals(ub.getBookId()))
////                .findFirst();
////
////        if (existingOpt.isPresent()) {
////            UserBook existing = existingOpt.get();
////            if (request.getShelfType() != null) existing.setShelfType(request.getShelfType());
////            if (request.getNotes() != null) existing.setNotes(request.getNotes());
////            userRepository.save(user);
////            log.debug("Updated existing UserBook {} for user {}", requestedBookId, userId);
////            return;
////        }
////
////        // Get metadata from ExternalBookService
////        BookSearchResponse metadata = externalBookService.getBookDetails(requestedBookId);
////
////        UserBook ub = new UserBook();
////        ub.setBookId(metadata.getBookId() != null ? metadata.getBookId() : requestedBookId);
////        ub.setShelfType(request.getShelfType() != null ? request.getShelfType() : ShelfType.WANT_TO_READ);
////        ub.setNotes(request.getNotes());
////
////        // map metadata fields (null-safe)
////        ub.setTitle(metadata.getTitle());
////        if (metadata.getAuthors() != null) ub.setAuthors(metadata.getAuthors());
////        ub.setSubtitle(metadata.getSubtitle());
////        ub.setPageCount(metadata.getPageCount());
////        ub.setPublishedDate(metadata.getPublishedDate());
////        ub.setCoverImageUrl(metadata.getCoverImageUrl());
////        ub.setSmallCoverImageUrl(metadata.getSmallCoverImageUrl());
////        if (metadata.getCategories() != null) ub.setCategories(metadata.getCategories());
////
////        user.getUserBooks().add(ub);
////        userRepository.save(user);
////
////        log.info("Added book {} to user {} (title={})", ub.getBookId(), userId, ub.getTitle());
////    }
////
////    public void updateProgress(String userId, UpdateProgressRequest request) {
////        User user = findUserByIdOrEmail(userId);
////
////        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
////                .filter(ub -> ub.getBookId().equals(request.getBookId()))
////                .findFirst();
////
////        if (userBookOpt.isPresent()) {
////            UserBook userBook = userBookOpt.get();
////            userBook.setProgress(Math.max(0, Math.min(100, request.getProgress())));
////            if (request.getProgress() >= 100) {
////                userBook.setShelfType(ShelfType.READ);
////            }
////            userRepository.save(user);
////            log.debug("Updated progress for user {} book {} -> {}", userId, request.getBookId(), request.getProgress());
////        } else {
////            throw new IllegalArgumentException("Book not found in user library: " + request.getBookId());
////        }
////    }
////
////    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) {
////        User user = findUserByIdOrEmail(userId);
////
////        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
////                .filter(ub -> ub.getBookId().equals(bookId))
////                .findFirst();
////
////        if (userBookOpt.isPresent()) {
////            userBookOpt.get().setShelfType(newShelf);
////            userRepository.save(user);
////            log.debug("Moved book {} to shelf {} for user {}", bookId, newShelf, userId);
////        } else {
////            throw new IllegalArgumentException("Book not found in user library: " + bookId);
////        }
////    }
////
////    public void addReview(String userId, ReviewRequest request) {
////        User user = findUserByIdOrEmail(userId);
////
////        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
////                .filter(ub -> ub.getBookId().equals(request.getBookId()))
////                .findFirst();
////
////        if (userBookOpt.isPresent()) {
////            UserBook ub = userBookOpt.get();
////            ub.setReview(request.getReviewText());
////            ub.setRating(Math.max(0, request.getRating()));
////            userRepository.save(user);
////            log.debug("Added/updated review for user {} book {}", userId, request.getBookId());
////        } else {
////            // If the book is not present in user's library, optionally add it automatically (choice made here: add it)
////            log.debug("Book {} not in user {} library: adding automatically before saving review", request.getBookId(), userId);
////
////            AddBookRequest add = new AddBookRequest();
////            add.setBookId(request.getBookId());
////            add.setShelfType(ShelfType.WANT_TO_READ); // default shelf
////            add.setNotes(null);
////            addBookToShelf(userId, add);
////
////            // Now find and set review
////            User updated = findUserByIdOrEmail(userId);
////            Optional<UserBook> ub2 = updated.getUserBooks().stream()
////                    .filter(x -> x.getBookId().equals(request.getBookId()))
////                    .findFirst();
////
////            if (ub2.isPresent()) {
////                ub2.get().setReview(request.getReviewText());
////                ub2.get().setRating(Math.max(0, request.getRating()));
////                userRepository.save(updated);
////            } else {
////                throw new IllegalStateException("Failed to add book + review for " + request.getBookId());
////            }
////        }
////    }
////
////    public void updateReview(String userId, String reviewId, ReviewRequest request) {
////        // reviewId used as bookId for per-user-per-book reviews
////        addReview(userId, request);
////    }
////
////    public void deleteReview(String userId, String reviewId) {
////        User user = findUserByIdOrEmail(userId);
////
////        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
////                .filter(ub -> ub.getBookId().equals(reviewId))
////                .findFirst();
////
////        if (userBookOpt.isPresent()) {
////            UserBook ub = userBookOpt.get();
////            ub.setReview(null);
////            ub.setRating(0);
////            userRepository.save(user);
////            log.debug("Deleted review for user {} book {}", userId, reviewId);
////        } else {
////            throw new IllegalArgumentException("Book not found in user library: " + reviewId);
////        }
////    }
////}
//
//
//package com.amigoscode.bookbuddybackend.service;
//
//import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
//import com.amigoscode.bookbuddybackend.dto.request.ReviewRequest;
//import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
//import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
//import com.amigoscode.bookbuddybackend.dto.response.ShelfResponse;
//import com.amigoscode.bookbuddybackend.model.User;
//import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
//import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
//import com.amigoscode.bookbuddybackend.repository.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//public class UserLibraryService {
//
//    private final UserRepository userRepository;
//    private final ExternalBookService externalBookService;
//
//    public UserLibraryService(UserRepository userRepository,
//                              ExternalBookService externalBookService) {
//        this.userRepository = userRepository;
//        this.externalBookService = externalBookService;
//    }
//
//    private User findUserByIdOrEmail(String idOrEmail) {
//        Optional<User> byId = userRepository.findById(idOrEmail);
//        if (byId.isPresent()) return byId.get();
//
//        Optional<User> byEmail = userRepository.findByEmail(idOrEmail);
//        if (byEmail.isPresent()) return byEmail.get();
//
//        throw new IllegalArgumentException("User not found: " + idOrEmail);
//    }
//
//    public ShelfResponse getShelves(String userId) {
//        User user = findUserByIdOrEmail(userId);
//
//        Map<ShelfType, List<UserBook>> grouped = user.getUserBooks().stream()
//                .filter(Objects::nonNull)
//                .collect(Collectors.groupingBy(ub -> ub.getShelfType() == null ? ShelfType.WANT_TO_READ : ub.getShelfType()));
//
//        ShelfResponse response = new ShelfResponse();
//        response.setCurrentlyReading(grouped.getOrDefault(ShelfType.CURRENTLY_READING, Collections.emptyList()));
//        response.setWantToRead(grouped.getOrDefault(ShelfType.WANT_TO_READ, Collections.emptyList()));
//        response.setRead(grouped.getOrDefault(ShelfType.READ, Collections.emptyList()));
//
//        return response;
//    }
//
//    public void addBookToShelf(String userId, AddBookRequest request) {
//        User user = findUserByIdOrEmail(userId);
//
//        if (request == null || request.getBookId() == null || request.getBookId().isBlank()) {
//            throw new IllegalArgumentException("bookId is required");
//        }
//
//        String requestedBookId = request.getBookId().trim();
//
//        Optional<UserBook> existingOpt = user.getUserBooks()
//                .stream()
//                .filter(ub -> requestedBookId.equals(ub.getBookId()))
//                .findFirst();
//
//        if (existingOpt.isPresent()) {
//            UserBook existing = existingOpt.get();
//            if (request.getShelfType() != null) existing.setShelfType(request.getShelfType());
//            if (request.getNotes() != null) existing.setNotes(request.getNotes());
//            userRepository.save(user);
//            log.debug("Updated existing UserBook {} for user {}", requestedBookId, userId);
//            return;
//        }
//
//        // Fix: Use BookDetailResponse instead of BookSearchResponse
//        BookDetailResponse metadata = externalBookService.getBookDetails(requestedBookId);
//
//        UserBook ub = new UserBook();
//        ub.setBookId(metadata.getBookId() != null ? metadata.getBookId() : requestedBookId);
//        ub.setShelfType(request.getShelfType() != null ? request.getShelfType() : ShelfType.WANT_TO_READ);
//        ub.setNotes(request.getNotes());
//
//        ub.setTitle(metadata.getTitle());
//        if (metadata.getAuthors() != null) ub.setAuthors(metadata.getAuthors());
////        ub.setSubtitle(metadata.getSubtitle());
////        ub.setPageCount(metadata.getPageCount());
////        ub.setPublishedDate(metadata.getPublishedDate());
//        ub.setCoverImageUrl(metadata.getCoverImageUrl());
//        ub.setSmallCoverImageUrl(metadata.getSmallCoverImageUrl());
//        if (metadata.getCategories() != null) ub.setCategories(metadata.getCategories());
//
//        user.getUserBooks().add(ub);
//        userRepository.save(user);
//
//        log.info("Added book {} to user {} (title={})", ub.getBookId(), userId, ub.getTitle());
//    }
//
//    public void updateProgress(String userId, UpdateProgressRequest request) {
//        User user = findUserByIdOrEmail(userId);
//
//        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
//                .filter(ub -> ub.getBookId().equals(request.getBookId()))
//                .findFirst();
//
//        if (userBookOpt.isPresent()) {
//            UserBook userBook = userBookOpt.get();
//            userBook.setProgress(Math.max(0, Math.min(100, request.getProgress())));
//            if (request.getProgress() >= 100) userBook.setShelfType(ShelfType.READ);
//            userRepository.save(user);
//            log.debug("Updated progress for user {} book {} -> {}", userId, request.getBookId(), request.getProgress());
//        } else {
//            throw new IllegalArgumentException("Book not found in user library: " + request.getBookId());
//        }
//    }
//
//    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) {
//        User user = findUserByIdOrEmail(userId);
//
//        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
//                .filter(ub -> ub.getBookId().equals(bookId))
//                .findFirst();
//
//        if (userBookOpt.isPresent()) {
//            userBookOpt.get().setShelfType(newShelf);
//            userRepository.save(user);
//            log.debug("Moved book {} to shelf {} for user {}", bookId, newShelf, userId);
//        } else {
//            throw new IllegalArgumentException("Book not found in user library: " + bookId);
//        }
//    }
//
//    public void addReview(String userId, ReviewRequest request) {
//        User user = findUserByIdOrEmail(userId);
//
//        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
//                .filter(ub -> ub.getBookId().equals(request.getBookId()))
//                .findFirst();
//
//        if (userBookOpt.isPresent()) {
//            UserBook ub = userBookOpt.get();
//            ub.setReview(request.getReviewText());
//            ub.setRating(Math.max(0, request.getRating()));
//            userRepository.save(user);
//            log.debug("Added/updated review for user {} book {}", userId, request.getBookId());
//        } else {
//            AddBookRequest add = new AddBookRequest();
//            add.setBookId(request.getBookId());
//            add.setShelfType(ShelfType.WANT_TO_READ);
//            addBookToShelf(userId, add);
//
//            User updated = findUserByIdOrEmail(userId);
//            Optional<UserBook> ub2 = updated.getUserBooks().stream()
//                    .filter(x -> x.getBookId().equals(request.getBookId()))
//                    .findFirst();
//
//            if (ub2.isPresent()) {
//                ub2.get().setReview(request.getReviewText());
//                ub2.get().setRating(Math.max(0, request.getRating()));
//                userRepository.save(updated);
//            } else {
//                throw new IllegalStateException("Failed to add book + review for " + request.getBookId());
//            }
//        }
//    }
//
//    public void updateReview(String userId, String reviewId, ReviewRequest request) {
//        addReview(userId, request);
//    }
//
//    public void deleteReview(String userId, String reviewId) {
//        User user = findUserByIdOrEmail(userId);
//
//        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
//                .filter(ub -> ub.getBookId().equals(reviewId))
//                .findFirst();
//
//        if (userBookOpt.isPresent()) {
//            UserBook ub = userBookOpt.get();
//            ub.setReview(null);
//            ub.setRating(0);
//            userRepository.save(user);
//            log.debug("Deleted review for user {} book {}", userId, reviewId);
//        } else {
//            throw new IllegalArgumentException("Book not found in user library: " + reviewId);
//        }
//    }
//}
//


package com.amigoscode.bookbuddybackend.service;

import com.amigoscode.bookbuddybackend.dto.request.AddBookRequest;
import com.amigoscode.bookbuddybackend.dto.request.ReviewRequest;
import com.amigoscode.bookbuddybackend.dto.request.UpdateProgressRequest;
import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
import com.amigoscode.bookbuddybackend.dto.response.ShelfResponse;
import com.amigoscode.bookbuddybackend.model.User;
import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
import com.amigoscode.bookbuddybackend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserLibraryService {

    private final UserRepository userRepository;
    private final ExternalBookService externalBookService;

    public UserLibraryService(UserRepository userRepository,
                              ExternalBookService externalBookService) {
        this.userRepository = userRepository;
        this.externalBookService = externalBookService;
    }

    private User findUserByIdOrEmail(String idOrEmail) {
        Optional<User> byId = userRepository.findById(idOrEmail);
        if (byId.isPresent()) return byId.get();

        Optional<User> byEmail = userRepository.findByEmail(idOrEmail);
        if (byEmail.isPresent()) return byEmail.get();

        throw new IllegalArgumentException("User not found: " + idOrEmail);
    }

    private String normalizeBookId(String bookId) {
        if (bookId == null) return null;
        return bookId.startsWith("/works/") ? bookId.substring(7) : bookId;
    }

    public ShelfResponse getShelves(String userId) {
        User user = findUserByIdOrEmail(userId);

        Map<ShelfType, List<UserBook>> grouped = user.getUserBooks().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(ub -> ub.getShelfType() == null ? ShelfType.WANT_TO_READ : ub.getShelfType()));

        ShelfResponse response = new ShelfResponse();
        response.setCurrentlyReading(grouped.getOrDefault(ShelfType.CURRENTLY_READING, Collections.emptyList()));
        response.setWantToRead(grouped.getOrDefault(ShelfType.WANT_TO_READ, Collections.emptyList()));
        response.setRead(grouped.getOrDefault(ShelfType.READ, Collections.emptyList()));

        return response;
    }

    public void addBookToShelf(String userId, AddBookRequest request) {
        User user = findUserByIdOrEmail(userId);

        if (request == null || request.getBookId() == null || request.getBookId().isBlank()) {
            throw new IllegalArgumentException("bookId is required");
        }

        String normalizedBookId = normalizeBookId(request.getBookId().trim());

        // Prevent duplicate books
        Optional<UserBook> existingOpt = user.getUserBooks()
                .stream()
                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
                .findFirst();

        if (existingOpt.isPresent()) {
            UserBook existing = existingOpt.get();
            if (request.getShelfType() != null) existing.setShelfType(request.getShelfType());
            if (request.getNotes() != null) existing.setNotes(request.getNotes());
            userRepository.save(user);
            log.debug("Updated existing UserBook {} for user {}", normalizedBookId, userId);
            return;
        }

        // Fetch metadata from external service
        BookDetailResponse metadata = externalBookService.getBookDetails(normalizedBookId);

        UserBook ub = new UserBook();
        ub.setBookId(metadata.getBookId() != null ? metadata.getBookId() : normalizedBookId);
        ub.setShelfType(request.getShelfType() != null ? request.getShelfType() : ShelfType.WANT_TO_READ);
        ub.setNotes(request.getNotes());
        ub.setTitle(metadata.getTitle());
        ub.setAuthors(metadata.getAuthors());
        ub.setCoverImageUrl(metadata.getCoverImageUrl());
        ub.setSmallCoverImageUrl(metadata.getSmallCoverImageUrl());
        ub.setCategories(metadata.getCategories());

        user.getUserBooks().add(ub);
        userRepository.save(user);

        log.info("Added book {} to user {} (title={})", ub.getBookId(), userId, ub.getTitle());
    }

    public void updateProgress(String userId, UpdateProgressRequest request) {
        User user = findUserByIdOrEmail(userId);

        String normalizedBookId = normalizeBookId(request.getBookId());

        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
                .findFirst();

        if (userBookOpt.isPresent()) {
            UserBook userBook = userBookOpt.get();
            userBook.setProgress(Math.max(0, Math.min(100, request.getProgress())));
            if (request.getProgress() >= 100) userBook.setShelfType(ShelfType.READ);
            userRepository.save(user);
            log.debug("Updated progress for user {} book {} -> {}", userId, normalizedBookId, request.getProgress());
        } else {
            throw new IllegalArgumentException("Book not found in user library: " + request.getBookId());
        }
    }

    public void moveBookToShelf(String userId, String bookId, ShelfType newShelf) {
        User user = findUserByIdOrEmail(userId);

        String normalizedBookId = normalizeBookId(bookId);

        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
                .findFirst();

        if (userBookOpt.isPresent()) {
            userBookOpt.get().setShelfType(newShelf);
            userRepository.save(user);
            log.debug("Moved book {} to shelf {} for user {}", normalizedBookId, newShelf, userId);
        } else {
            throw new IllegalArgumentException("Book not found in user library: " + bookId);
        }
    }

    public void addReview(String userId, ReviewRequest request) {
        User user = findUserByIdOrEmail(userId);

        String normalizedBookId = normalizeBookId(request.getBookId());

        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
                .findFirst();

        if (userBookOpt.isPresent()) {
            UserBook ub = userBookOpt.get();
            ub.setReview(request.getReviewText());
            ub.setRating(Math.max(0, request.getRating()));
            userRepository.save(user);
            log.debug("Added/updated review for user {} book {}", userId, normalizedBookId);
        } else {
            // Add book first if missing
            AddBookRequest add = new AddBookRequest();
            add.setBookId(normalizedBookId);
            add.setShelfType(ShelfType.WANT_TO_READ);
            addBookToShelf(userId, add);

            User updated = findUserByIdOrEmail(userId);
            Optional<UserBook> ub2 = updated.getUserBooks().stream()
                    .filter(x -> normalizeBookId(x.getBookId()).equals(normalizedBookId))
                    .findFirst();

            if (ub2.isPresent()) {
                ub2.get().setReview(request.getReviewText());
                ub2.get().setRating(Math.max(0, request.getRating()));
                userRepository.save(updated);
            } else {
                throw new IllegalStateException("Failed to add book + review for " + normalizedBookId);
            }
        }
    }

    public void updateReview(String userId, String reviewId, ReviewRequest request) {
        addReview(userId, request);
    }

    public void deleteReview(String userId, String reviewId) {
        User user = findUserByIdOrEmail(userId);

        String normalizedBookId = normalizeBookId(reviewId);

        Optional<UserBook> userBookOpt = user.getUserBooks().stream()
                .filter(ub -> normalizeBookId(ub.getBookId()).equals(normalizedBookId))
                .findFirst();

        if (userBookOpt.isPresent()) {
            UserBook ub = userBookOpt.get();
            ub.setReview(null);
            ub.setRating(0);
            userRepository.save(user);
            log.debug("Deleted review for user {} book {}", userId, normalizedBookId);
        } else {
            throw new IllegalArgumentException("Book not found in user library: " + reviewId);
        }
    }
}
