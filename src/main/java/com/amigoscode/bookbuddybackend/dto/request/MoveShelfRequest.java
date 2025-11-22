////package com.amigoscode.bookbuddybackend.dto.request;
////
////import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
////
////public record MoveShelfRequest(ShelfType newShelf) { }
//
//package com.amigoscode.bookbuddybackend.dto.request;
//
//import com.amigoscode.bookbuddybackend.model.enums.ShelfType;
//
//public record MoveShelfRequest(ShelfType newShelf) { }


package com.amigoscode.bookbuddybackend.dto.request;

import com.amigoscode.bookbuddybackend.model.enums.ShelfType;

public class MoveShelfRequest {

    private ShelfType newShelf;

    public MoveShelfRequest() { }

    public MoveShelfRequest(ShelfType newShelf) {
        this.newShelf = newShelf;
    }

    public ShelfType newShelf() {
        return newShelf;
    }

    public ShelfType getNewShelf() {
        return newShelf;
    }

    public void setNewShelf(ShelfType newShelf) {
        this.newShelf = newShelf;
    }
}
