package com.stackroute.controller;

/*
Add appropriate annotation/s to create a bean for handling http requests for the rest api
 */

import com.stackroute.dto.EmployeeDepartmentDto;
import com.stackroute.errorhandler.EmployeeExistsException;
import com.stackroute.errorhandler.EmployeeNotFoundException;
import com.stackroute.model.Employee;
import com.stackroute.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    /*
        Inject the service object here
    */
    @Autowired
    private EmployeeService employeeService;

    /*
    Create API endpoints as per the requirement given below
    */

    /*
     description  :  create new employee
     api endpoint : /api/employee
     http request : POST
     request body : employee details
     success response:
            body: created employee    - http status: 201
     failure response:
            If an employee exists with same employee id and joining date
            body: failure message   - http status: 409
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) throws  EmployeeExistsException {
        return employeeService.createEmployee(employee);
    }

    /*
     description  : get an employee by employee id
     api endpoint : /api/employee/{id}
     http request : GET
     success response:
            body: Employee Details   - http status: 200
     failure response:
            If no employee exists for given criteria
            body: failure message - http status: 404
  */
    @GetMapping("/{id}")
    public Employee getEployeeById(@PathVariable Integer id) throws EmployeeNotFoundException {
        return employeeService.getEmployeeByEmployeeId(id);
    }

    /*
     description  : get all employees by sector id
     api endpoint : /api/employee/sector/{sectorId}
     http request : GET
     success response:
            body: list of employees    - http status: 200
     failure response:
            if no employee exists for given criteria
            body: failure message   - http status: 404
  */
    @GetMapping("sector/{sectorId}")
    public List<Employee> getBysectorID(@PathVariable String sectorId) throws  EmployeeNotFoundException {
        return employeeService.getEmployeesBySectorId(sectorId);
    }

    /*
     description  : get all employees by branch Id and joining date
     api endpoint : /api/employee
     request query parameters: branch and date
                               ex: <...pathurl>?branch=xxx&date=yyyy-MM-dd
     http request : GET
     success response:
            body: list of employees    - http status: 200
     failure response:
            if no employees exists for given criteria
            body: failure message   - http status: 404
  */
    @GetMapping
    public List<Employee> getByDateOfjoining(@RequestParam("branch") String branchId, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate joiningDate) throws EmployeeNotFoundException {
        return employeeService.getEmployeesByBranchIdAndJoiningDate(branchId,joiningDate);
    }

    /*
     description  : update the department name for given employee id
     api endpoint : /api/employee
     http request : PUT
     request body : employee id and new department name
     success response:
            body: updated employee    - http status: 200
     failure response:
            If no employee exists for given criteria
            body: failure message - http status: 404
  */
    @PutMapping
    public Employee updateEmployeeDepartment(@RequestBody EmployeeDepartmentDto employeeDepartmentDto) throws EmployeeNotFoundException {
        return employeeService.updateEmployeeDepartment(employeeDepartmentDto.getEmployeeId(), employeeDepartmentDto.getDepartmentName());
    }

}
