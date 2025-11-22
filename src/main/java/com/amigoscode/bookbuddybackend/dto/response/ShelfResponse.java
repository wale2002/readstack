package com.amigoscode.bookbuddybackend.dto.response;

import com.amigoscode.bookbuddybackend.model.embedded.UserBook;

import java.util.List;

public class ShelfResponse {

    private List<UserBook> currentlyReading;
    private List<UserBook> wantToRead;
    private List<UserBook> read;

    public List<UserBook> getCurrentlyReading() {
        return currentlyReading;
    }

    public void setCurrentlyReading(List<UserBook> currentlyReading) {
        this.currentlyReading = currentlyReading;
    }

    public List<UserBook> getWantToRead() {
        return wantToRead;
    }

    public void setWantToRead(List<UserBook> wantToRead) {
        this.wantToRead = wantToRead;
    }

    public List<UserBook> getRead() {
        return read;
    }

    public void setRead(List<UserBook> read) {
        this.read = read;
    }
}
