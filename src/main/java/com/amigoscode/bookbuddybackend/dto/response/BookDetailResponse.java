////////
//////////package com.amigoscode.bookbuddybackend.dto.response;
//////////
//////////import java.util.List;
//////////
//////////public class BookDetailResponse {
//////////
//////////    private String bookId;
//////////    private String title;
//////////    private List<String> authors;
//////////    private String description;
//////////    private String coverImageUrl;
//////////    private String smallCoverImageUrl;
//////////
//////////    // NEW: categories field
//////////    private List<String> categories;
//////////
//////////    // -----------------------------
//////////    // Getters and Setters
//////////    // -----------------------------
//////////    public String getBookId() {
//////////        return bookId;
//////////    }
//////////
//////////    public void setBookId(String bookId) {
//////////        this.bookId = bookId;
//////////    }
//////////
//////////    public String getTitle() {
//////////        return title;
//////////    }
//////////
//////////    public void setTitle(String title) {
//////////        this.title = title;
//////////    }
//////////
//////////    public List<String> getAuthors() {
//////////        return authors;
//////////    }
//////////
//////////    public void setAuthors(List<String> authors) {
//////////        this.authors = authors;
//////////    }
//////////
//////////    public String getDescription() {
//////////        return description;
//////////    }
//////////
//////////    public void setDescription(String description) {
//////////        this.description = description;
//////////    }
//////////
//////////    public String getCoverImageUrl() {
//////////        return coverImageUrl;
//////////    }
//////////
//////////    public void setCoverImageUrl(String coverImageUrl) {
//////////        this.coverImageUrl = coverImageUrl;
//////////    }
//////////
//////////    public String getSmallCoverImageUrl() {
//////////        return smallCoverImageUrl;
//////////    }
//////////
//////////    public void setSmallCoverImageUrl(String smallCoverImageUrl) {
//////////        this.smallCoverImageUrl = smallCoverImageUrl;
//////////    }
//////////
//////////    public List<String> getCategories() {
//////////        return categories;
//////////    }
//////////
//////////    public void setCategories(List<String> categories) {
//////////        this.categories = categories;
//////////    }
//////////}
//////////
////////
////////package com.amigoscode.bookbuddybackend.dto.response;
////////
////////import java.util.List;
////////
////////public class BookDetailResponse {
////////
////////    private String bookId;
////////    private String title;
////////    private List<String> authors;
////////    private String description;
////////    private List<String> categories;
////////    private String coverImageUrl;
////////    private String smallCoverImageUrl;
////////
////////    public BookDetailResponse() {}
////////
////////    public String getBookId() {
////////        return bookId;
////////    }
////////
////////    public void setBookId(String bookId) {
////////        this.bookId = bookId;
////////    }
////////
////////    public String getTitle() {
////////        return title;
////////    }
////////
////////    public void setTitle(String title) {
////////        this.title = title;
////////    }
////////
////////    public List<String> getAuthors() {
////////        return authors;
////////    }
////////
////////    public void setAuthors(List<String> authors) {
////////        this.authors = authors;
////////    }
////////
////////    public String getDescription() {
////////        return description;
////////    }
////////
////////    public void setDescription(String description) {
////////        this.description = description;
////////    }
////////
////////    public List<String> getCategories() {
////////        return categories;
////////    }
////////
////////    public void setCategories(List<String> categories) {
////////        this.categories = categories;
////////    }
////////
////////    public String getCoverImageUrl() {
////////        return coverImageUrl;
////////    }
////////
////////    public void setCoverImageUrl(String coverImageUrl) {
////////        this.coverImageUrl = coverImageUrl;
////////    }
////////
////////    public String getSmallCoverImageUrl() {
////////        return smallCoverImageUrl;
////////    }
////////
////////    public void setSmallCoverImageUrl(String smallCoverImageUrl) {
////////        this.smallCoverImageUrl = smallCoverImageUrl;
////////    }
////////}
////////
//////
//////
//////package com.amigoscode.bookbuddybackend.dto.response;
//////
//////import java.util.List;
//////
//////public class BookDetailResponse {
//////
//////    private String bookId;
//////    private String title;
//////    private List<String> authors;
//////    private String description;
//////    private List<String> categories;
//////    private String coverImageUrl;
//////    private String smallCoverImageUrl;
//////
//////    // NEW: URL to read the book on Open Library
//////    private String readUrl;
//////
//////    public BookDetailResponse() {}
//////
//////    public String getBookId() {
//////        return bookId;
//////    }
//////
//////    public void setBookId(String bookId) {
//////        this.bookId = bookId;
//////    }
//////
//////    public String getTitle() {
//////        return title;
//////    }
//////
//////    public void setTitle(String title) {
//////        this.title = title;
//////    }
//////
//////    public List<String> getAuthors() {
//////        return authors;
//////    }
//////
//////    public void setAuthors(List<String> authors) {
//////        this.authors = authors;
//////    }
//////
//////    public String getDescription() {
//////        return description;
//////    }
//////
//////    public void setDescription(String description) {
//////        this.description = description;
//////    }
//////
//////    public List<String> getCategories() {
//////        return categories;
//////    }
//////
//////    public void setCategories(List<String> categories) {
//////        this.categories = categories;
//////    }
//////
//////    public String getCoverImageUrl() {
//////        return coverImageUrl;
//////    }
//////
//////    public void setCoverImageUrl(String coverImageUrl) {
//////        this.coverImageUrl = coverImageUrl;
//////    }
//////
//////    public String getSmallCoverImageUrl() {
//////        return smallCoverImageUrl;
//////    }
//////
//////    public void setSmallCoverImageUrl(String smallCoverImageUrl) {
//////        this.smallCoverImageUrl = smallCoverImageUrl;
//////    }
//////
//////    public String getReadUrl() {
//////        return readUrl;
//////    }
//////
//////    public void setReadUrl(String readUrl) {
//////        this.readUrl = readUrl;
//////    }
//////}
////
////
////package com.amigoscode.bookbuddybackend.dto.response;
////
////import java.util.List;
////
////public class BookDetailResponse {
////
////    private String bookId;
////    private String title;
////    private List<String> authors;
////    private String description;
////    private List<String> categories;
////    private String coverImageUrl;
////    private String smallCoverImageUrl;
////
////    // NEW: Multiple read links
////    private List<ReadLink> readLinks;
////
////    public BookDetailResponse() {}
////
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
////    public List<String> getCategories() {
////        return categories;
////    }
////
////    public void setCategories(List<String> categories) {
////        this.categories = categories;
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
////    public List<ReadLink> getReadLinks() {
////        return readLinks;
////    }
////
////    public void setReadLinks(List<ReadLink> readLinks) {
////        this.readLinks = readLinks;
////    }
////
////    // Inner class for multiple read links
////    public static class ReadLink {
////        private String platform;
////        private String url;
////
////        public ReadLink() {}
////
////        public ReadLink(String platform, String url) {
////            this.platform = platform;
////            this.url = url;
////        }
////
////        public String getPlatform() {
////            return platform;
////        }
////
////        public void setPlatform(String platform) {
////            this.platform = platform;
////        }
////
////        public String getUrl() {
////            return url;
////        }
////
////        public void setUrl(String url) {
////            this.url = url;
////        }
////    }
////}
//
//
//package com.amigoscode.bookbuddybackend.dto.response;
//
//import java.util.List;
//
//public class BookDetailResponse {
//
//    private String bookId;
//    private String title;
//    private List<String> authors;
//    private String description;
//    private String fallbackDescription; // Optional fallback from other platforms
//    private List<String> categories;
//    private String coverImageUrl;
//    private String smallCoverImageUrl;
//
//    // Multiple read links
//    private List<ReadLink> readLinks;
//
//    public BookDetailResponse() {}
//
//    // Getters & Setters
//    public String getBookId() { return bookId; }
//    public void setBookId(String bookId) { this.bookId = bookId; }
//
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//
//    public List<String> getAuthors() { return authors; }
//    public void setAuthors(List<String> authors) { this.authors = authors; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public String getFallbackDescription() { return fallbackDescription; }
//    public void setFallbackDescription(String fallbackDescription) { this.fallbackDescription = fallbackDescription; }
//
//    public List<String> getCategories() { return categories; }
//    public void setCategories(List<String> categories) { this.categories = categories; }
//
//    public String getCoverImageUrl() { return coverImageUrl; }
//    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
//
//    public String getSmallCoverImageUrl() { return smallCoverImageUrl; }
//    public void setSmallCoverImageUrl(String smallCoverImageUrl) { this.smallCoverImageUrl = smallCoverImageUrl; }
//
//    public List<ReadLink> getReadLinks() { return readLinks; }
//    public void setReadLinks(List<ReadLink> readLinks) { this.readLinks = readLinks; }
//
//    // Inner class for multiple read links
//    public static class ReadLink {
//        private String platform;
//        private String url;
//
//        public ReadLink() {}
//        public ReadLink(String platform, String url) {
//            this.platform = platform;
//            this.url = url;
//        }
//
//        public String getPlatform() { return platform; }
//        public void setPlatform(String platform) { this.platform = platform; }
//
//        public String getUrl() { return url; }
//        public void setUrl(String url) { this.url = url; }
//    }
//}


package com.amigoscode.bookbuddybackend.dto.response;

import java.util.List;

public class BookDetailResponse {

    private String bookId;
    private String title;
    private String subtitle;
    private List<String> authors;
    private String description;
    private String fallbackDescription; // Optional fallback from other platforms
    private List<String> categories;
    private Integer pageCount;
    private String publishedDate;
    private Double averageRating;
    private Integer ratingsCount;
    private String coverImageUrl;
    private String smallCoverImageUrl;

    // Multiple read links
    private List<ReadLink> readLinks;

    public BookDetailResponse() {}

    // Getters & Setters
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public List<String> getAuthors() { return authors; }
    public void setAuthors(List<String> authors) { this.authors = authors; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFallbackDescription() { return fallbackDescription; }
    public void setFallbackDescription(String fallbackDescription) { this.fallbackDescription = fallbackDescription; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    public Integer getPageCount() { return pageCount; }
    public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }

    public String getPublishedDate() { return publishedDate; }
    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public Integer getRatingsCount() { return ratingsCount; }
    public void setRatingsCount(Integer ratingsCount) { this.ratingsCount = ratingsCount; }

    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }

    public String getSmallCoverImageUrl() { return smallCoverImageUrl; }
    public void setSmallCoverImageUrl(String smallCoverImageUrl) { this.smallCoverImageUrl = smallCoverImageUrl; }

    public List<ReadLink> getReadLinks() { return readLinks; }
    public void setReadLinks(List<ReadLink> readLinks) { this.readLinks = readLinks; }

    // Inner class for multiple read links
    public static class ReadLink {
        private String platform;
        private String url;

        public ReadLink() {}
        public ReadLink(String platform, String url) {
            this.platform = platform;
            this.url = url;
        }

        public String getPlatform() { return platform; }
        public void setPlatform(String platform) { this.platform = platform; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }
}