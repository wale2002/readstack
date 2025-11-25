////
//////
//////
//////package com.amigoscode.bookbuddybackend.dto.openlibrary;
//////
//////import com.fasterxml.jackson.annotation.JsonIgnore;
//////import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//////import com.fasterxml.jackson.annotation.JsonProperty;
//////import lombok.Getter;
//////import lombok.Setter;
//////
//////import java.util.ArrayList;
//////import java.util.List;
//////import java.util.Map;
//////
//////@JsonIgnoreProperties(ignoreUnknown = true)
//////public class OpenLibraryBookDoc {
//////
//////    @Getter
//////    @JsonProperty("key")
//////    private String workKey;
//////
//////    @Getter @Setter
//////    private String title;
//////
//////    // Authors reference array from works endpoint
//////    @JsonProperty("authors")
//////    private List<AuthorRef> authorRefs;
//////
//////    @Getter
//////    @JsonProperty("covers")
//////    private List<Integer> coverIds;
//////
//////    @Setter
//////    @JsonProperty("description")
//////    private Object descriptionRaw;
//////
//////    @JsonProperty("subjects")
//////    private List<String> subjects;
//////
//////    // ------------------------
//////    // Nested author reference
//////    // ------------------------
//////    @JsonIgnoreProperties(ignoreUnknown = true)
//////    public static class AuthorRef {
//////        private AuthorKey author;
//////        public AuthorKey getAuthor() { return author; }
//////        public void setAuthor(AuthorKey author) { this.author = author; }
//////    }
//////
//////    @JsonIgnoreProperties(ignoreUnknown = true)
//////    public static class AuthorKey {
//////        private String key;
//////        public String getKey() { return key; }
//////        public void setKey(String key) { this.key = key; }
//////    }
//////
//////    // ------------------------
//////    // GETTERS / HELPERS
//////    // ------------------------
//////    public List<String> getAuthors() {
//////        if (authorRefs == null) return null;
//////        List<String> namesOrKeys = new ArrayList<>();
//////        for (AuthorRef ref : authorRefs) {
//////            if (ref != null && ref.getAuthor() != null)
//////                namesOrKeys.add(ref.getAuthor().getKey());
//////        }
//////        return namesOrKeys;
//////    }
//////
//////    public Integer getCoverId() {
//////        if (coverIds != null && !coverIds.isEmpty()) return coverIds.get(0);
//////        return null;
//////    }
//////
//////    @JsonIgnore
//////    public Object getDescriptionRaw() {
//////        return descriptionRaw;
//////    }
//////
//////    // Unified description
//////    public String getDescription() {
//////        if (descriptionRaw == null) return null;
//////
//////        if (descriptionRaw instanceof String s) return s;
//////
//////        if (descriptionRaw instanceof Map<?, ?> map) {
//////            Object v = map.get("value");
//////            return v != null ? v.toString() : null;
//////        }
//////
//////        return descriptionRaw.toString();
//////    }
//////
//////    public List<String> getSubjects() {
//////        return subjects;
//////    }
//////
//////    public void setSubjects(List<String> subjects) {
//////        this.subjects = subjects;
//////    }
//////
//////    public void setWorkKey(String workKey) { this.workKey = workKey; }
//////}
////
////
////package com.amigoscode.bookbuddybackend.dto.openlibrary;
////
////import com.fasterxml.jackson.annotation.JsonIgnore;
////import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
////import com.fasterxml.jackson.annotation.JsonProperty;
////import lombok.Getter;
////import lombok.Setter;
////
////import java.util.List;
////
////@Getter
////@Setter
////@JsonIgnoreProperties(ignoreUnknown = true)
////public class OpenLibraryBookDoc {
////
////    // ---------- SEARCH FIELDS ----------
////    @JsonProperty("key")
////    private String workKey;  // /works/OL257943W
////
////    @JsonProperty("title")
////    private String title;
////
////    @JsonProperty("author_name")
////    private List<String> authors;
////
////    @JsonProperty("subject")
////    private List<String> subjects;
////
////    @JsonProperty("cover_i")
////    private Integer coverId; // Search returns ONE id
////
////
////    // ---------- WORK DETAILS ----------
////    @JsonProperty("covers")
////    private List<Integer> coverIds;
////
////    // This receives the raw JSON field but MUST NOT expose a getter
////    @JsonProperty("description")
////    @JsonIgnore
////    private Object descriptionRaw;
////
////
////    // Only ONE getter Jackson will use for "description"
////    @JsonProperty("description")
////    public String getDescription() {
////        if (descriptionRaw == null) return null;
////
////        if (descriptionRaw instanceof String s) return s;
////
////        if (descriptionRaw instanceof java.util.Map map) {
////            Object val = map.get("value");
////            return val != null ? val.toString() : null;
////        }
////
////        return null;
////    }
////}
//
//
//package com.amigoscode.bookbuddybackend.dto.openlibrary;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Getter
//@Setter
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class OpenLibraryBookDoc {
//
//    // ---------- SEARCH FIELDS ----------
//    @JsonProperty("key")
//    private String workKey;  // /works/OL257943W
//
//    @JsonProperty("title")
//    private String title;
//
//    // For search endpoint
//    @JsonProperty("author_name")
//    private List<String> authors;
//
//    @JsonProperty("subject")
//    private List<String> subjects;
//
//    @JsonProperty("cover_i")
//    private Integer coverId;
//
//    // ---------- WORK DETAILS ----------
//    @JsonProperty("authors")
//    private List<AuthorRef> authorRefs; // Work endpoint returns author refs
//
//    @JsonProperty("covers")
//    private List<Integer> coverIds;
//
//    @JsonProperty("description")
//    @JsonIgnore
//    private Object descriptionRaw;
//
//    // Only ONE getter Jackson will use for "description"
//    @JsonProperty("description")
//    public String getDescription() {
//        if (descriptionRaw == null) return null;
//        if (descriptionRaw instanceof String s) return s;
//        if (descriptionRaw instanceof Map map) {
//            Object val = map.get("value");
//            return val != null ? val.toString() : null;
//        }
//        return null;
//    }
//
//    // ------------------------
//    // Get authors for work endpoint (keys only)
//    // ------------------------
//    public List<String> getAuthorsFromRefs() {
//        if (authorRefs == null) return null;
//        List<String> keys = new ArrayList<>();
//        for (AuthorRef ref : authorRefs) {
//            if (ref != null && ref.getAuthor() != null)
//                keys.add(ref.getAuthor().getKey()); // e.g., "/authors/OL12345A"
//        }
//        return keys;
//    }
//
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class AuthorRef {
//        private AuthorKey author;
//        public AuthorKey getAuthor() { return author; }
//        public void setAuthor(AuthorKey author) { this.author = author; }
//    }
//
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class AuthorKey {
//        private String key;
//        public String getKey() { return key; }
//        public void setKey(String key) { this.key = key; }
//    }
//}
