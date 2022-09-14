package com.stackroute.commander.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.controller.EmployeeController;
import com.stackroute.dto.EmployeeDepartmentDto;
import com.stackroute.errorhandler.EmployeeNotFoundException;
import com.stackroute.model.Employee;
import com.stackroute.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {
    private Employee employee1, employee2, employee3, employee4;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        employee1 = new Employee(1, "health", "gurgaon", LocalDate.now(), "HR", 30000);
        employee2 = new Employee(2, "e-commerce", "bangalore", LocalDate.now(), "HR", 34000);
        employee3 = new Employee(3, "e-commerce", "gurgaon", LocalDate.now(), "HR", 10000);
        employee4 = new Employee(4, "health", "gurgaon", LocalDate.now(), "Finance", 10000);
    }

    @AfterEach
    public void tearDown() {
        employee1 = null;
        employee2 = null;
        employee3 = null;
    }

    @Test
    public void givenEmployeeDetailsWhenCreatedThenReturnSuccessCode() throws Exception{
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee1);

        MvcResult mvcResult = mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(employee1)))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class))
                .usingRecursiveComparison().isEqualTo(employee1);
    }

    @Test
    public void givenExistingEmployeeDetailsWhenCreatedThenReturnConflictCode() throws Exception{
        when(employeeService.getEmployeesBySectorId(anyString())).thenReturn(List.of(employee1, employee3));

        MvcResult mvcResult = mockMvc
                .perform(get("/api/employee/sector/health"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class))
                .hasSize(2);
    }

    @Test
    public void givenSectorIdWhenDoesNotExistsThenReturnNotFound() throws Exception{
        when(employeeService.getEmployeesBySectorId(anyString())).thenThrow(new EmployeeNotFoundException());

        mockMvc.perform(get("/api/employee/sector/technology"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenEmployeeIdWhenExistsThenReturnEmployee() throws Exception{
        when(employeeService.getEmployeeByEmployeeId(anyInt())).thenReturn(employee1);

        MvcResult mvcResult = mockMvc
                .perform(get("/api/employee/1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), Employee.class))
                .usingRecursiveComparison().isEqualTo(employee1);
    }

    @Test
    public void givenEmployeeIdWhenDoesNotExistsThenReturnNotFound() throws Exception{
        when(employeeService.getEmployeeByEmployeeId(anyInt())).thenThrow(new EmployeeNotFoundException());

        mockMvc.perform(get("/api/employee/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenBranchAndJoiningDateWhenExistsThenReturnEmployees() throws Exception{
        when(employeeService.getEmployeesByBranchIdAndJoiningDate(anyString(), any(LocalDate.class)))
                .thenReturn(List.of(employee1, employee3));

        MvcResult mvcResult = mockMvc
                .perform(get("/api/employee?branch=gurgaon&date=2022-04-27"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class))
                .hasSize(2);
    }

    @Test
    public void givenDepartmentWhenEmployeeExistsThenReturnUpdatedEmployee() throws Exception {
        when(employeeService.updateEmployeeDepartment(anyInt(), anyString())).thenReturn(employee4);

        MvcResult settled = mockMvc.perform(put("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new EmployeeDepartmentDto(1, "Finance"))))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mapper.readValue(settled.getResponse().getContentAsString(), Employee.class))
                .usingRecursiveComparison().isEqualTo(employee4);
    }


}
