//package com.amigoscode.bookbuddybackend.model.embedded;
//
//import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//public class UserBook {
//
//    @Field
//    private String bookId;
//
//    @Field
//    private ShelfType shelfType;
//
//    @Field
//    private int progress = 0; // Percentage
//
//    @Field
//    private int rating = 0;
//
//    @Field
//    private String review;
//
//    @Field
//    private String notes;
//
//    // Getters and Setters
//
//    public String getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(String bookId) {
//        this.bookId = bookId;
//    }
//
//    public ShelfType getShelfType() {
//        return shelfType;
//    }
//
//    public void setShelfType(ShelfType shelfType) {
//        this.shelfType = shelfType;
//    }
//
//    public int getProgress() {
//        return progress;
//    }
//
//    public void setProgress(int progress) {
//        this.progress = progress;
//    }
//
//    public int getRating() {
//        return rating;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }
//
//    public String getReview() {
//        return review;
//    }
//
//    public void setReview(String review) {
//        this.review = review;
//    }
//
//    public String getNotes() {
//        return notes;
//    }
//
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }
//}



package com.amigoscode.bookbuddybackend.model.embedded;

import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

public class UserBook {

    @Field
    private String bookId;

    @Field
    private ShelfType shelfType;

    @Field
    private int progress = 0; // Percentage

    @Field
    private int rating = 0;

    @Field
    private String review;

    @Field
    private String notes;

    // ---- metadata fields (new) ----
    @Field
    private String title;

    @Field
    private List<String> authors = new ArrayList<>();

    @Field
    private String subtitle;

    @Field
    private Integer pageCount;

    @Field
    private String publishedDate;

    @Field
    private String coverImageUrl;

    @Field
    private String smallCoverImageUrl;

    @Field
    private List<String> categories = new ArrayList<>();
    // -------------------------------

    // Getters and Setters

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public ShelfType getShelfType() {
        return shelfType;
    }

    public void setShelfType(ShelfType shelfType) {
        this.shelfType = shelfType;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getSmallCoverImageUrl() {
        return smallCoverImageUrl;
    }

    public void setSmallCoverImageUrl(String smallCoverImageUrl) {
        this.smallCoverImageUrl = smallCoverImageUrl;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
