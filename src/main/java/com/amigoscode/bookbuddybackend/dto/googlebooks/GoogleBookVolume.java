//package com.amigoscode.bookbuddybackend.dto.googlebooks;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Getter
//@Setter
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class GoogleBookVolume {
//
//    @JsonProperty("id")
//    private String id;  // Volume ID, e.g., ZyTCAlFPjgYC
//
//    @JsonProperty("volumeInfo")
//    private VolumeInfo volumeInfo;
//
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class VolumeInfo {
//        @JsonProperty("title")
//        private String title;
//
//        @JsonProperty("authors")
//        private List<String> authors;
//
//        @JsonProperty("description")
//        private String description;
//
//        @JsonProperty("categories")
//        private List<String> categories;
//
//        @JsonProperty("imageLinks")
//        private ImageLinks imageLinks;
//
//        @JsonProperty("infoLink")
//        private String infoLink;
//
//        @JsonProperty("previewLink")
//        private String previewLink;
//
//        public String getTitle() { return title; }
//        public void setTitle(String title) { this.title = title; }
//        public List<String> getAuthors() { return authors; }
//        public void setAuthors(List<String> authors) { this.authors = authors; }
//        public String getDescription() { return description; }
//        public void setDescription(String description) { this.description = description; }
//        public List<String> getCategories() { return categories; }
//        public void setCategories(List<String> categories) { this.categories = categories; }
//        public ImageLinks getImageLinks() { return imageLinks; }
//        public void setImageLinks(ImageLinks imageLinks) { this.imageLinks = imageLinks; }
//        public String getInfoLink() { return infoLink; }
//        public void setInfoLink(String infoLink) { this.infoLink = infoLink; }
//        public String getPreviewLink() { return previewLink; }
//        public void setPreviewLink(String previewLink) { this.previewLink = previewLink; }
//    }
//
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class ImageLinks {
//        @JsonProperty("smallThumbnail")
//        private String smallThumbnail;
//
//        @JsonProperty("thumbnail")
//        private String thumbnail;
//
//        @JsonProperty("small")
//        private String small;
//
//        @JsonProperty("medium")
//        private String medium;
//
//        @JsonProperty("large")
//        private String large;
//
//        @JsonProperty("extraLarge")
//        private String extraLarge;
//
//        public String getSmallThumbnail() { return smallThumbnail; }
//        public void setSmallThumbnail(String smallThumbnail) { this.smallThumbnail = smallThumbnail; }
//        public String getThumbnail() { return thumbnail; }
//        public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
//        public String getSmall() { return small; }
//        public void setSmall(String small) { this.small = small; }
//        public String getMedium() { return medium; }
//        public void setMedium(String medium) { this.medium = medium; }
//        public String getLarge() { return large; }
//        public void setLarge(String large) { this.large = large; }
//        public String getExtraLarge() { return extraLarge; }
//        public void setExtraLarge(String extraLarge) { this.extraLarge = extraLarge; }
//    }
//}

package com.amigoscode.bookbuddybackend.dto.googlebooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookVolume {

    @JsonProperty("id")
    private String id;  // Volume ID, e.g., ZyTCAlFPjgYC

    @JsonProperty("volumeInfo")
    private VolumeInfo volumeInfo;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VolumeInfo {
        @JsonProperty("title")
        private String title;

        @JsonProperty("subtitle")
        private String subtitle;

        @JsonProperty("authors")
        private List<String> authors;

        @JsonProperty("description")
        private String description;

        @JsonProperty("categories")
        private List<String> categories;

        @JsonProperty("pageCount")
        private Integer pageCount;

        @JsonProperty("publishedDate")
        private String publishedDate;

        @JsonProperty("averageRating")
        private Double averageRating;

        @JsonProperty("ratingsCount")
        private Integer ratingsCount;

        @JsonProperty("imageLinks")
        private ImageLinks imageLinks;

        @JsonProperty("infoLink")
        private String infoLink;

        @JsonProperty("previewLink")
        private String previewLink;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getSubtitle() { return subtitle; }
        public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
        public List<String> getAuthors() { return authors; }
        public void setAuthors(List<String> authors) { this.authors = authors; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
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
        public ImageLinks getImageLinks() { return imageLinks; }
        public void setImageLinks(ImageLinks imageLinks) { this.imageLinks = imageLinks; }
        public String getInfoLink() { return infoLink; }
        public void setInfoLink(String infoLink) { this.infoLink = infoLink; }
        public String getPreviewLink() { return previewLink; }
        public void setPreviewLink(String previewLink) { this.previewLink = previewLink; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageLinks {
        @JsonProperty("smallThumbnail")
        private String smallThumbnail;

        @JsonProperty("thumbnail")
        private String thumbnail;

        @JsonProperty("small")
        private String small;

        @JsonProperty("medium")
        private String medium;

        @JsonProperty("large")
        private String large;

        @JsonProperty("extraLarge")
        private String extraLarge;

        public String getSmallThumbnail() { return smallThumbnail; }
        public void setSmallThumbnail(String smallThumbnail) { this.smallThumbnail = smallThumbnail; }
        public String getThumbnail() { return thumbnail; }
        public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
        public String getSmall() { return small; }
        public void setSmall(String small) { this.small = small; }
        public String getMedium() { return medium; }
        public void setMedium(String medium) { this.medium = medium; }
        public String getLarge() { return large; }
        public void setLarge(String large) { this.large = large; }
        public String getExtraLarge() { return extraLarge; }
        public void setExtraLarge(String extraLarge) { this.extraLarge = extraLarge; }
    }
}