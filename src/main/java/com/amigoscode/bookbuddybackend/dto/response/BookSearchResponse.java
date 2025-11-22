//package com.amigoscode.bookbuddybackend.dto.response;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
////public class BookSearchResponse {
////
////    @JsonProperty("bookId")
////    private String bookId;                // e.g., OL1234567M or ISBN:1234567890
////
////    private String title;
////
////    @JsonProperty("subtitle")
////    private String subtitle;              // optional
////
////    private List<String> authors;
////
////    private String description;
////
////    @JsonProperty("pageCount")
////    private Integer pageCount;
////
////    @JsonProperty("publishedDate")
////    private String publishedDate;         // e.g., "2023" or "2023-05-15"
////
////    @JsonProperty("coverImageUrl")
////    private String coverImageUrl;         // full URL to cover (large size preferred)
////
////    @JsonProperty("smallCoverImageUrl")
////    private String smallCoverImageUrl;    // thumbnail for lists
////
////    private List<String> categories;
////
////    @JsonProperty("averageRating")
////    private Double averageRating;         // from external source if available
////
////    @JsonProperty("ratingsCount")
////    private Integer ratingsCount;
////
////    // --- Constructors ---
////    public BookSearchResponse() {}
////
////    public BookSearchResponse(String bookId, String title, List<String> authors, String coverImageUrl) {
////        this.bookId = bookId;
////        this.title = title;
////        this.authors = authors;
////        this.coverImageUrl = coverImageUrl;
////    }
////
////    // --- Getters and Setters ---
////    public String getBookId() {
////        return bookId;
////    }
////
////    public void setBookId(String bookId) {
////        this.bookId = bookId;
////    }
////
////    public String getTitle() {
////        return title;
////    }
////
////    public void setTitle(String title) {
////        this.title = title;
////    }
////
////    public String getSubtitle() {
////        return subtitle;
////    }
////
////    public void setSubtitle(String subtitle) {
////        this.subtitle = subtitle;
////    }
////
////    public List<String> getAuthors() {
////        return authors;
////    }
////
////    public void setAuthors(List<String> authors) {
////        this.authors = authors;
////    }
////
////    public String getDescription() {
////        return description;
////    }
////
////    public void setDescription(String description) {
////        this.description = description;
////    }
////
////    public Integer getPageCount() {
////        return pageCount;
////    }
////
////    public void setPageCount(Integer pageCount) {
////        this.pageCount = pageCount;
////    }
////
////    public String getPublishedDate() {
////        return publishedDate;
////    }
////
////    public void setPublishedDate(String publishedDate) {
////        this.publishedDate = publishedDate;
////    }
////
////    public String getCoverImageUrl() {
////        return coverImageUrl;
////    }
////
////    public void setCoverImageUrl(String coverImageUrl) {
////        this.coverImageUrl = coverImageUrl;
////    }
////
////    public String getSmallCoverImageUrl() {
////        return smallCoverImageUrl;
////    }
////
////    public void setSmallCoverImageUrl(String smallCoverImageUrl) {
////        this.smallCoverImageUrl = smallCoverImageUrl;
////    }
////
////    public List<String> getCategories() {
////        return categories;
////    }
////
////    public void setCategories(List<String> categories) {
////        this.categories = categories;
////    }
////
////    public Double getAverageRating() {
////        return averageRating;
////    }
////
////    public void setAverageRating(Double averageRating) {
////        this.averageRating = averageRating;
////    }
////
////    public Integer getRatingsCount() {
////        return ratingsCount;
////    }
////
////    public void setRatingsCount(Integer ratingsCount) {
////        this.ratingsCount = ratingsCount;
////    }
////}
//
//
//@Getter
//public class BookSearchResponse {
//    // --- Getters and Setters ---
//    @JsonProperty("bookId")
//   private String bookId;                // e.g., OL1234567M or ISBN:1234567890
//
//    @Setter
//    private String title;
//
//    @Setter
//    @JsonProperty("subtitle")
//    private String subtitle;              // optional
//
//    @Setter
//    private List<String> authors;
//
//    @Setter
//    private String description;
//
//    @Setter
//    @JsonProperty("pageCount")
//    private Integer pageCount;
//
//    @Setter
//    @JsonProperty("publishedDate")
//    private String publishedDate;         // e.g., "2023" or "2023-05-15"
//
//    @Setter
//    @JsonProperty("coverImageUrl")
//    private String coverImageUrl;         // full URL to cover (large size preferred)
//
//    @Setter
//    @JsonProperty("smallCoverImageUrl")
//    private String smallCoverImageUrl;    // thumbnail for lists
//
//    @Setter
//    private List<String> categories;
//
//    @Setter
//    @JsonProperty("averageRating")
//    private Double averageRating;         // from external source if available
//
//    @Setter
//    @JsonProperty("ratingsCount")
//    private Integer ratingsCount;
//
//    // --- Constructors ---
//    public BookSearchResponse() {}
//
//    public BookSearchResponse(String bookId, String title, List<String> authors, String coverImageUrl) {
//        setBookId(bookId);
//        this.title = title;
//        this.authors = authors;
//        this.coverImageUrl = coverImageUrl;
//    }
//
//    public void setBookId(String bookId) {
//        if (bookId != null && bookId.startsWith("/works/")) {
//            this.bookId = bookId.substring(7);  // Strip the "/works/" prefix to get "OL82548W"
//        } else {
//            this.bookId = bookId;
//        }
//    }
//
//}
//



package com.amigoscode.bookbuddybackend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class BookSearchResponse {

    @JsonProperty("bookId")
    private String bookId;

    @Setter
    private String title;

    @Setter
    @JsonProperty("subtitle")
    private String subtitle;

    @Setter
    private List<String> authors;

    @Setter
    private String description;

    @Setter
    @JsonProperty("pageCount")
    private Integer pageCount;

    @Setter
    @JsonProperty("publishedDate")
    private String publishedDate;

    @Setter
    @JsonProperty("coverImageUrl")
    private String coverImageUrl;

    @Setter
    @JsonProperty("smallCoverImageUrl")
    private String smallCoverImageUrl;

    @Setter
    private List<String> categories;

    @Setter
    @JsonProperty("averageRating")
    private Double averageRating;

    @Setter
    @JsonProperty("ratingsCount")
    private Integer ratingsCount;

    // --- Constructors ---
    public BookSearchResponse() {}

    public BookSearchResponse(String bookId, String title, List<String> authors, String coverImageUrl) {
        setBookId(bookId);
        this.title = title;
        this.authors = authors;
        this.coverImageUrl = coverImageUrl;
    }

    public void setBookId(String bookId) {
        if (bookId != null && bookId.startsWith("/works/")) {
            this.bookId = bookId.substring(7); // remove "/works/"
        } else {
            this.bookId = bookId;
        }
    }
}
