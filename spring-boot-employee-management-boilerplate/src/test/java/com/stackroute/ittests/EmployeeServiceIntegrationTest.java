package com.stackroute.ittests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.EmployeemanagementApplication;
import com.stackroute.model.Employee;
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
        classes = EmployeemanagementApplication.class
)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceIntegrationTest {
    public static final String EMPLOYEES_BASE_URL = "/api/employee";
    private Employee employee1;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        employee1 = new Employee(1, "health", "gurgaon", LocalDate.now(), "HR", 30000);
    }

    @AfterEach
    public void tearDown() {
        employee1 = null;
    }

    @Test
    @Order(1)
    public void givenEmployeeDetailsThenCreateNewEmployee() throws Exception {
        mockMvc.perform(
                        post(EMPLOYEES_BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(employee1)))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @Order(2)
    public void givenSectorIdWhenEmployeeExistsThenReturnEmployees() throws Exception {
        mockMvc.perform(
                        get(EMPLOYEES_BASE_URL + "/sector/health"))
                .andExpect(status().isOk())
                .andReturn();

    }

    private String toJson(final Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

}
