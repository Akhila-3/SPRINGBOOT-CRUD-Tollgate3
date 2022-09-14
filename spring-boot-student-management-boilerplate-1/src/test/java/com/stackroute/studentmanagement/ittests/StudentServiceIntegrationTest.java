package com.stackroute.studentmanagement.ittests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.studentmanagement.StudentmanagementApplication;
import com.stackroute.studentmanagement.model.Student;
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
        classes = StudentmanagementApplication.class
)

@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentServiceIntegrationTest {

    public static final String STUDENTS_BASE_URL = "/api/students";
    private Student studentOne;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        studentOne = new Student(0, "Mihir", "mih@gmail.com", "XII","9999277301", LocalDate.now());
    }

    @AfterEach
    public void tearDown() {
        studentOne = null;
    }

    @Test
    @Order(1)
    public void givenStudentDetailsThenCreateNewStudent() throws Exception {
        mockMvc.perform(
                        post(STUDENTS_BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(studentOne)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @Order(2)
    public void givenGradeWhenStudentExistsThenReturnStudents() throws Exception {
        mockMvc.perform(
                        get(STUDENTS_BASE_URL + "/grade/XII"))
                .andExpect(status().isOk())
                .andReturn();

    }

    private String toJson(final Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}
