package com.amigoscode.bookbuddybackend.dto.response;

import com.amigoscode.bookbuddybackend.model.Book;
import com.amigoscode.bookbuddybackend.model.embedded.UserBook;

public class ShelfItemResponse {

    private Book book;
    private UserBook userBook;

    public ShelfItemResponse(Book book, UserBook userBook) {
        this.book = book;
        this.userBook = userBook;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public UserBook getUserBook() {
        return userBook;
    }

    public void setUserBook(UserBook userBook) {
        this.userBook = userBook;
    }
}
