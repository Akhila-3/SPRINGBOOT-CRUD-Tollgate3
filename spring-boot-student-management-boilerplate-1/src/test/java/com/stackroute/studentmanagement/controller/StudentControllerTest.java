package com.stackroute.studentmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.studentmanagement.errorhandler.StudentAlreadyExistsException;
import com.stackroute.studentmanagement.errorhandler.StudentNotFoundException;
import com.stackroute.studentmanagement.model.Student;
import com.stackroute.studentmanagement.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest {

    private Student studentOne, studentTwo, studentThree, studentUpdated;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService service;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        studentOne = new Student(0, "Mihir", "mih@gmail.com", "XII","9999277301", LocalDate.now());
        studentTwo = new Student(0, "Shanmukha", "sha@gmail.com", "XI","8888211402", LocalDate.now());
        studentThree = new Student(0, "Neha", "neh@gmail.com","XI", "7777211311", LocalDate.now());
        studentUpdated = new Student(1, "Mihir", "mih@gmail.com", "XII","9955277301", LocalDate.now());
    }

    @AfterEach
    public void tearDown() {
        studentOne = null;
        studentTwo = null;
        studentThree = null;
    }

    @Test
    public void givenStudentDetailsWhenCreatedThenReturnSuccessCode() throws Exception {
        when(service.createStudent(any(Student.class))).thenReturn(studentOne);

        MvcResult mvcResult = mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentOne)))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), Student.class))
                .usingRecursiveComparison().isEqualTo(studentOne);
    }

    @Test
    public void givenExistingStudentDetailsWhenCreatedThenReturnConflictCode() throws Exception {
        when(service.createStudent(any(Student.class))).thenThrow(new StudentAlreadyExistsException());

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentOne)))
                .andExpect(status().isConflict());
    }

    @Test
    public void givenGradeWhenExistsThenReturnClaim() throws Exception {
        when(service.getStudentsByGrade(anyString())).thenReturn(List.of(studentTwo, studentThree));

        MvcResult mvcResult = mockMvc
                .perform(get("/api/students/grade/XI"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class))
                .hasSize(2);
    }

    @Test
    public void givenGradeWhenDoesNotExistsThenReturnNotFound() throws Exception {
        when(service.getStudentsByGrade(anyString())).thenThrow(new StudentNotFoundException());

        mockMvc.perform(get("/api/students/grade/IX"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void givenStudentIdWhenExistsThenReturnStudent() throws Exception {
        when(service.getStudentById(anyInt())).thenReturn(studentOne);

        MvcResult mvcResult = mockMvc
                .perform(get("/api/students/1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), Student.class))
                .usingRecursiveComparison().isEqualTo(studentOne);
    }

    @Test
    public void givenStudentIdWhenDoesNotExistsThenReturnNotFound() throws Exception {
        when(service.getStudentById(anyInt())).thenThrow(new StudentNotFoundException());

        mockMvc.perform(get("/api/students/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenAdmissionDateWhenExistsThenReturnStudents() throws Exception {
        when(service.getStudentsByDateOfAdmission(any(LocalDate.class)))
                .thenReturn(List.of(studentOne, studentTwo, studentThree));

        MvcResult mvcResult = mockMvc
                .perform(get("/api/students?date=2022-01-01"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class))
                .hasSize(3);
    }

    @Test
    public void givenStudentWhenStudentExistsThenReturnUpdatedStudent() throws Exception {
        when(service.updateStudent(any(Student.class))).thenReturn(studentUpdated);

        MvcResult settled = mockMvc.perform(put("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(studentUpdated)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mapper.readValue(settled.getResponse().getContentAsString(), Student.class))
                .usingRecursiveComparison().isEqualTo(studentUpdated);

    }
}
