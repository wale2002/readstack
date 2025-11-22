//package com.amigoscode.bookbuddybackend.dto.request;
//
//import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
//import jakarta.validation.constraints.*;
//
//public class ReviewRequest {
//
//    @NotBlank(message = "Book ID is required")
//    @Size(min = 1, max = 100, message = "Book ID must be between 1 and 100 characters")
//    private String bookId;
//
//    @Size(max = 2000, message = "Review cannot exceed 2000 characters")
//    private String reviewText;  // renamed for clarity (was 'review')
//
//    @Min(value = 1, message = "Rating must be at least 1")
//    @Max(value = 5, message = "Rating cannot exceed 5")
//    private Integer rating;  // optional
//
//    private ShelfType newShelf;  // renamed to match AddBookRequest/MoveShelfRequest pattern
//
//    // --- Constructors ---
//    public ReviewRequest() {}
//
//    public ReviewRequest(String bookId, String reviewText, Integer rating, ShelfType newShelf) {
//        this.bookId = bookId;
//        this.reviewText = reviewText;
//        this.rating = rating;
//        this.newShelf = newShelf;
//    }
//
//    // --- Getters and Setters ---
//    public String getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(String bookId) {
//        this.bookId = bookId;
//    }
//
//    // Primary modern getter
//    public String getReviewText() {
//        return reviewText;
//    }
//
//    public void setReviewText(String reviewText) {
//        this.reviewText = reviewText;
//    }
//
//    // Backward-compatible alias (so old code using getReview() still works)
//    public String getReview() {
//        return reviewText;
//    }
//
//    public void setReview(String reviewText) {
//        this.reviewText = reviewText;
//    }
//
//    public Integer getRating() {
//        return rating;
//    }
//
//    public void setRating(Integer rating) {
//        this.rating = rating;
//    }
//
//    public ShelfType getNewShelf() {
//        return newShelf;
//    }
//
//    public void setNewShelf(ShelfType newShelf) {
//        this.newShelf = newShelf;
//    }
//
//    // Backward-compatible alias
//    public ShelfType getMoveToShelf() {
//        return newShelf;
//    }
//
//    public void setMoveToShelf(ShelfType newShelf) {
//        this.newShelf = newShelf;
//    }
//}

package com.amigoscode.bookbuddybackend.dto.request;

public class ReviewRequest {

    private String bookId;
    private String reviewText;
    private int rating; // e.g., 0-5

    public ReviewRequest() { }

    public ReviewRequest(String bookId, String reviewText, int rating) {
        this.bookId = bookId;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
