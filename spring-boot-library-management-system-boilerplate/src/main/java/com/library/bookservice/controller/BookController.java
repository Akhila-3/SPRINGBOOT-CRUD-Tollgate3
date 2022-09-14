package com.library.bookservice.controller;

import com.library.bookservice.dto.BookStatusDto;
import com.library.bookservice.errorhandler.BookExistsException;
import com.library.bookservice.errorhandler.BookNotFoundException;
import com.library.bookservice.model.Book;
import com.library.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/*
Add appropriate annotation/s to create a bean for handling http requests for the rest api
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    /*
    Inject the service object here
     */
    @Autowired
    private BookService service;


    /*
    Create API endpoints as per the requirement given below
    */

    /*
     description  :  add new book
     api endpoint : /api/books
     http request : POST
     request body : book details
     success response:
            body: added book    - http status: 201
     failure response:
            If a book exists with same author id and book published date
            body: failure message   - http status: 409
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) throws BookExistsException {
        return service.addBook(book);
    }

    /*
     description  : get a book by book id
     api endpoint : /api/books/{id}
     http request : GET
     success response:
            body: Book Details   - http status: 200
     failure response:
            If no books exists for given criteria
            body: failure message - http status: 404
  */
    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable Integer bookId) throws BookNotFoundException {
        return service.getBookById(bookId);
    }


    /*
     description  : get all books by author id
     api endpoint : /api/books/author/{authorId}
     http request : GET
     success response:
            body: list of books    - http status: 200
     failure response:
            if no books exists for given criteria
            body: failure message   - http status: 404
  */
    @GetMapping("/author/{authorId}")
    public List<Book> getBooksByAuthorId(@PathVariable String authorId) throws BookNotFoundException {
        return service.getBooksByAuthorId(authorId);
    }


    /*
     description  : get all books by library Id and book published date
     api endpoint : /api/books
     request query parameters: library and date
                               ex: <...pathurl>?library=xxx&date=yyyy-MM-dd
     http request : GET
     success response:
            body: list of books    - http status: 200
     failure response:
            if no books exists for given criteria
            body: failure message   - http status: 404
  */
    @GetMapping
    public List<Book> getBooksByLibraryAndPublishedDate(@RequestParam("library") String libraryId,
                                                       @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate bookPublishedDate) throws BookNotFoundException {
        return service.getBooksByLibraryAndBookPublishedDate(libraryId,bookPublishedDate);
    }


    /*
     description  : update the book status for given book id
     api endpoint : /api/books
     http request : PUT
     request body : book id and new book status
     success response:
            body: updated book    - http status: 200
     failure response:
            If no books exists for given criteria
            body: failure message - http status: 404
  */
    @PutMapping
    public Book updateBookStatus(@RequestBody BookStatusDto bookStatusDto) throws BookNotFoundException {
        return service.updateBookStatus(bookStatusDto.getBookId(), bookStatusDto.getStatus());
    }

}