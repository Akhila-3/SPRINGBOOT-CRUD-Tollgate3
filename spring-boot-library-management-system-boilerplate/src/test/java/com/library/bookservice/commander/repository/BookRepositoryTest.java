package com.library.bookservice.commander.repository;

import com.library.bookservice.commander.MethodExecutor;
import com.library.bookservice.model.Book;
import com.library.bookservice.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {


    @Autowired
    private BookRepository bookRepository;

    private Book bookOne, bookTwo, bookThree;

    @BeforeEach
    public void setUp() {
        bookOne = new Book(0, "AUTH001", "LIB001", LocalDate.now(), "NEW", 10);
        bookTwo = new Book(0, "AUTH002", "LIB002", LocalDate.now(), "NEW", 20);
        bookThree = new Book(0, "AUTH002", "LIB001", LocalDate.now(), "NEW", 20);

        MethodExecutor.executeMethod(bookRepository, "save",
                new Class[]{Object.class},
                new Object[]{bookOne});
        MethodExecutor.executeMethod(bookRepository, "save",
                new Class[]{Object.class},
                new Object[]{bookTwo});
        MethodExecutor.executeMethod(bookRepository, "save",
                new Class[]{Object.class},
                new Object[]{bookThree});
    }

    @Test
    public void givenBookIdWhenNotExistsThenReturnEmptyOptional() {
        Optional<Book> optionalBook = (Optional<Book>) MethodExecutor.executeMethod(bookRepository, "findById",
                new Class[]{Object.class},
                new Object[]{999});
        assertThat(optionalBook).isEmpty();
    }

    @Test
    public void givenAuthorIdWhenExistsThenReturnOptionalWithBook() {
        List<Book> books = (List<Book>) MethodExecutor.executeMethod(bookRepository, "findBookByAuthorId",
                new Class[]{String.class},
                new Object[]{"AUTH002"});
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    public void givenAuthorIdWhenNotExistsThenReturnEmptyList() {

        List<Book> books = (List<Book>) MethodExecutor.executeMethod(bookRepository, "findBookByAuthorId",
                new Class[]{String.class},
                new Object[]{"AUTH999"});
        assertThat(books.size()).isZero();

    }

    @Test
    public void givenAuthorIdAndBookPublishedDateWhenExistsThenReturnOptionalWithBook() {
        Optional<Book> optionalBook = (Optional<Book>) MethodExecutor.executeMethod(bookRepository, "findBookByAuthorIdAndBookPublishedDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{"AUTH001", LocalDate.now()});
        assertThat(optionalBook).isPresent();
        assertThat(optionalBook.get().getLibraryId()).isEqualTo("LIB001");
    }

    @Test
    public void givenLibraryIdAndBookPublishedDateWhenExistsThenReturnList() {
        List<Book> books = (List<Book>) MethodExecutor.executeMethod(bookRepository, "findBookByLibraryIdAndBookPublishedDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{"LIB001", LocalDate.now()});

        assertThat(books.size()).isEqualTo(2);

    }


    @AfterEach
    public void tearDown() {
        MethodExecutor.executeMethod(bookRepository, "deleteAll",
                new Class[]{},
                new Object[]{});
        bookOne = null;
        bookTwo = null;
        bookThree = null;

    }
}