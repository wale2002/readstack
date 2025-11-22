////package com.amigoscode.bookbuddybackend.dto.request;
////
////import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
////
////public class AddBookRequest {
////
////    private String bookId;
////    private ShelfType shelfType;
////    private String notes;
////
////    // Getters and Setters
////
////    public String getBookId() {
////        return bookId;
////    }
////
////    public void setBookId(String bookId) {
////        this.bookId = bookId;
////    }
////
////    public ShelfType getShelfType() {
////        return shelfType;
////    }
////
////    public void setShelfType(ShelfType shelfType) {
////        this.shelfType = shelfType;
////    }
////
////    public String getNotes() {
////        return notes;
////    }
////
////    public void setNotes(String notes) {
////        this.notes = notes;
////    }
////}
//
//
//package com.amigoscode.bookbuddybackend.dto.request;
//
//import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
//
//public class AddBookRequest {
//
//    private String bookId;
//    private ShelfType shelfType;
//    private String notes;
//
//    public AddBookRequest() { }
//
//    public AddBookRequest(String bookId, ShelfType shelfType, String notes) {
//        this.bookId = bookId;
//        this.shelfType = shelfType;
//        this.notes = notes;
//    }
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
//    public String getNotes() {
//        return notes;
//    }
//
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }
//}


package com.amigoscode.bookbuddybackend.dto.request;

import com.amigoscode.bookbuddybackend.model.enums.ShelfType;

public class AddBookRequest {

    private String bookId;
    private String title;       // Optional: can be used in responses
    private ShelfType shelfType;
    private String notes;

    public AddBookRequest() { }

    public AddBookRequest(String bookId, String title, ShelfType shelfType, String notes) {
        this.bookId = bookId;
        this.title = title;
        this.shelfType = shelfType;
        this.notes = notes;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ShelfType getShelfType() {
        return shelfType;
    }

    public void setShelfType(ShelfType shelfType) {
        this.shelfType = shelfType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
