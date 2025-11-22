////////////
////////////
////////////
//////////////package com.amigoscode.bookbuddybackend.service;
//////////////
//////////////import com.amigoscode.bookbuddybackend.client.OpenLibraryClient;
//////////////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibraryBookDoc;
//////////////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibrarySearchResponse;
//////////////import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
//////////////import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
//////////////import com.fasterxml.jackson.databind.ObjectMapper;
//////////////import org.springframework.stereotype.Service;
//////////////
//////////////import java.util.List;
//////////////
//////////////@Service
//////////////public class ExternalBookService {
//////////////
//////////////    private final OpenLibraryClient openLibraryClient;
//////////////    private final ObjectMapper mapper;
//////////////
//////////////    public ExternalBookService(OpenLibraryClient openLibraryClient, ObjectMapper mapper) {
//////////////        this.openLibraryClient = openLibraryClient;
//////////////        this.mapper = mapper;
//////////////    }
//////////////
//////////////    // ------------------------
//////////////    // SEARCH BOOKS
//////////////    // ------------------------
//////////////    public BookSearchResponse searchBooks(String query) {
//////////////        try {
//////////////            String rawResponse = openLibraryClient.searchBooks(query);
//////////////            return mapSearchJsonToBookResponse(rawResponse);
//////////////        } catch (Exception e) {
//////////////            e.printStackTrace();
//////////////            return new BookSearchResponse();
//////////////        }
//////////////    }
//////////////
//////////////    // ------------------------
//////////////    // GET BOOK DETAILS
//////////////    // ------------------------
//////////////    public BookDetailResponse getBookDetails(String bookId) {
//////////////        try {
//////////////            bookId = normalizeBookId(bookId);
//////////////            String rawResponse;
//////////////
//////////////            if (bookId.toUpperCase().startsWith("OL") && bookId.toUpperCase().endsWith("W")) {
//////////////                // Work endpoint
//////////////                rawResponse = openLibraryClient.getBookByWorkId(bookId);
//////////////                return mapWorkJsonToDetailResponse(rawResponse);
//////////////            } else {
//////////////                // ISBN endpoint
//////////////                rawResponse = openLibraryClient.getBookByIsbn(bookId);
//////////////                return mapSearchJsonToDetailResponse(rawResponse);
//////////////            }
//////////////
//////////////        } catch (Exception e) {
//////////////            e.printStackTrace();
//////////////            return new BookDetailResponse();
//////////////        }
//////////////    }
//////////////
//////////////    // ------------------------
//////////////    // MAP WORK JSON → BookDetailResponse
//////////////    // ------------------------
//////////////    private BookDetailResponse mapWorkJsonToDetailResponse(String rawJson) {
//////////////        try {
//////////////            OpenLibraryBookDoc work = mapper.readValue(rawJson, OpenLibraryBookDoc.class);
//////////////
//////////////            BookDetailResponse response = new BookDetailResponse();
//////////////            response.setBookId(work.getWorkKey());
//////////////            response.setTitle(work.getTitle());
//////////////            response.setAuthors(work.getAuthors());
//////////////            response.setDescription(work.getDescription());
//////////////            response.setCategories(work.getSubjects());
//////////////            response.setCoverImageUrl(buildCoverUrl(work.getCoverId(), "L"));
//////////////            response.setSmallCoverImageUrl(buildCoverUrl(work.getCoverId(), "S"));
//////////////
//////////////            return response;
//////////////        } catch (Exception e) {
//////////////            e.printStackTrace();
//////////////            return new BookDetailResponse();
//////////////        }
//////////////    }
//////////////
//////////////    // ------------------------
//////////////    // MAP SEARCH JSON → BookSearchResponse
//////////////    // ------------------------
//////////////    private BookSearchResponse mapSearchJsonToBookResponse(String rawJson) {
//////////////        try {
//////////////            OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
//////////////            List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
//////////////
//////////////            if (docs == null || docs.isEmpty()) {
//////////////                return new BookSearchResponse();
//////////////            }
//////////////
//////////////            OpenLibraryBookDoc doc = docs.get(0);
//////////////
//////////////            BookSearchResponse response = new BookSearchResponse();
//////////////            response.setBookId(doc.getWorkKey());
//////////////            response.setTitle(doc.getTitle());
//////////////            response.setAuthors(doc.getAuthors());
//////////////            response.setDescription(doc.getDescription());
//////////////            response.setCategories(doc.getSubjects());
//////////////            response.setCoverImageUrl(buildCoverUrl(doc.getCoverId(), "L"));
//////////////            response.setSmallCoverImageUrl(buildCoverUrl(doc.getCoverId(), "S"));
//////////////
//////////////            return response;
//////////////        } catch (Exception e) {
//////////////            e.printStackTrace();
//////////////            return new BookSearchResponse();
//////////////        }
//////////////    }
//////////////
//////////////    // ------------------------
//////////////    // MAP SEARCH JSON → BookDetailResponse (for ISBN details)
//////////////    // ------------------------
//////////////    private BookDetailResponse mapSearchJsonToDetailResponse(String rawJson) {
//////////////        try {
//////////////            BookSearchResponse searchResult = mapSearchJsonToBookResponse(rawJson);
//////////////
//////////////            BookDetailResponse detailResponse = new BookDetailResponse();
//////////////            detailResponse.setBookId(searchResult.getBookId());
//////////////            detailResponse.setTitle(searchResult.getTitle());
//////////////            detailResponse.setAuthors(searchResult.getAuthors());
//////////////            detailResponse.setDescription(searchResult.getDescription());
//////////////            detailResponse.setCategories(searchResult.getCategories());
//////////////            detailResponse.setCoverImageUrl(searchResult.getCoverImageUrl());
//////////////            detailResponse.setSmallCoverImageUrl(searchResult.getSmallCoverImageUrl());
//////////////
//////////////            return detailResponse;
//////////////        } catch (Exception e) {
//////////////            e.printStackTrace();
//////////////            return new BookDetailResponse();
//////////////        }
//////////////    }
//////////////
//////////////    // ------------------------
//////////////    // HELPERS
//////////////    // ------------------------
//////////////    private String buildCoverUrl(Integer coverId, String size) {
//////////////        if (coverId == null) return null;
//////////////        return String.format("https://covers.openlibrary.org/b/id/%d-%s.jpg", coverId, size);
//////////////    }
//////////////
//////////////    private String normalizeBookId(String bookId) {
//////////////        if (bookId == null) return null;
//////////////        bookId = bookId.trim();
//////////////        bookId = bookId.replaceFirst("^/", "");
//////////////        bookId = bookId.replace("works/", "").replace("/works/", "");
//////////////        return bookId;
//////////////    }
//////////////}
////////////
////////////package com.amigoscode.bookbuddybackend.service;
////////////
////////////import com.amigoscode.bookbuddybackend.client.OpenLibraryClient;
////////////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibraryBookDoc;
////////////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibrarySearchResponse;
////////////import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
////////////import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
////////////import com.fasterxml.jackson.databind.ObjectMapper;
////////////import org.springframework.stereotype.Service;
////////////
////////////import java.util.List;
////////////
////////////@Service
////////////public class ExternalBookService {
////////////
////////////    private final OpenLibraryClient openLibraryClient;
////////////    private final ObjectMapper mapper;
////////////
////////////    public ExternalBookService(OpenLibraryClient openLibraryClient, ObjectMapper mapper) {
////////////        this.openLibraryClient = openLibraryClient;
////////////        this.mapper = mapper;
////////////    }
////////////
////////////    // ------------------------
////////////    // SEARCH BOOKS
////////////    // ------------------------
////////////    public BookSearchResponse searchBooks(String query) {
////////////        try {
////////////            String rawResponse = openLibraryClient.searchBooks(query);
////////////            return mapSearchJsonToBookResponse(rawResponse);
////////////        } catch (Exception e) {
////////////            e.printStackTrace();
////////////            return new BookSearchResponse();
////////////        }
////////////    }
////////////
////////////    // ------------------------
////////////    // GET BOOK DETAILS
////////////    // ------------------------
////////////    public BookDetailResponse getBookDetails(String bookId) {
////////////        try {
////////////            bookId = normalizeBookId(bookId);
////////////            String rawResponse;
////////////
////////////            if (bookId.toUpperCase().startsWith("OL") && bookId.toUpperCase().endsWith("W")) {
////////////                rawResponse = openLibraryClient.getBookByWorkId(bookId);
////////////                return mapWorkJsonToBookDetailResponse(rawResponse);
////////////            } else {
////////////                rawResponse = openLibraryClient.getBookByIsbn(bookId);
////////////                return mapWorkJsonToBookDetailResponse(rawResponse); // ISBN returns single book as well
////////////            }
////////////
////////////        } catch (Exception e) {
////////////            e.printStackTrace();
////////////            return new BookDetailResponse();
////////////        }
////////////    }
////////////
////////////    // ------------------------
////////////    // MAP WORK/ISBN JSON → BookDetailResponse
////////////    // ------------------------
////////////    private BookDetailResponse mapWorkJsonToBookDetailResponse(String rawJson) {
////////////        try {
////////////            OpenLibraryBookDoc work = mapper.readValue(rawJson, OpenLibraryBookDoc.class);
////////////
////////////            BookDetailResponse response = new BookDetailResponse();
////////////            response.setBookId(work.getWorkKey());
////////////            response.setTitle(work.getTitle());
////////////            response.setAuthors(work.getAuthors());
////////////            response.setDescription(work.getDescription());
////////////            response.setCategories(work.getSubjects());
////////////            response.setCoverImageUrl(buildCoverUrl(work.getCoverId(), "L"));
////////////            response.setSmallCoverImageUrl(buildCoverUrl(work.getCoverId(), "S"));
////////////
////////////            return response;
////////////        } catch (Exception e) {
////////////            e.printStackTrace();
////////////            return new BookDetailResponse();
////////////        }
////////////    }
////////////
////////////    // ------------------------
////////////    // MAP SEARCH JSON → BookSearchResponse
////////////    // ------------------------
////////////    private BookSearchResponse mapSearchJsonToBookResponse(String rawJson) {
////////////        try {
////////////            OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
////////////            List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
////////////
////////////            if (docs == null || docs.isEmpty()) {
////////////                return new BookSearchResponse();
////////////            }
////////////
////////////            OpenLibraryBookDoc doc = docs.get(0);
////////////
////////////            BookSearchResponse response = new BookSearchResponse();
////////////            response.setBookId(doc.getWorkKey());
////////////            response.setTitle(doc.getTitle());
////////////            response.setAuthors(doc.getAuthors());
////////////            response.setDescription(doc.getDescription());
////////////            response.setCategories(doc.getSubjects());
////////////            response.setCoverImageUrl(buildCoverUrl(doc.getCoverId(), "L"));
////////////            response.setSmallCoverImageUrl(buildCoverUrl(doc.getCoverId(), "S"));
////////////
////////////            return response;
////////////        } catch (Exception e) {
////////////            e.printStackTrace();
////////////            return new BookSearchResponse();
////////////        }
////////////    }
////////////
////////////    // ------------------------
////////////    // HELPERS
////////////    // ------------------------
////////////    private String buildCoverUrl(Integer coverId, String size) {
////////////        if (coverId == null) return null;
////////////        return String.format("https://covers.openlibrary.org/b/id/%d-%s.jpg", coverId, size);
////////////    }
////////////
////////////    private String normalizeBookId(String bookId) {
////////////        return bookId != null ? bookId.trim() : "";
////////////    }
////////////}
////////////
//////////
//////////
//////////package com.amigoscode.bookbuddybackend.service;
//////////
//////////import com.amigoscode.bookbuddybackend.client.OpenLibraryClient;
//////////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibraryBookDoc;
//////////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibrarySearchResponse;
//////////import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
//////////import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
//////////import com.fasterxml.jackson.databind.ObjectMapper;
//////////import org.springframework.stereotype.Service;
//////////
//////////import java.util.List;
//////////
//////////@Service
//////////public class ExternalBookService {
//////////
//////////    private final OpenLibraryClient openLibraryClient;
//////////    private final ObjectMapper mapper;
//////////
//////////    public ExternalBookService(OpenLibraryClient openLibraryClient, ObjectMapper mapper) {
//////////        this.openLibraryClient = openLibraryClient;
//////////        this.mapper = mapper;
//////////    }
//////////
//////////    // ------------------------
//////////    // SEARCH BOOKS
//////////    // ------------------------
//////////    public BookSearchResponse searchBooks(String query) {
//////////        try {
//////////            String rawResponse = openLibraryClient.searchBooks(query);
//////////            return mapJsonToBookSearchResponse(rawResponse);
//////////        } catch (Exception e) {
//////////            e.printStackTrace();
//////////            return new BookSearchResponse();
//////////        }
//////////    }
//////////
//////////    // ------------------------
//////////    // GET BOOK DETAILS
//////////    // ------------------------
//////////    public BookDetailResponse getBookDetails(String bookId) {
//////////        try {
//////////            bookId = normalizeBookId(bookId);
//////////            String rawResponse;
//////////
//////////            if (bookId.toUpperCase().startsWith("OL") && bookId.toUpperCase().endsWith("W")) {
//////////                // Work endpoint
//////////                rawResponse = openLibraryClient.getBookByWorkId(bookId);
//////////            } else {
//////////                // ISBN endpoint
//////////                rawResponse = openLibraryClient.getBookByIsbn(bookId);
//////////            }
//////////
//////////            return mapJsonToBookDetailResponse(rawResponse);
//////////
//////////        } catch (Exception e) {
//////////            e.printStackTrace();
//////////            return new BookDetailResponse();
//////////        }
//////////    }
//////////
//////////    // ------------------------
//////////    // MAP JSON → BookDetailResponse
//////////    // Handles both work and search JSON
//////////    // ------------------------
//////////    private BookDetailResponse mapJsonToBookDetailResponse(String rawJson) {
//////////        try {
//////////            OpenLibraryBookDoc bookDoc;
//////////            // Attempt to parse as single work book
//////////            try {
//////////                bookDoc = mapper.readValue(rawJson, OpenLibraryBookDoc.class);
//////////            } catch (Exception e) {
//////////                // Fallback: maybe search result JSON, take first doc
//////////                OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
//////////                List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
//////////                if (docs == null || docs.isEmpty()) return new BookDetailResponse();
//////////                bookDoc = docs.get(0);
//////////            }
//////////
//////////            BookDetailResponse response = new BookDetailResponse();
//////////            response.setBookId(bookDoc.getWorkKey());
//////////            response.setTitle(bookDoc.getTitle());
//////////            response.setAuthors(bookDoc.getAuthors());
//////////            response.setDescription(bookDoc.getDescription());
//////////            response.setCategories(bookDoc.getSubjects());
//////////
//////////            // Unified cover ID
//////////            Integer coverId = bookDoc.getCoverId() != null ? bookDoc.getCoverId()
//////////                    : (bookDoc.getCoverIds() != null && !bookDoc.getCoverIds().isEmpty() ? bookDoc.getCoverIds().get(0) : null);
//////////
//////////            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
//////////            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));
//////////
//////////            return response;
//////////        } catch (Exception e) {
//////////            e.printStackTrace();
//////////            return new BookDetailResponse();
//////////        }
//////////    }
//////////
//////////    // ------------------------
//////////    // MAP JSON → BookSearchResponse
//////////    // ------------------------
//////////    private BookSearchResponse mapJsonToBookSearchResponse(String rawJson) {
//////////        try {
//////////            OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
//////////            List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
//////////
//////////            if (docs == null || docs.isEmpty()) {
//////////                return new BookSearchResponse();
//////////            }
//////////
//////////            OpenLibraryBookDoc doc = docs.get(0);
//////////
//////////            BookSearchResponse response = new BookSearchResponse();
//////////            response.setBookId(doc.getWorkKey());
//////////            response.setTitle(doc.getTitle());
//////////            response.setAuthors(doc.getAuthors());
//////////            response.setDescription(doc.getDescription());
//////////            response.setCategories(doc.getSubjects());
//////////
//////////            Integer coverId = doc.getCoverId() != null ? doc.getCoverId()
//////////                    : (doc.getCoverIds() != null && !doc.getCoverIds().isEmpty() ? doc.getCoverIds().get(0) : null);
//////////
//////////            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
//////////            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));
//////////
//////////            return response;
//////////        } catch (Exception e) {
//////////            e.printStackTrace();
//////////            return new BookSearchResponse();
//////////        }
//////////    }
//////////
//////////    // ------------------------
//////////    // HELPERS
//////////    // ------------------------
//////////    private String buildCoverUrl(Integer coverId, String size) {
//////////        if (coverId == null) return null;
//////////        return String.format("https://covers.openlibrary.org/b/id/%d-%s.jpg", coverId, size);
//////////    }
//////////
//////////    private String normalizeBookId(String bookId) {
//////////        if (bookId == null) return "";
//////////        bookId = bookId.trim();
//////////        bookId = bookId.replaceFirst("^/", "");
//////////        bookId = bookId.replace("works/", "").replace("/works/", "");
//////////        return bookId;
//////////    }
//////////}
////////
////////
////////package com.amigoscode.bookbuddybackend.service;
////////
////////import com.amigoscode.bookbuddybackend.client.OpenLibraryClient;
////////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibraryBookDoc;
////////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibrarySearchResponse;
////////import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
////////import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
////////import com.fasterxml.jackson.databind.ObjectMapper;
////////import org.springframework.stereotype.Service;
////////
////////import java.util.List;
////////
////////@Service
////////public class ExternalBookService {
////////
////////    private final OpenLibraryClient openLibraryClient;
////////    private final ObjectMapper mapper;
////////
////////    public ExternalBookService(OpenLibraryClient openLibraryClient, ObjectMapper mapper) {
////////        this.openLibraryClient = openLibraryClient;
////////        this.mapper = mapper;
////////    }
////////
////////    // ------------------------
////////    // SEARCH BOOKS
////////    // ------------------------
////////    public BookSearchResponse searchBooks(String query) {
////////        try {
////////            String rawResponse = openLibraryClient.searchBooks(query);
////////            return mapJsonToBookSearchResponse(rawResponse);
////////        } catch (Exception e) {
////////            e.printStackTrace();
////////            return new BookSearchResponse();
////////        }
////////    }
////////
////////    // ------------------------
////////    // GET BOOK DETAILS
////////    // ------------------------
////////    public BookDetailResponse getBookDetails(String bookId) {
////////        try {
////////            bookId = normalizeBookId(bookId);
////////            String rawResponse;
////////
////////            if (bookId.toUpperCase().startsWith("OL") && bookId.toUpperCase().endsWith("W")) {
////////                // Work endpoint
////////                rawResponse = openLibraryClient.getBookByWorkId(bookId);
////////            } else {
////////                // ISBN endpoint
////////                rawResponse = openLibraryClient.getBookByIsbn(bookId);
////////            }
////////
////////            return mapJsonToBookDetailResponse(rawResponse);
////////
////////        } catch (Exception e) {
////////            e.printStackTrace();
////////            return new BookDetailResponse();
////////        }
////////    }
////////
////////    // ------------------------
////////    // MAP JSON → BookDetailResponse
////////    // Handles both work and ISBN JSON
////////    // ------------------------
////////    private BookDetailResponse mapJsonToBookDetailResponse(String rawJson) {
////////        try {
////////            OpenLibraryBookDoc bookDoc;
////////
////////            // Attempt to parse as single work book
////////            try {
////////                bookDoc = mapper.readValue(rawJson, OpenLibraryBookDoc.class);
////////            } catch (Exception e) {
////////                // Fallback: maybe search result JSON, take first doc
////////                OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
////////                List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
////////                if (docs == null || docs.isEmpty()) return new BookDetailResponse();
////////                bookDoc = docs.get(0);
////////            }
////////
////////            BookDetailResponse response = new BookDetailResponse();
////////            response.setBookId(bookDoc.getWorkKey());
////////            response.setTitle(bookDoc.getTitle());
////////
////////            // ------------------------
////////            // Set authors
////////            // ------------------------
////////            if (bookDoc.getAuthors() != null) {
////////                response.setAuthors(bookDoc.getAuthors()); // search JSON
////////            } else {
////////                response.setAuthors(bookDoc.getAuthorsFromRefs()); // work JSON
////////            }
////////
////////            response.setDescription(bookDoc.getDescription());
////////            response.setCategories(bookDoc.getSubjects());
////////
////////            // Unified cover ID
////////            Integer coverId = bookDoc.getCoverId() != null ? bookDoc.getCoverId()
////////                    : (bookDoc.getCoverIds() != null && !bookDoc.getCoverIds().isEmpty() ? bookDoc.getCoverIds().get(0) : null);
////////
////////            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
////////            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));
////////
////////            return response;
////////
////////        } catch (Exception e) {
////////            e.printStackTrace();
////////            return new BookDetailResponse();
////////        }
////////    }
////////
////////    // ------------------------
////////    // MAP JSON → BookSearchResponse
////////    // ------------------------
////////    private BookSearchResponse mapJsonToBookSearchResponse(String rawJson) {
////////        try {
////////            OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
////////            List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
////////
////////            if (docs == null || docs.isEmpty()) {
////////                return new BookSearchResponse();
////////            }
////////
////////            OpenLibraryBookDoc doc = docs.get(0);
////////
////////            BookSearchResponse response = new BookSearchResponse();
////////            response.setBookId(doc.getWorkKey());
////////            response.setTitle(doc.getTitle());
////////            response.setAuthors(doc.getAuthors());
////////            response.setDescription(doc.getDescription());
////////            response.setCategories(doc.getSubjects());
////////
////////            Integer coverId = doc.getCoverId() != null ? doc.getCoverId()
////////                    : (doc.getCoverIds() != null && !doc.getCoverIds().isEmpty() ? doc.getCoverIds().get(0) : null);
////////
////////            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
////////            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));
////////
////////            return response;
////////        } catch (Exception e) {
////////            e.printStackTrace();
////////            return new BookSearchResponse();
////////        }
////////    }
////////
////////    // ------------------------
////////    // HELPERS
////////    // ------------------------
////////    private String buildCoverUrl(Integer coverId, String size) {
////////        if (coverId == null) return null;
////////        return String.format("https://covers.openlibrary.org/b/id/%d-%s.jpg", coverId, size);
////////    }
////////
////////    private String normalizeBookId(String bookId) {
////////        if (bookId == null) return "";
////////        bookId = bookId.trim();
////////        bookId = bookId.replaceFirst("^/", "");
////////        bookId = bookId.replace("works/", "").replace("/works/", "");
////////        return bookId;
////////    }
////////}
//////
//////package com.amigoscode.bookbuddybackend.service;
//////
//////import com.amigoscode.bookbuddybackend.client.OpenLibraryClient;
//////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibraryBookDoc;
//////import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibrarySearchResponse;
//////import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
//////import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
//////import com.fasterxml.jackson.databind.ObjectMapper;
//////import com.fasterxml.jackson.databind.JsonNode;
//////import org.springframework.stereotype.Service;
//////import org.springframework.web.client.RestTemplate;
//////
//////import java.util.ArrayList;
//////import java.util.List;
//////
//////@Service
//////public class ExternalBookService {
//////
//////    private final OpenLibraryClient openLibraryClient;
//////    private final ObjectMapper mapper;
//////    private final RestTemplate restTemplate;
//////
//////    public ExternalBookService(OpenLibraryClient openLibraryClient, ObjectMapper mapper) {
//////        this.openLibraryClient = openLibraryClient;
//////        this.mapper = mapper;
//////        this.restTemplate = new RestTemplate();
//////    }
//////
//////    // ------------------------
//////    // SEARCH BOOKS
//////    // ------------------------
//////    public BookSearchResponse searchBooks(String query) {
//////        try {
//////            String rawResponse = openLibraryClient.searchBooks(query);
//////            return mapJsonToBookSearchResponse(rawResponse);
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////            return new BookSearchResponse();
//////        }
//////    }
//////
//////    // ------------------------
//////    // GET BOOK DETAILS
//////    // ------------------------
//////    public BookDetailResponse getBookDetails(String bookId) {
//////        try {
//////            bookId = normalizeBookId(bookId);
//////            String rawResponse;
//////
//////            if (bookId.toUpperCase().startsWith("OL") && bookId.toUpperCase().endsWith("W")) {
//////                rawResponse = openLibraryClient.getBookByWorkId(bookId);
//////            } else {
//////                rawResponse = openLibraryClient.getBookByIsbn(bookId);
//////            }
//////
//////            return mapJsonToBookDetailResponse(rawResponse);
//////
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////            return new BookDetailResponse();
//////        }
//////    }
//////
//////    // ------------------------
//////    // MAP JSON → BookDetailResponse (Work/ISBN)
//////    // ------------------------
//////    private BookDetailResponse mapJsonToBookDetailResponse(String rawJson) {
//////        try {
//////            OpenLibraryBookDoc bookDoc;
//////            try {
//////                bookDoc = mapper.readValue(rawJson, OpenLibraryBookDoc.class);
//////            } catch (Exception e) {
//////                OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
//////                List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
//////                if (docs == null || docs.isEmpty()) return new BookDetailResponse();
//////                bookDoc = docs.get(0);
//////            }
//////
//////            BookDetailResponse response = new BookDetailResponse();
//////            response.setBookId(bookDoc.getWorkKey());
//////            response.setTitle(bookDoc.getTitle());
//////
//////            // ------------------------
//////            // Resolve author names from refs if needed
//////            // ------------------------
//////            List<String> authorNames = new ArrayList<>();
//////            if (bookDoc.getAuthors() != null && !bookDoc.getAuthors().isEmpty()) {
//////                authorNames = bookDoc.getAuthors(); // already names
//////            } else if (bookDoc.getAuthorRefs() != null) {
//////                for (OpenLibraryBookDoc.AuthorRef ref : bookDoc.getAuthorRefs()) {
//////                    if (ref != null && ref.getAuthor() != null) {
//////                        String name = fetchAuthorNameByKey(ref.getAuthor().getKey());
//////                        if (name != null) authorNames.add(name);
//////                    }
//////                }
//////            }
//////            response.setAuthors(authorNames);
//////
//////            response.setDescription(bookDoc.getDescription());
//////            response.setCategories(bookDoc.getSubjects());
//////
//////            // Unified cover ID
//////            Integer coverId = bookDoc.getCoverId() != null ? bookDoc.getCoverId()
//////                    : (bookDoc.getCoverIds() != null && !bookDoc.getCoverIds().isEmpty() ? bookDoc.getCoverIds().get(0) : null);
//////
//////            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
//////            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));
//////
//////            return response;
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////            return new BookDetailResponse();
//////        }
//////    }
//////
//////    // ------------------------
//////    // MAP JSON → BookSearchResponse
//////    // ------------------------
//////    private BookSearchResponse mapJsonToBookSearchResponse(String rawJson) {
//////        try {
//////            OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
//////            List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
//////            if (docs == null || docs.isEmpty()) return new BookSearchResponse();
//////
//////            OpenLibraryBookDoc doc = docs.get(0);
//////            BookSearchResponse response = new BookSearchResponse();
//////            response.setBookId(doc.getWorkKey());
//////            response.setTitle(doc.getTitle());
//////            response.setAuthors(doc.getAuthors());
//////            response.setDescription(doc.getDescription());
//////            response.setCategories(doc.getSubjects());
//////
//////            Integer coverId = doc.getCoverId() != null ? doc.getCoverId()
//////                    : (doc.getCoverIds() != null && !doc.getCoverIds().isEmpty() ? doc.getCoverIds().get(0) : null);
//////
//////            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
//////            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));
//////
//////            return response;
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////            return new BookSearchResponse();
//////        }
//////    }
//////
//////    // ------------------------
//////    // Fetch author name directly via RestTemplate
//////    // ------------------------
//////    private String fetchAuthorNameByKey(String authorKey) {
//////        try {
//////            if (authorKey == null) return null;
//////            String url = "https://openlibrary.org" + authorKey + ".json"; // e.g., /authors/OL12345A.json
//////            String raw = restTemplate.getForObject(url, String.class);
//////            if (raw == null) return null;
//////            JsonNode node = mapper.readTree(raw);
//////            return node.path("name").asText(null);
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////            return null;
//////        }
//////    }
//////
//////    // ------------------------
//////    // Helpers
//////    // ------------------------
//////    private String buildCoverUrl(Integer coverId, String size) {
//////        if (coverId == null) return null;
//////        return String.format("https://covers.openlibrary.org/b/id/%d-%s.jpg", coverId, size);
//////    }
//////
//////    private String normalizeBookId(String bookId) {
//////        if (bookId == null) return "";
//////        bookId = bookId.trim();
//////        bookId = bookId.replaceFirst("^/", "");
//////        bookId = bookId.replace("works/", "").replace("/works/", "");
//////        return bookId;
//////    }
//////}
//////
////
////
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
////            // Resolve author names from refs if needed
////            // ------------------------
////            List<String> authorNames = new ArrayList<>();
////            if (bookDoc.getAuthors() != null && !bookDoc.getAuthors().isEmpty()) {
////                authorNames = bookDoc.getAuthors(); // already names
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
////            response.setDescription(bookDoc.getDescription());
////            response.setCategories(bookDoc.getSubjects());
////
////            // Unified cover ID
////            Integer coverId = bookDoc.getCoverId() != null ? bookDoc.getCoverId()
////                    : (bookDoc.getCoverIds() != null && !bookDoc.getCoverIds().isEmpty() ? bookDoc.getCoverIds().get(0) : null);
////
////            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
////            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));
////
////            // ------------------------
////            // Add readUrl
////            // ------------------------
////            if (bookDoc.getWorkKey() != null) {
////                String normalizedKey = bookDoc.getWorkKey().replaceFirst("^/works/", "");
////                response.setReadUrl("https://openlibrary.org/works/" + normalizedKey);
////            }
////
////            return response;
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
////    // Fetch author name directly via RestTemplate
////    // ------------------------
////    private String fetchAuthorNameByKey(String authorKey) {
////        try {
////            if (authorKey == null) return null;
////            String url = "https://openlibrary.org" + authorKey + ".json"; // e.g., /authors/OL12345A.json
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
//
//package com.amigoscode.bookbuddybackend.service;
//
//import com.amigoscode.bookbuddybackend.client.OpenLibraryClient;
//import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibraryBookDoc;
//import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibrarySearchResponse;
//import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
//import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ExternalBookService {
//
//    private final OpenLibraryClient openLibraryClient;
//    private final ObjectMapper mapper;
//    private final RestTemplate restTemplate;
//
//    public ExternalBookService(OpenLibraryClient openLibraryClient, ObjectMapper mapper) {
//        this.openLibraryClient = openLibraryClient;
//        this.mapper = mapper;
//        this.restTemplate = new RestTemplate();
//    }
//
//    // ------------------------
//    // SEARCH BOOKS
//    // ------------------------
//    public BookSearchResponse searchBooks(String query) {
//        try {
//            String rawResponse = openLibraryClient.searchBooks(query);
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
//            if (bookId.toUpperCase().startsWith("OL") && bookId.toUpperCase().endsWith("W")) {
//                rawResponse = openLibraryClient.getBookByWorkId(bookId);
//            } else {
//                rawResponse = openLibraryClient.getBookByIsbn(bookId);
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
//    // MAP JSON → BookDetailResponse (Work/ISBN)
//    // ------------------------
//    private BookDetailResponse mapJsonToBookDetailResponse(String rawJson) {
//        try {
//            OpenLibraryBookDoc bookDoc;
//            try {
//                bookDoc = mapper.readValue(rawJson, OpenLibraryBookDoc.class);
//            } catch (Exception e) {
//                OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
//                List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
//                if (docs == null || docs.isEmpty()) return new BookDetailResponse();
//                bookDoc = docs.get(0);
//            }
//
//            BookDetailResponse response = new BookDetailResponse();
//            response.setBookId(bookDoc.getWorkKey());
//            response.setTitle(bookDoc.getTitle());
//
//            // ------------------------
//            // Resolve author names from refs if needed
//            // ------------------------
//            List<String> authorNames = new ArrayList<>();
//            if (bookDoc.getAuthors() != null && !bookDoc.getAuthors().isEmpty()) {
//                authorNames = bookDoc.getAuthors(); // already names
//            } else if (bookDoc.getAuthorRefs() != null) {
//                for (OpenLibraryBookDoc.AuthorRef ref : bookDoc.getAuthorRefs()) {
//                    if (ref != null && ref.getAuthor() != null) {
//                        String name = fetchAuthorNameByKey(ref.getAuthor().getKey());
//                        if (name != null) authorNames.add(name);
//                    }
//                }
//            }
//            response.setAuthors(authorNames);
//
//            response.setDescription(bookDoc.getDescription());
//            response.setCategories(bookDoc.getSubjects());
//
//            // Unified cover ID
//            Integer coverId = bookDoc.getCoverId() != null ? bookDoc.getCoverId()
//                    : (bookDoc.getCoverIds() != null && !bookDoc.getCoverIds().isEmpty() ? bookDoc.getCoverIds().get(0) : null);
//
//            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
//            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));
//
//            // ------------------------
//            // Multi-platform read links
//            // ------------------------
//            List<BookDetailResponse.ReadLink> readLinks = new ArrayList<>();
//
//            // Open Library
//            if (bookDoc.getWorkKey() != null) {
//                String key = bookDoc.getWorkKey().replaceFirst("^/works/", "");
//                readLinks.add(new BookDetailResponse.ReadLink("Open Library", "https://openlibrary.org/works/" + key));
//            }
//
//            // Project Gutenberg
//            String gutenbergUrl = "https://www.gutenberg.org/ebooks/search/?query=" + bookDoc.getTitle().replace(" ", "+");
//            readLinks.add(new BookDetailResponse.ReadLink("Project Gutenberg", gutenbergUrl));
//
//            // Internet Archive
//            String archiveUrl = "https://archive.org/search.php?query=" + bookDoc.getTitle().replace(" ", "+");
//            readLinks.add(new BookDetailResponse.ReadLink("Internet Archive", archiveUrl));
//
//            response.setReadLinks(readLinks);
//
//            return response;
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
//            OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
//            List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
//            if (docs == null || docs.isEmpty()) return new BookSearchResponse();
//
//            OpenLibraryBookDoc doc = docs.get(0);
//            BookSearchResponse response = new BookSearchResponse();
//            response.setBookId(doc.getWorkKey());
//            response.setTitle(doc.getTitle());
//            response.setAuthors(doc.getAuthors());
//            response.setDescription(doc.getDescription());
//            response.setCategories(doc.getSubjects());
//
//            Integer coverId = doc.getCoverId() != null ? doc.getCoverId()
//                    : (doc.getCoverIds() != null && !doc.getCoverIds().isEmpty() ? doc.getCoverIds().get(0) : null);
//
//            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
//            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));
//
//            return response;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new BookSearchResponse();
//        }
//    }
//
//    // ------------------------
//    // Fetch author name directly via RestTemplate
//    // ------------------------
//    private String fetchAuthorNameByKey(String authorKey) {
//        try {
//            if (authorKey == null) return null;
//            String url = "https://openlibrary.org" + authorKey + ".json"; // e.g., /authors/OL12345A.json
//            String raw = restTemplate.getForObject(url, String.class);
//            if (raw == null) return null;
//            JsonNode node = mapper.readTree(raw);
//            return node.path("name").asText(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // ------------------------
//    // Helpers
//    // ------------------------
//    private String buildCoverUrl(Integer coverId, String size) {
//        if (coverId == null) return null;
//        return String.format("https://covers.openlibrary.org/b/id/%d-%s.jpg", coverId, size);
//    }
//
//    private String normalizeBookId(String bookId) {
//        if (bookId == null) return "";
//        bookId = bookId.trim();
//        bookId = bookId.replaceFirst("^/", "");
//        bookId = bookId.replace("works/", "").replace("/works/", "");
//        return bookId;
//    }
//}

package com.amigoscode.bookbuddybackend.service;

import com.amigoscode.bookbuddybackend.client.OpenLibraryClient;
import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibraryBookDoc;
import com.amigoscode.bookbuddybackend.dto.openlibrary.OpenLibrarySearchResponse;
import com.amigoscode.bookbuddybackend.dto.response.BookDetailResponse;
import com.amigoscode.bookbuddybackend.dto.response.BookSearchResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExternalBookService {

    private final OpenLibraryClient openLibraryClient;
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;

    public ExternalBookService(OpenLibraryClient openLibraryClient, ObjectMapper mapper) {
        this.openLibraryClient = openLibraryClient;
        this.mapper = mapper;
        this.restTemplate = new RestTemplate();
    }

    // ------------------------
    // SEARCH BOOKS
    // ------------------------
    public BookSearchResponse searchBooks(String query) {
        try {
            String rawResponse = openLibraryClient.searchBooks(query);
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

            if (bookId.toUpperCase().startsWith("OL") && bookId.toUpperCase().endsWith("W")) {
                rawResponse = openLibraryClient.getBookByWorkId(bookId);
            } else {
                rawResponse = openLibraryClient.getBookByIsbn(bookId);
            }

            return mapJsonToBookDetailResponse(rawResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return new BookDetailResponse();
        }
    }

    // ------------------------
    // MAP JSON → BookDetailResponse (Work/ISBN)
    // ------------------------
    private BookDetailResponse mapJsonToBookDetailResponse(String rawJson) {
        try {
            OpenLibraryBookDoc bookDoc;
            try {
                bookDoc = mapper.readValue(rawJson, OpenLibraryBookDoc.class);
            } catch (Exception e) {
                OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
                List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
                if (docs == null || docs.isEmpty()) return new BookDetailResponse();
                bookDoc = docs.get(0);
            }

            BookDetailResponse response = new BookDetailResponse();
            response.setBookId(bookDoc.getWorkKey());
            response.setTitle(bookDoc.getTitle());

            // ------------------------
            // Resolve author names
            // ------------------------
            List<String> authorNames = new ArrayList<>();
            if (bookDoc.getAuthors() != null && !bookDoc.getAuthors().isEmpty()) {
                authorNames = bookDoc.getAuthors();
            } else if (bookDoc.getAuthorRefs() != null) {
                for (OpenLibraryBookDoc.AuthorRef ref : bookDoc.getAuthorRefs()) {
                    if (ref != null && ref.getAuthor() != null) {
                        String name = fetchAuthorNameByKey(ref.getAuthor().getKey());
                        if (name != null) authorNames.add(name);
                    }
                }
            }
            response.setAuthors(authorNames);

            // ------------------------
            // Description
            // ------------------------
            response.setDescription(bookDoc.getDescription());
            if (response.getDescription() == null || response.getDescription().isEmpty()) {
                String fallbackDesc = fetchFallbackDescription(bookDoc.getTitle());
                response.setFallbackDescription(fallbackDesc);
            }

            response.setCategories(bookDoc.getSubjects());

            // ------------------------
            // Cover images
            // ------------------------
            Integer coverId = bookDoc.getCoverId() != null ? bookDoc.getCoverId()
                    : (bookDoc.getCoverIds() != null && !bookDoc.getCoverIds().isEmpty() ? bookDoc.getCoverIds().get(0) : null);
            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));

            // ------------------------
            // Multi-platform read links
            // ------------------------
            List<BookDetailResponse.ReadLink> readLinks = new ArrayList<>();
            if (bookDoc.getWorkKey() != null) {
                String key = bookDoc.getWorkKey().replaceFirst("^/works/", "");
                readLinks.add(new BookDetailResponse.ReadLink("Open Library",
                        "https://openlibrary.org/works/" + key));
            }

            String encodedTitle = URLEncoder.encode(bookDoc.getTitle(), StandardCharsets.UTF_8);
            readLinks.add(new BookDetailResponse.ReadLink("Project Gutenberg",
                    "https://www.gutenberg.org/ebooks/search/?query=" + encodedTitle));
            readLinks.add(new BookDetailResponse.ReadLink("Internet Archive",
                    "https://archive.org/search.php?query=" + encodedTitle));

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
            OpenLibrarySearchResponse searchResponse = mapper.readValue(rawJson, OpenLibrarySearchResponse.class);
            List<OpenLibraryBookDoc> docs = searchResponse.getDocs();
            if (docs == null || docs.isEmpty()) return new BookSearchResponse();

            OpenLibraryBookDoc doc = docs.get(0);
            BookSearchResponse response = new BookSearchResponse();
            response.setBookId(doc.getWorkKey());
            response.setTitle(doc.getTitle());
            response.setAuthors(doc.getAuthors());
            response.setDescription(doc.getDescription());
            response.setCategories(doc.getSubjects());

            Integer coverId = doc.getCoverId() != null ? doc.getCoverId()
                    : (doc.getCoverIds() != null && !doc.getCoverIds().isEmpty() ? doc.getCoverIds().get(0) : null);

            response.setCoverImageUrl(buildCoverUrl(coverId, "L"));
            response.setSmallCoverImageUrl(buildCoverUrl(coverId, "S"));

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new BookSearchResponse();
        }
    }

    // ------------------------
    // Fetch author name
    // ------------------------
    private String fetchAuthorNameByKey(String authorKey) {
        try {
            if (authorKey == null) return null;
            String url = "https://openlibrary.org" + authorKey + ".json";
            String raw = restTemplate.getForObject(url, String.class);
            if (raw == null) return null;
            JsonNode node = mapper.readTree(raw);
            return node.path("name").asText(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ------------------------
    // Fallback description
    // ------------------------
    private String fetchFallbackDescription(String title) {
        try {
            String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
            String url = "https://gutendex.com/books/?search=" + encoded;
            String raw = restTemplate.getForObject(url, String.class);
            if (raw != null) {
                JsonNode root = mapper.readTree(raw);
                JsonNode results = root.path("results");
                if (results.isArray() && results.size() > 0) {
                    JsonNode first = results.get(0);
                    JsonNode descNode = first.path("description");
                    if (!descNode.isMissingNode()) {
                        return descNode.asText();
                    }
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
    private String buildCoverUrl(Integer coverId, String size) {
        if (coverId == null) return null;
        return String.format("https://covers.openlibrary.org/b/id/%d-%s.jpg", coverId, size);
    }

    private String normalizeBookId(String bookId) {
        if (bookId == null) return "";
        bookId = bookId.trim();
        bookId = bookId.replaceFirst("^/", "");
        bookId = bookId.replace("works/", "").replace("/works/", "");
        return bookId;
    }
}

