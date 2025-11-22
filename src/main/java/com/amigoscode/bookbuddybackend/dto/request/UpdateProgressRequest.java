//package com.amigoscode.bookbuddybackend.dto.request;
//
//public class UpdateProgressRequest {
//
//    private String bookId;
//    private int progress; // Percentage
//
//    // Getters and Setters
//
//    public String getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(String bookId) {
//        this.bookId = bookId;
//    }
//
//    public int getProgress() {
//        return progress;
//    }
//
//    public void setProgress(int progress) {
//        this.progress = progress;
//    }
//}

package com.amigoscode.bookbuddybackend.dto.request;

public class UpdateProgressRequest {

    private String bookId;
    private int progress; // 0 - 100

    public UpdateProgressRequest() { }

    public UpdateProgressRequest(String bookId, int progress) {
        this.bookId = bookId;
        this.progress = progress;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
