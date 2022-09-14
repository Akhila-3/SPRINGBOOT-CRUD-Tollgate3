package com.library.bookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.bookservice.dto.BookStatusDto;
import com.library.bookservice.errorhandler.BookExistsException;
import com.library.bookservice.errorhandler.BookNotFoundException;
import com.library.bookservice.model.Book;
import com.library.bookservice.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    private Book bookOne, bookTwo, bookThree, bookOld;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    @Autowired
    private ObjectMapper mapper;

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
    public void givenBookDetailsWhenAddedThenReturnSuccessCode() throws Exception {
        when(service.addBook(any(Book.class))).thenReturn(bookOne);

        MvcResult mvcResult = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookOne)))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), Book.class))
                .usingRecursiveComparison().isEqualTo(bookOne);
    }

    @Test
    public void givenExistingBookDetailsWhenAddedThenReturnConflictCode() throws Exception {
        when(service.addBook(any(Book.class))).thenThrow(new BookExistsException());

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookOne)))
                .andExpect(status().isConflict());
    }

    @Test
    public void givenAuthorIdWhenExistsThenReturnBook() throws Exception {
        when(service.getBooksByAuthorId(anyString())).thenReturn(List.of(bookTwo, bookThree));

        MvcResult mvcResult = mockMvc
                .perform(get("/api/books/author/AUTH001"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class))
                .hasSize(2);
    }

    @Test
    public void givenAuthorIdWhenDoesNotExistsThenReturnNotFound() throws Exception {
        when(service.getBooksByAuthorId(anyString())).thenThrow(new BookNotFoundException());

        mockMvc.perform(get("/api/books/author/AUTH999"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void givenBookIdWhenExistsThenReturnBook() throws Exception {
        when(service.getBookById(anyInt())).thenReturn(bookOne);

        MvcResult mvcResult = mockMvc
                .perform(get("/api/books/1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), Book.class))
                .usingRecursiveComparison().isEqualTo(bookOne);
    }

    @Test
    public void givenBookIdWhenDoesNotExistsThenReturnNotFound() throws Exception {
        when(service.getBookById(anyInt())).thenThrow(new BookNotFoundException());

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenLibraryAndBookPublishedDateWhenExistsThenReturnBooks() throws Exception {
        when(service.getBooksByLibraryAndBookPublishedDate(anyString(), any(LocalDate.class)))
                .thenReturn(List.of(bookOne, bookThree));

        MvcResult mvcResult = mockMvc
                .perform(get("/api/books?library=LIB001&date=2022-01-01"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class))
                .hasSize(2);
    }

    @Test
    public void givenStatusWhenBookExistsThenReturnUpdatedBook() throws Exception {
        when(service.updateBookStatus(anyInt(), anyString())).thenReturn(bookOld);

        MvcResult settled = mockMvc.perform(put("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new BookStatusDto(1, "OLD"))))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mapper.readValue(settled.getResponse().getContentAsString(), Book.class))
                .usingRecursiveComparison().isEqualTo(bookOld);

    }
}