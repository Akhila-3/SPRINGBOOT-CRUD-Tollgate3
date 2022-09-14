package com.library.bookservice.dto;

/*
 The Dto should be used for updating book status
 */
public class BookStatusDto {
    private Integer bookId;
    private String status;

    public BookStatusDto() {
    }

    public BookStatusDto(Integer bookId, String status) {
        this.bookId = bookId;
        this.status = status;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
