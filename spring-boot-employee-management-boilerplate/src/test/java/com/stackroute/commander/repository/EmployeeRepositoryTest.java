package com.stackroute.commander.repository;

import com.stackroute.commander.MethodExecutor;
import com.stackroute.model.Employee;
import com.stackroute.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee1, employee2, employee3;

    @BeforeEach
    public void  setUp() {
        employee1 = new Employee(1, "health", "gurgaon", LocalDate.now(), "HR", 30000);
        employee2 = new Employee(2, "e-commerce", "bangalore", LocalDate.now(), "Marketing", 34000);
        employee3 = new Employee(3, "e-commerce", "gurgaon", LocalDate.now(), "Finance", 10000);

        MethodExecutor.executeMethod(employeeRepository, "save",
                new Class[]{Object.class},
                new Object[]{employee1});
        MethodExecutor.executeMethod(employeeRepository, "save",
                new Class[]{Object.class},
                new Object[]{employee2});
        MethodExecutor.executeMethod(employeeRepository, "save",
                new Class[]{Object.class},
                new Object[]{employee3});
    }

    @Test
    public void givenEmployeeIdWhenNotExistsThenReturnEmptyOptional() {
        Optional<Employee> optionalEmployee = (Optional<Employee>)MethodExecutor.executeMethod(employeeRepository, "findById",
                new Class[]{Object.class},
                new Object[]{999});
        assertThat(optionalEmployee.isEmpty());
    }

    @Test
    public void givenSectorIdWhenExistsThenReturnOptionalWithEmployee() {
        List<Employee> employees = (List<Employee>) MethodExecutor.executeMethod(employeeRepository, "findEmployeesBySectorId",
                new Class[]{String.class},
                new Object[]{"e-commerce"});
        assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    public void givenSectorIdWhenNotExistsThenReturnEmptyList() {
        List<Employee> employees = (List<Employee>) MethodExecutor.executeMethod(employeeRepository, "findEmployeesBySectorId",
                new Class[]{String.class},
                new Object[]{"SEC311"});
        assertThat(employees.size()).isZero();
    }

    @Test
    public void givenSectorIdAndJoiningDateWhenExistsThenReturnOptionalWithEmployee() {
        Optional<Employee> optionalEmployee = (Optional<Employee>) MethodExecutor.executeMethod(employeeRepository, "findEmployeeBySectorIdAndEmployeeJoiningDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{"health", LocalDate.now()});
        assertThat(optionalEmployee).isPresent();
        assertThat(optionalEmployee.get().getBranchId()).isEqualTo("gurgaon");
    }

    @Test
    public void givenBranchIdAndJoiningDateWhenExistsThenReturnList() {
        List<Employee> employees = (List<Employee>) MethodExecutor.executeMethod(employeeRepository, "findEmployeesByBranchIdAndEmployeeJoiningDate",
                new Class[]{String.class, LocalDate.class},
                new Object[]{"gurgaon", LocalDate.now()});

        assertThat(employees.size()).isEqualTo(2);
    }

    @AfterEach
    public void tearDown() {
        MethodExecutor.executeMethod(employeeRepository, "deleteAll",
                new Class[]{},
                new Object[]{});

        employee1 = null;
        employee2 = null;
        employee3 = null;
    }

}
