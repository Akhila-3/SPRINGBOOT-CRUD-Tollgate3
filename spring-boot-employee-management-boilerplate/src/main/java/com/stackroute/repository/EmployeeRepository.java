package com.stackroute.repository;

import com.stackroute.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
Add appropriate annotation/s and implement required interface/s to create a bean for handling database interactions using Data JPA
*/

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    /* **USE FIND KEYWORD SYNTAX to define data access methods** */

    /*
         Define a method to get employee by sectorId and joining date, returning an Optional of employee
    */
    // findEmployeeBySectorIdAndEmployeeJoiningDate(String sectorId, LocalDate employeeJoiningDate);
    Optional<Employee> findEmployeeBySectorIdAndEmployeeJoiningDate(String sectorId, LocalDate employeeJoiningDate);
    /*
        Define a method to get all employees by sector id, returning list of employees
    */
    // findEmployeesBySectorId(String sectorId);
    List<Employee> findEmployeesBySectorId(String sectorId);
    /*
        Define a method to get all employees by branch id and joining date, returning list of employees
    */

    // findEmployeesByBranchIdAndEmployeeJoiningDate(String branchId, LocalDate employeeJoiningDate);
    List<Employee> findEmployeesByBranchIdAndEmployeeJoiningDate(String branchId, LocalDate employeeJoiningDate);
}