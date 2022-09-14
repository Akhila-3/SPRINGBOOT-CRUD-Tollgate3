package com.library.bookservice.ittests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.bookservice.BookServiceApplication;
import com.library.bookservice.model.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BookServiceApplication.class
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceIntegrationTest {

    public static final String BOOKS_BASE_URL = "/api/books";
    private Book bookOne;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        bookOne = new Book(1, "AUTH001", "LIB001", LocalDate.now(), "NEW", 10);
    }

    @AfterEach
    public void tearDown() {
        bookOne = null;
    }

    @Test
    @Order(1)
    public void givenBookDetailsThenAddNewBook() throws Exception {
        mockMvc.perform(
                        post(BOOKS_BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(bookOne)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @Order(2)
    public void givenAuthorIdWhenBookExistsThenReturnBooks() throws Exception {
        mockMvc.perform(
                        get(BOOKS_BASE_URL + "/author/AUTH001"))
                .andExpect(status().isOk())
                .andReturn();

    }

    private String toJson(final Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}
