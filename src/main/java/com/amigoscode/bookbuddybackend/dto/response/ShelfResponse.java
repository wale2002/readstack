//package com.amigoscode.bookbuddybackend.dto.response;
//
//import com.amigoscode.bookbuddybackend.model.embedded.UserBook;
//
//import java.util.List;
//
//public class ShelfResponse {
//
//    private List<UserBook> currentlyReading;
//    private List<UserBook> wantToRead;
//    private List<UserBook> read;
//
//    public List<UserBook> getCurrentlyReading() {
//        return currentlyReading;
//    }
//
//    public void setCurrentlyReading(List<UserBook> currentlyReading) {
//        this.currentlyReading = currentlyReading;
//    }
//
//    public List<UserBook> getWantToRead() {
//        return wantToRead;
//    }
//
//    public void setWantToRead(List<UserBook> wantToRead) {
//        this.wantToRead = wantToRead;
//    }
//
//    public List<UserBook> getRead() {
//        return read;
//    }
//
//    public void setRead(List<UserBook> read) {
//        this.read = read;
//    }
//}


// src/main/java/com/amigoscode/bookbuddybackend/dto/response/ShelfResponse.java
package com.amigoscode.bookbuddybackend.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ShelfResponse {

    private List<ShelfItemResponse> currentlyReading = new ArrayList<>();
    private List<ShelfItemResponse> wantToRead = new ArrayList<>();
    private List<ShelfItemResponse> read = new ArrayList<>();

    // Getters & Setters
    public List<ShelfItemResponse> getCurrentlyReading() { return currentlyReading; }
    public void setCurrentlyReading(List<ShelfItemResponse> currentlyReading) { this.currentlyReading = currentlyReading; }

    public List<ShelfItemResponse> getWantToRead() { return wantToRead; }
    public void setWantToRead(List<ShelfItemResponse> wantToRead) { this.wantToRead = wantToRead; }

    public List<ShelfItemResponse> getRead() { return read; }
    public void setRead(List<ShelfItemResponse> read) { this.read = read; }
}