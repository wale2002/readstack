////package com.amigoscode.bookbuddybackend.service;
////
////import com.amigoscode.bookbuddybackend.client.OpenLibraryClient;
////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibraryBookDoc;
////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibrarySearchResponse;
////import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
////import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
////import com.fasterxml.jackson.databind.JsonNode;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import org.springframework.stereotype.Service;
////import org.springframework.web.client.RestTemplate;
////
////import java.net.URLEncoder;
////import java.nio.charset.StandardCharsets;
////import java.util.ArrayList;
////import java.util.List;
////
////@Service
////public class ExternalBookService {
////
////    private final OpenLibraryClient openLibraryClient;
////    private final ObjectMapper mapper;
////    private final RestTemplate restTemplate;
////
////    public ExternalBookService(OpenLibraryClient openLibraryClient, ObjectMapper mapper) {
////        this.openLibraryClient = openLibraryClient;
////        this.mapper = mapper;
////        this.restTemplate = new RestTemplate();
////    }
////
////    // ------------------------
////    // SEARCH BOOKS
////    // ------------------------
////    public BookSearchResponse searchBooks(String query) {
////        try {
////            String rawResponse = openLibraryClient.searchBooks(query);
////            return mapJsonToBookSearchResponse(rawResponse);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return new BookSearchResponse();
////        }
////    }
////
////    // ------------------------
////    // GET BOOK DETAILS
////    // ------------------------
////    public BookDetailResponse getBookDetails(String bookId) {
////        try {
////            bookId = normalizeBookId(bookId);
////            String rawResponse;
////
////            if (bookId.toUpperCase().startsWith("OL") && bookId.toUpperCase().endsWith("W")) {
////                rawResponse = openLibraryClient.getBookByWorkId(bookId);
////            } else {
////                rawResponse = openLibraryClient.getBookByIsbn(bookId);
////            }
////
////            return mapJsonToBookDetailResponse(rawResponse);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////            return new BookDetailResponse();
////        }
////    }
////
////    // ------------------------
////    // MAP JSON → BookDetailResponse (Work/ISBN)
////    // ------------------------
////    private BookDetailResponse mapJsonToBookDetailResponse(String rawJson) {
////        try {
////            OpenLibraryBookDoc bookDoc;
////            try {
////                bookDoc = mapper.readValue(rawJson, OpenLibraryBookDoc.class);
////            } catch (Exception e) {
////                OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
////                List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
////                if (docs == null || docs.isEmpty()) return new BookDetailResponse();
////                bookDoc = docs.get(0);
////            }
////
////            BookDetailResponse response = new BookDetailResponse();
////            response.setBookId(bookDoc.getWorkKey());
////            response.setTitle(bookDoc.getTitle());
////
////            // ------------------------
////            // Resolve author names
////            // ------------------------
////            List<String> authorNames = new ArrayList<>();
////            if (bookDoc.getAuthors() != null && !bookDoc.getAuthors().isEmpty()) {
////                authorNames = bookDoc.getAuthors();
////            } else if (bookDoc.getAuthorRefs() != null) {
////                for (OpenLibraryBookDoc.AuthorRef ref : bookDoc.getAuthorRefs()) {
////                    if (ref != null && ref.getAuthor() != null) {
////                        String name = fetchAuthorNameByKey(ref.getAuthor().getKey());
////                        if (name != null) authorNames.add(name);
////                    }
////                }
////            }
////            response.setAuthors(authorNames);
////
////            // ------------------------
////            // Description
////            // ------------------------
////            response.setDescription(bookDoc.getDescription());
////            if (response.getDescription() == null || response.getDescription().isEmpty()) {
////                String fallbackDesc = fetchFallbackDescription(bookDoc.getTitle());
////                response.setFallbackDescription(fallbackDesc);
////            }
////
////            response.setCategories(bookDoc.getSubjects());
////
////            // ------------------------
////            // Cover images
////            // ------------------------
////            Integer coverId = bookDoc.getCoverId() != null ? bookDoc.getCoverId()
////                    : (bookDoc.getCoverIds() != null && !bookDoc.getCoverIds().isEmpty() ? bookDoc.getCoverIds().get(0) : null);
////            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
////            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));
////
////            // ------------------------
////            // Multi-platform read links
////            // ------------------------
////            List<BookDetailResponse.ReadLink> readLinks = new ArrayList<>();
////            if (bookDoc.getWorkKey() != null) {
////                String key = bookDoc.getWorkKey().replaceFirst("^/works/", "");
////                readLinks.add(new BookDetailResponse.ReadLink("Open Library",
////                        "https://openlibrary.org/works/" + key));
////            }
////
////            String encodedTitle = URLEncoder.encode(bookDoc.getTitle(), StandardCharsets.UTF_8);
////            readLinks.add(new BookDetailResponse.ReadLink("Project Gutenberg",
////                    "https://www.gutenberg.org/ebooks/search/?query=" + encodedTitle));
////            readLinks.add(new BookDetailResponse.ReadLink("Internet Archive",
////                    "https://archive.org/search.php?query=" + encodedTitle));
////
////            response.setReadLinks(readLinks);
////
////            return response;
////
////        } catch (Exception e) {
////            e.printStackTrace();
////            return new BookDetailResponse();
////        }
////    }
////
////    // ------------------------
////    // MAP JSON → BookSearchResponse
////    // ------------------------
////    private BookSearchResponse mapJsonToBookSearchResponse(String rawJson) {
////        try {
////            OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
////            List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
////            if (docs == null || docs.isEmpty()) return new BookSearchResponse();
////
////            OpenLibraryBookDoc doc = docs.get(0);
////            BookSearchResponse response = new BookSearchResponse();
////            response.setBookId(doc.getWorkKey());
////            response.setTitle(doc.getTitle());
////            response.setAuthors(doc.getAuthors());
////            response.setDescription(doc.getDescription());
////            response.setCategories(doc.getSubjects());
////
////            Integer coverId = doc.getCoverId() != null ? doc.getCoverId()
////                    : (doc.getCoverIds() != null && !doc.getCoverIds().isEmpty() ? doc.getCoverIds().get(0) : null);
////
////            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
////            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));
////
////            return response;
////        } catch (Exception e) {
////            e.printStackTrace();
////            return new BookSearchResponse();
////        }
////    }
////
////    // ------------------------
////    // Fetch author name
////    // ------------------------
////    private String fetchAuthorNameByKey(String authorKey) {
////        try {
////            if (authorKey == null) return null;
////            String url = "https://openlibrary.org" + authorKey + ".json";
////            String raw = restTemplate.getForObject(url, String.class);
////            if (raw == null) return null;
////            JsonNode node = mapper.readTree(raw);
////            return node.path("name").asText(null);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return null;
////        }
////    }
////
////    // ------------------------
////    // Fallback description
////    // ------------------------
////    private String fetchFallbackDescription(String title) {
////        try {
////            String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
////            String url = "https://gutendex.com/books/?search=" + encoded;
////            String raw = restTemplate.getForObject(url, String.class);
////            if (raw != null) {
////                JsonNode root = mapper.readTree(raw);
////                JsonNode results = root.path("results");
////                if (results.isArray() && results.size() > 0) {
////                    JsonNode first = results.get(0);
////                    JsonNode descNode = first.path("description");
////                    if (!descNode.isMissingNode()) {
////                        return descNode.asText();
////                    }
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return null;
////    }
////
////    // ------------------------
////    // Helpers
////    // ------------------------
////    private String buildCoverUrl(Integer coverId, String size) {
////        if (coverId == null) return null;
////        return String.format("https://covers.openlibrary.org/b/id/%d-%s.jpg", coverId, size);
////    }
////
////    private String normalizeBookId(String bookId) {
////        if (bookId == null) return "";
////        bookId = bookId.trim();
////        bookId = bookId.replaceFirst("^/", "");
////        bookId = bookId.replace("works/", "").replace("/works/", "");
////        return bookId;
////    }
////}
////
//
//package com.amigoscode.bookbuddybackend.service;
//
//import com.amigoscode.bookbuddybackend.client.GoogleBooksClient;
//import com.amigoscode.bookbuddybackend.dto.googlebooks.GoogleBookVolume;
//import com.amigoscode.bookbuddybackend.dto.googlebooks.GoogleBooksSearchResponse;
//import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
//import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Service
//public class ExternalBookService {
//
//    private final GoogleBooksClient googleBooksClient;
//    private final ObjectMapper mapper;
//    private final RestTemplate restTemplate;
//
//    public ExternalBookService(GoogleBooksClient googleBooksClient, ObjectMapper mapper) {
//        this.googleBooksClient = googleBooksClient;
//        this.mapper = mapper;
//        this.restTemplate = new RestTemplate();
//    }
//
//    // ------------------------
//    // SEARCH BOOKS
//    // ------------------------
//    public BookSearchResponse searchBooks(String query) {
//        try {
//            String rawResponse = googleBooksClient.searchBooks(query);
//            return mapJsonToBookSearchResponse(rawResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new BookSearchResponse();
//        }
//    }
//
//    // ------------------------
//    // GET BOOK DETAILS
//    // ------------------------
//    public BookDetailResponse getBookDetails(String bookId) {
//        try {
//            bookId = normalizeBookId(bookId);
//            String rawResponse;
//
//            if (isLikelyIsbn(bookId)) {
//                rawResponse = googleBooksClient.getBookByIsbn("isbn:" + bookId);
//            } else {
//                rawResponse = googleBooksClient.getBookById(bookId);
//            }
//
//            return mapJsonToBookDetailResponse(rawResponse);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new BookDetailResponse();
//        }
//    }
//
//    // ------------------------
//    // MAP JSON → BookDetailResponse (Volume or Search)
//    // ------------------------
//    private BookDetailResponse mapJsonToBookDetailResponse(String rawJson) {
//        try {
//            GoogleBookVolume volume;
//            try {
//                volume = mapper.readValue(rawJson, GoogleBookVolume.class);
//            } catch (Exception e) {
//                GoogleBooksSearchResponse searchResponse = mapper.readValue(rawJson, GoogleBooksSearchResponse.class);
//                List<GoogleBookVolume> items = searchResponse.getItems();
//                if (items == null || items.isEmpty()) return new BookDetailResponse();
//                volume = items.get(0);
//            }
//
//            BookDetailResponse response = new BookDetailResponse();
//            response.setBookId(volume.getId());
//            response.setTitle(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getTitle() : null);
//
//            // ------------------------
//            // Authors
//            // ------------------------
//            List<String> authors = volume.getVolumeInfo() != null && volume.getVolumeInfo().getAuthors() != null
//                    ? volume.getVolumeInfo().getAuthors() : Collections.emptyList();
//            response.setAuthors(authors);
//
//            // ------------------------
//            // Description
//            // ------------------------
//            String description = volume.getVolumeInfo() != null ? volume.getVolumeInfo().getDescription() : null;
//            response.setDescription(description);
//            if (description == null || description.isEmpty()) {
//                String fallbackDesc = fetchFallbackDescription(response.getTitle());
//                response.setFallbackDescription(fallbackDesc);
//            }
//
//            // ------------------------
//            // Categories
//            // ------------------------
//            List<String> categories = volume.getVolumeInfo() != null && volume.getVolumeInfo().getCategories() != null
//                    ? volume.getVolumeInfo().getCategories() : Collections.emptyList();
//            response.setCategories(categories);
//
//            // ------------------------
//            // Cover images
//            // ------------------------
//            GoogleBookVolume.ImageLinks imageLinks = volume.getVolumeInfo() != null ? volume.getVolumeInfo().getImageLinks() : null;
//            String coverUrl = null;
//            String smallCoverUrl = null;
//            if (imageLinks != null) {
//                // Prefer largest available
//                if (imageLinks.getExtraLarge() != null) coverUrl = imageLinks.getExtraLarge();
//                else if (imageLinks.getLarge() != null) coverUrl = imageLinks.getLarge();
//                else if (imageLinks.getMedium() != null) coverUrl = imageLinks.getMedium();
//                else if (imageLinks.getThumbnail() != null) coverUrl = imageLinks.getThumbnail();
//                else if (imageLinks.getSmall() != null) coverUrl = imageLinks.getSmall();
//                else if (imageLinks.getSmallThumbnail() != null) coverUrl = imageLinks.getSmallThumbnail();
//
//                // Small cover
//                smallCoverUrl = imageLinks.getSmallThumbnail() != null ? imageLinks.getSmallThumbnail() : coverUrl;
//            }
//            response.setCoverImageUrl(coverUrl);
//            response.setSmallCoverImageUrl(smallCoverUrl);
//
//            // ------------------------
//            // Multi-platform read links
//            // ------------------------
//            List<BookDetailResponse.ReadLink> readLinks = new ArrayList<>();
//            if (volume.getVolumeInfo() != null && volume.getVolumeInfo().getInfoLink() != null) {
//                readLinks.add(new BookDetailResponse.ReadLink("Google Books", volume.getVolumeInfo().getInfoLink()));
//            }
//
//            String encodedTitle = URLEncoder.encode(response.getTitle() != null ? response.getTitle() : "", StandardCharsets.UTF_8);
//            readLinks.add(new BookDetailResponse.ReadLink("Project Gutenberg",
//                    "https://www.gutenberg.org/ebooks/search/?query=" + encodedTitle));
//            readLinks.add(new BookDetailResponse.ReadLink("Internet Archive",
//                    "https://archive.org/search.php?query=" + encodedTitle));
//
//            response.setReadLinks(readLinks);
//
//            return response;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new BookDetailResponse();
//        }
//    }
//
//    // ------------------------
//    // MAP JSON → BookSearchResponse
//    // ------------------------
//    private BookSearchResponse mapJsonToBookSearchResponse(String rawJson) {
//        try {
//            GoogleBooksSearchResponse searchResponse = mapper.readValue(rawJson, GoogleBooksSearchResponse.class);
//            List<GoogleBookVolume> items = searchResponse.getItems();
//            if (items == null || items.isEmpty()) return new BookSearchResponse();
//
//            GoogleBookVolume volume = items.get(0);
//            BookSearchResponse response = new BookSearchResponse();
//            response.setBookId(volume.getId());
//            response.setTitle(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getTitle() : null);
//            response.setAuthors(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getAuthors() : null);
//            response.setDescription(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getDescription() : null);
//            response.setCategories(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getCategories() : null);
//
//            GoogleBookVolume.ImageLinks imageLinks = volume.getVolumeInfo() != null ? volume.getVolumeInfo().getImageLinks() : null;
//            String coverUrl = null;
//            String smallCoverUrl = null;
//            if (imageLinks != null) {
//                // Prefer larger
//                if (imageLinks.getExtraLarge() != null) coverUrl = imageLinks.getExtraLarge();
//                else if (imageLinks.getLarge() != null) coverUrl = imageLinks.getLarge();
//                else if (imageLinks.getMedium() != null) coverUrl = imageLinks.getMedium();
//                else if (imageLinks.getThumbnail() != null) coverUrl = imageLinks.getThumbnail();
//                else if (imageLinks.getSmall() != null) coverUrl = imageLinks.getSmall();
//                else if (imageLinks.getSmallThumbnail() != null) coverUrl = imageLinks.getSmallThumbnail();
//
//                smallCoverUrl = imageLinks.getSmallThumbnail() != null ? imageLinks.getSmallThumbnail() : coverUrl;
//            }
//            response.setCoverImageUrl(coverUrl);
//            response.setSmallCoverImageUrl(smallCoverUrl);
//
//            return response;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new BookSearchResponse();
//        }
//    }
//
//    // ------------------------
//    // Fallback description
//    // ------------------------
//    private String fetchFallbackDescription(String title) {
//        try {
//            if (title == null || title.isEmpty()) return null;
//            String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
//            String url = "https://gutendex.com/books/?search=" + encoded;
//            String raw = restTemplate.getForObject(url, String.class);
//            if (raw != null) {
//                JsonNode root = mapper.readTree(raw);
//                JsonNode results = root.path("results");
//                if (results.isArray() && results.size() > 0) {
//                    JsonNode first = results.get(0);
//                    return first.path("subjects").toString(); // Gutendex doesn't have description, using subjects as fallback
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    // ------------------------
//    // Helpers
//    // ------------------------
//    private String normalizeBookId(String bookId) {
//        if (bookId == null) return "";
//        bookId = bookId.trim();
//        bookId = bookId.replaceFirst("^/", "");
//        // For Google, no specific prefix like /works/
//        return bookId;
//    }
//
//    private boolean isLikelyIsbn(String bookId) {
//        // Simple check: if all digits and length 10 or 13, treat as ISBN
//        return bookId != null && bookId.matches("\\d{10}|\\d{13}");
//    }
//}
//

package com.amigoscode.bookbuddybackend.service;

import com.amigoscode.bookbuddybackend.client.GoogleBooksClient;
import com.amigoscode.bookbuddybackend.dto.googlebooks.GoogleBookVolume;
import com.amigoscode.bookbuddybackend.dto.googlebooks.GoogleBooksSearchResponse;
import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ExternalBookService {

    private final GoogleBooksClient googleBooksClient;
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;

    public ExternalBookService(GoogleBooksClient googleBooksClient, ObjectMapper mapper) {
        this.googleBooksClient = googleBooksClient;
        this.mapper = mapper;
        this.restTemplate = new RestTemplate();
    }

    // ------------------------
    // SEARCH BOOKS
    // ------------------------
    public BookSearchResponse searchBooks(String query) {
        try {
            String rawResponse = googleBooksClient.searchBooks(query);
            return mapJsonToBookSearchResponse(rawResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new BookSearchResponse();
        }
    }

    // ------------------------
    // GET BOOK DETAILS
    // ------------------------
    public BookDetailResponse getBookDetails(String bookId) {
        try {
            bookId = normalizeBookId(bookId);
            String rawResponse;

            if (isLikelyIsbn(bookId)) {
                rawResponse = googleBooksClient.getBookByIsbn("isbn:" + bookId);
            } else {
                rawResponse = googleBooksClient.getBookById(bookId);
            }

            return mapJsonToBookDetailResponse(rawResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return new BookDetailResponse();
        }
    }

    // ------------------------
    // MAP JSON → BookDetailResponse (Volume or Search)
    // ------------------------
    private BookDetailResponse mapJsonToBookDetailResponse(String rawJson) {
        try {
            GoogleBookVolume volume;
            try {
                volume = mapper.readValue(rawJson, GoogleBookVolume.class);
            } catch (Exception e) {
                GoogleBooksSearchResponse searchResponse = mapper.readValue(rawJson, GoogleBooksSearchResponse.class);
                List<GoogleBookVolume> items = searchResponse.getItems();
                if (items == null || items.isEmpty()) return new BookDetailResponse();
                volume = items.get(0);
            }

            BookDetailResponse response = new BookDetailResponse();
            response.setBookId(volume.getId());
            response.setTitle(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getTitle() : null);
            response.setSubtitle(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getSubtitle() : null);

            // ------------------------
            // Authors
            // ------------------------
            List<String> authors = volume.getVolumeInfo() != null && volume.getVolumeInfo().getAuthors() != null
                    ? volume.getVolumeInfo().getAuthors() : Collections.emptyList();
            response.setAuthors(authors);

            // ------------------------
            // Description
            // ------------------------
            String description = volume.getVolumeInfo() != null ? volume.getVolumeInfo().getDescription() : null;
            response.setDescription(description);
            if (description == null || description.isEmpty()) {
                String fallbackDesc = fetchFallbackDescription(response.getTitle());
                response.setFallbackDescription(fallbackDesc);
            }

            // ------------------------
            // Categories
            // ------------------------
            List<String> categories = volume.getVolumeInfo() != null && volume.getVolumeInfo().getCategories() != null
                    ? volume.getVolumeInfo().getCategories() : Collections.emptyList();
            response.setCategories(categories);

            response.setPageCount(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getPageCount() : null);
            response.setPublishedDate(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getPublishedDate() : null);
            response.setAverageRating(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getAverageRating() : null);
            response.setRatingsCount(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getRatingsCount() : null);

            // ------------------------
            // Cover images
            // ------------------------
            GoogleBookVolume.ImageLinks imageLinks = volume.getVolumeInfo() != null ? volume.getVolumeInfo().getImageLinks() : null;
            String coverUrl = null;
            String smallCoverUrl = null;
            if (imageLinks != null) {
                // Prefer largest available
                if (imageLinks.getExtraLarge() != null) coverUrl = imageLinks.getExtraLarge();
                else if (imageLinks.getLarge() != null) coverUrl = imageLinks.getLarge();
                else if (imageLinks.getMedium() != null) coverUrl = imageLinks.getMedium();
                else if (imageLinks.getThumbnail() != null) coverUrl = imageLinks.getThumbnail();
                else if (imageLinks.getSmall() != null) coverUrl = imageLinks.getSmall();
                else if (imageLinks.getSmallThumbnail() != null) coverUrl = imageLinks.getSmallThumbnail();

                // Small cover
                smallCoverUrl = imageLinks.getSmallThumbnail() != null ? imageLinks.getSmallThumbnail() : coverUrl;
            }
            response.setCoverImageUrl(coverUrl);
            response.setSmallCoverImageUrl(smallCoverUrl);

            // ------------------------
            // Multi-platform read links
            // ------------------------
            List<BookDetailResponse.ReadLink> readLinks = new ArrayList<>();
            // Use web version of Google Books
            if (volume.getId() != null) {
                readLinks.add(new BookDetailResponse.ReadLink("Google Books (Web)", "https://books.google.com/books?id=" + volume.getId()));
            }

            String encodedTitle = URLEncoder.encode(response.getTitle() != null ? response.getTitle() : "", StandardCharsets.UTF_8);

            // Existing free links
            readLinks.add(new BookDetailResponse.ReadLink("Project Gutenberg",
                    "https://www.gutenberg.org/ebooks/search/?query=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("Internet Archive",
                    "https://archive.org/search.php?query=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("Open Library",
                    "https://openlibrary.org/search?q=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("ManyBooks",
                    "https://manybooks.net/search-books?search=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("Standard Ebooks",
                    "https://standardebooks.org/ebooks?query=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("The Online Books Page",
                    "https://onlinebooks.library.upenn.edu/search.html?title=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("Classic Reader",
                    "https://www.classicreader.com/search.php?search=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("LibriVox (Audiobooks)",
                    "https://librivox.org/search?title=" + encodedTitle + "&author=&reader=&keywords=&genre=&status=complete&sort_order=alpha&search_form=advanced"));
            readLinks.add(new BookDetailResponse.ReadLink("Wikisource",
                    "https://en.wikisource.org/w/index.php?search=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("Smashwords",
                    "https://www.smashwords.com/books/search?query=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("OverDrive (Libraries)",
                    "https://www.overdrive.com/search?q=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("Hoopla (Libraries)",
                    "https://www.hoopladigital.com/search?q=" + encodedTitle));

            // Additional paid/commercial links
            readLinks.add(new BookDetailResponse.ReadLink("Amazon",
                    "https://www.amazon.com/s?k=" + encodedTitle + "&i=digital-text"));
            readLinks.add(new BookDetailResponse.ReadLink("Apple Books",
                    "https://books.apple.com/us/search?term=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("Barnes & Noble",
                    "https://www.barnesandnoble.com/s/" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("eBooks.com",
                    "https://www.ebooks.com/en-us/searchapp/searchresults.net?term=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("Bookmate",
                    "https://bookmate.com/search?q=" + encodedTitle));

            response.setReadLinks(readLinks);

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return new BookDetailResponse();
        }
    }

    // ------------------------
    // MAP JSON → BookSearchResponse
    // ------------------------
    private BookSearchResponse mapJsonToBookSearchResponse(String rawJson) {
        try {
            GoogleBooksSearchResponse searchResponse = mapper.readValue(rawJson, GoogleBooksSearchResponse.class);
            List<GoogleBookVolume> items = searchResponse.getItems();
            if (items == null || items.isEmpty()) return new BookSearchResponse();

            GoogleBookVolume volume = items.get(0);
            BookSearchResponse response = new BookSearchResponse();
            response.setBookId(volume.getId());
            response.setTitle(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getTitle() : null);
            response.setAuthors(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getAuthors() : null);
            response.setDescription(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getDescription() : null);
            response.setCategories(volume.getVolumeInfo() != null ? volume.getVolumeInfo().getCategories() : null);

            GoogleBookVolume.ImageLinks imageLinks = volume.getVolumeInfo() != null ? volume.getVolumeInfo().getImageLinks() : null;
            String coverUrl = null;
            String smallCoverUrl = null;
            if (imageLinks != null) {
                // Prefer larger
                if (imageLinks.getExtraLarge() != null) coverUrl = imageLinks.getExtraLarge();
                else if (imageLinks.getLarge() != null) coverUrl = imageLinks.getLarge();
                else if (imageLinks.getMedium() != null) coverUrl = imageLinks.getMedium();
                else if (imageLinks.getThumbnail() != null) coverUrl = imageLinks.getThumbnail();
                else if (imageLinks.getSmall() != null) coverUrl = imageLinks.getSmall();
                else if (imageLinks.getSmallThumbnail() != null) coverUrl = imageLinks.getSmallThumbnail();

                smallCoverUrl = imageLinks.getSmallThumbnail() != null ? imageLinks.getSmallThumbnail() : coverUrl;
            }
            response.setCoverImageUrl(coverUrl);
            response.setSmallCoverImageUrl(smallCoverUrl);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new BookSearchResponse();
        }
    }

    // ------------------------
    // Fallback description
    // ------------------------
    private String fetchFallbackDescription(String title) {
        try {
            if (title == null || title.isEmpty()) return null;
            String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
            String url = "https://gutendex.com/books/?search=" + encoded;
            String raw = restTemplate.getForObject(url, String.class);
            if (raw != null) {
                JsonNode root = mapper.readTree(raw);
                JsonNode results = root.path("results");
                if (results.isArray() && results.size() > 0) {
                    JsonNode first = results.get(0);
                    return first.path("subjects").toString(); // Gutendex doesn't have description, using subjects as fallback
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ------------------------
    // Helpers
    // ------------------------
    private String normalizeBookId(String bookId) {
        if (bookId == null) return "";
        bookId = bookId.trim();
        bookId = bookId.replaceFirst("^/", "");
        // For Google, no specific prefix like /works/
        return bookId;
    }

    private boolean isLikelyIsbn(String bookId) {
        // Simple check: if all digits and length 10 or 13, treat as ISBN
        return bookId != null && bookId.matches("\\d{10}|\\d{13}");
    }
}