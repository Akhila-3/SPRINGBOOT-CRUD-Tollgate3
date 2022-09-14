package com.library.bookservice.repository;

import com.library.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/*
Add appropriate annotation/s and implement required interface/s to create a bean for handling database interactions using Data JPA
*/
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    /* **USE FIND KEYWORD SYNTAX to define data access methods** */

    /*
         Define a method to get book by author id and book published date, returning an Optional of book
    */
    Optional<Book> findBookByAuthorIdAndBookPublishedDate(String authorId, LocalDate bookPublishedDate);

    /*
        Define a method to get all books by author id, returning list of books
    */
    List<Book> findBookByAuthorId(String authorId);

    /*
        Define a method to get all books by library id and book published date, returning list of books
    */
    List<Book> findBookByLibraryIdAndBookPublishedDate(String libraryId, LocalDate bookPublishedDate);
}