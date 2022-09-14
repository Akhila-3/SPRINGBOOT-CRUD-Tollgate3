package com.stackroute.service;

import com.stackroute.errorhandler.EmployeeExistsException;
import com.stackroute.errorhandler.EmployeeNotFoundException;
import com.stackroute.model.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee) throws EmployeeExistsException;

    Employee getEmployeeByEmployeeId(Integer employeeId) throws EmployeeNotFoundException;

    List<Employee> getEmployeesBySectorId(String sectorId) throws EmployeeNotFoundException;

    List<Employee>getEmployeesByBranchIdAndJoiningDate(String branchId, LocalDate joiningDate) throws EmployeeNotFoundException;

    Employee updateEmployeeDepartment(int employeeId, String department) throws EmployeeNotFoundException;
}
