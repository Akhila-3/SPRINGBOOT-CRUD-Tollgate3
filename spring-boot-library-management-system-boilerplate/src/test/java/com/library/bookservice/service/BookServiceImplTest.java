package com.library.bookservice.service;

import com.library.bookservice.MethodExecutor;
import com.library.bookservice.errorhandler.BookExistsException;
import com.library.bookservice.errorhandler.BookNotFoundException;
import com.library.bookservice.model.Book;
import com.library.bookservice.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository repository;


    @InjectMocks
    private BookServiceImpl service;

    private Book bookOne, bookTwo, bookThree, bookOld;

    @BeforeEach
    public void setUp() {
        bookOne = new Book(1, "AUTH001", "LIB001", LocalDate.now(), "NEW", 10);
        bookTwo = new Book(2, "AUTH002", "LIB002", LocalDate.now(), "NEW", 20);
        bookThree = new Book(3, "AUTH002", "LIB001", LocalDate.now(), "NEW", 20);
        bookOld = new Book(1, "AUTH001", "LIB001", LocalDate.now(), "OLD", 10);

    }

    @AfterEach
    public void tearDown() {
        bookOne = null;
        bookTwo = null;
        bookThree = null;
    }

    @Test
    public void givenBookWhenDoesNotExistThenReturnAddedBook() throws BookExistsException {
        when(MethodExecutor.executeMethod(repository, "findBookByAuthorIdAndBookPublishedDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(Optional.empty());
        when(MethodExecutor.executeMethod(repository, "save",
                new Class[]{Object.class},
                new Object[]{any(Book.class)}))
                .thenReturn(bookOne);

        Book book = service.addBook(bookOne);
        assertEquals(book, bookOne);


    }


    @Test
    public void givenBookWhenExistsThenThrowException() throws BookExistsException {
        when(MethodExecutor.executeMethod(repository, "findBookByAuthorIdAndBookPublishedDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(Optional.of(bookOne));

        assertThrows(BookExistsException.class, () -> service.addBook(bookOne));
    }

    @Test
    public void givenBookIdWhenExistsThenReturnBook() throws BookNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findById",
                new Class[]{Integer.class},
                new Object[]{anyInt()}))
                .thenReturn(Optional.of(bookOne));

        assertEquals(service.getBookById(1), bookOne);
    }

    @Test
    public void givenBookIdWhenDoesNotExistThenThrowException() throws BookNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findById",
                new Class[]{Integer.class},
                new Object[]{anyInt()}))
                .thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> service.getBookById(9));
    }


    @Test
    public void givenAuthorIdWhenExistsThenReturnListOfBook() throws BookNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findBookByAuthorId",
                new Class[]{String.class},
                new Object[]{anyString()}))
                .thenReturn(List.of(bookTwo, bookThree));

        List<Book> booksByAuthorId = service.getBooksByAuthorId("AUTH002");
        assertEquals(2, booksByAuthorId.size());
    }

    @Test
    public void givenAuthorIdWhenDoesNotExistThenThrowException() throws BookNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findBookByAuthorId",
                new Class[]{String.class},
                new Object[]{anyString()}))
                .thenReturn(Collections.emptyList());

        assertThrows(BookNotFoundException.class, () -> service.getBooksByAuthorId("AUTH009"));
    }


    @Test
    public void givenLibraryIdAndBookPublishedDateWhenExistsThenReturnListOfBook() throws BookNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findBookByLibraryIdAndBookPublishedDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(List.of(bookTwo, bookThree));

        List<Book> books = service.getBooksByLibraryAndBookPublishedDate("LIB001", LocalDate.now());
        assertEquals(2, books.size());
    }

    @Test
    public void givenLibraryIdAndBookPublishedDateWhenDoesNotExistThenThrowException() throws BookNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findBookByLibraryIdAndBookPublishedDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(Collections.emptyList());

        assertThrows(BookNotFoundException.class, () -> service.getBooksByLibraryAndBookPublishedDate("LIB009",LocalDate.now()));
    }

    @Test
    public void givenBookIdWhenExistsThenReturnUpdatedBook() throws BookNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findById",
                new Class[]{Integer.class},
                new Object[]{1}))
                .thenReturn(Optional.of(bookOne));
        when(MethodExecutor.executeMethod(repository, "save",
                new Class[]{Object.class},
                new Object[]{any(Book.class)}))
                .thenReturn(bookOld);

        assertEquals(bookOld,service.updateBookStatus(1, "OLD"));

    }

}