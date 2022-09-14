package com.stackroute.commander.service;

import com.stackroute.commander.MethodExecutor;
import com.stackroute.errorhandler.EmployeeExistsException;
import com.stackroute.errorhandler.EmployeeNotFoundException;
import com.stackroute.model.Employee;
import com.stackroute.repository.EmployeeRepository;
import com.stackroute.service.EmployeeService;
import com.stackroute.service.EmployeeServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee1, employee2, employee3, employee4;

    @BeforeEach
    public void  setUp() {
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
    public void givenEmployeeWhenDoesNotExistThenReturnCreatedEmployee() throws EmployeeExistsException {
        when(MethodExecutor.executeMethod(employeeRepository, "findEmployeeBySectorIdAndEmployeeJoiningDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(Optional.empty());
        when(MethodExecutor.executeMethod(employeeRepository, "save",
                new Class[]{Object.class},
                new Object[]{any(Employee.class)}))
                .thenReturn(employee1);

        Employee employee = employeeService.createEmployee(employee1);
        assertEquals(employee, employee1);
    }

    @Test
    public void givenEmployeeWhenExistsThenThrowException() throws EmployeeExistsException{
        when(MethodExecutor.executeMethod(employeeRepository, "findEmployeeBySectorIdAndEmployeeJoiningDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(Optional.of(employee1));

        assertThrows(EmployeeExistsException.class, () -> employeeService.createEmployee(employee1));
    }

    @Test
    public void givenEmployeeIdWhenExistsThenReturnEmployee() throws EmployeeNotFoundException {
        when(MethodExecutor.executeMethod(employeeRepository, "findById",
                new Class[]{Integer.class},
                new Object[]{anyInt()}))
                .thenReturn(Optional.of(employee1));

        assertEquals(employeeService.getEmployeeByEmployeeId(1), employee1);
    }

    @Test
    public void givenEmployeeIdWhenDoesNotExistThenThrowException() throws EmployeeNotFoundException{
        when(MethodExecutor.executeMethod(employeeRepository, "findById",
                new Class[]{Integer.class},
                new Object[]{anyInt()}))
                .thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeByEmployeeId(8));
    }

    @Test
    public void givenSectorIdWhenExistsThenReturnListOfEmployee() throws EmployeeNotFoundException {
        when(MethodExecutor.executeMethod(employeeRepository, "findEmployeesBySectorId",
                new Class[]{String.class},
                new Object[]{anyString()}))
                .thenReturn(List.of(employee2, employee3));

        List<Employee> employeesBySectorId = employeeService.getEmployeesBySectorId("health");
        assertEquals(2, employeesBySectorId.size());
    }

    @Test
    public void givenSectorIdWhenDoesNotExistThenThrowException() throws EmployeeNotFoundException {
        when(MethodExecutor.executeMethod(employeeRepository, "findEmployeesBySectorId",
                new Class[]{String.class},
                new Object[]{anyString()}))
                .thenReturn(Collections.emptyList());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeesBySectorId("Finance"));
    }

    @Test
    public void givenBranchIdAndJoiningDateWhenExistsThenReturnListOfEmployee() throws EmployeeNotFoundException{
        when(MethodExecutor.executeMethod(employeeRepository, "findEmployeesByBranchIdAndEmployeeJoiningDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(List.of(employee2, employee3));

        List<Employee> claims = employeeService.getEmployeesByBranchIdAndJoiningDate("gurgaon", LocalDate.now());
        assertEquals(2, claims.size());
    }

    @Test
    public void givenBranchIdAndJoiningDateWhenDoesNotExistThenThrowException() throws EmployeeNotFoundException {
        when(MethodExecutor.executeMethod(employeeRepository, "findEmployeesByBranchIdAndEmployeeJoiningDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{anyString(), any(LocalDate.class)}))
                .thenReturn(Collections.emptyList());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeesByBranchIdAndJoiningDate("hyderabad", LocalDate.now()));
    }

    @Test
    public void givenEmployeeIdWhenExistsThenReturnUpdatedEmployee() throws EmployeeNotFoundException {
        when(MethodExecutor.executeMethod(employeeRepository, "findById",
                new Class[]{Integer.class},
                new Object[]{1}))
                .thenReturn(Optional.of(employee1));
        when(MethodExecutor.executeMethod(employeeRepository, "save",
                new Class[]{Object.class},
                new Object[]{any(Employee.class)}))
                .thenReturn(employee4);

        assertEquals(employee4,employeeService.updateEmployeeDepartment(1, "Finance"));
    }

}
