package com.library.bookservice.service;

import com.library.bookservice.errorhandler.BookExistsException;
import com.library.bookservice.errorhandler.BookNotFoundException;
import com.library.bookservice.model.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {

    Book addBook(Book newBook) throws BookExistsException;

    Book getBookById(Integer id) throws BookNotFoundException;

    List<Book> getBooksByAuthorId(String authorId) throws BookNotFoundException;

    List<Book> getBooksByLibraryAndBookPublishedDate(String libraryId, LocalDate bookPublishedDate) throws BookNotFoundException;

    Book updateBookStatus(int bookId, String status) throws BookNotFoundException;
}
