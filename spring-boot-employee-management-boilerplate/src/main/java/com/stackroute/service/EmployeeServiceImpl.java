package com.stackroute.service;

import com.stackroute.errorhandler.EmployeeExistsException;
import com.stackroute.errorhandler.EmployeeNotFoundException;
import com.stackroute.model.Employee;
import com.stackroute.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
Add appropriate annotation/s to create a bean for service layer
Implement all the functionality based on the EmployeeService Interface
*/

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository repository;

    @Override
    public Employee createEmployee(Employee employee) throws EmployeeExistsException {
        Optional<Employee> studentOptional = repository.findEmployeeBySectorIdAndEmployeeJoiningDate(employee.getSectorId(),employee.getEmployeeJoiningDate());
        if(studentOptional.isPresent()){
            throw new EmployeeExistsException();
        }

        return repository.save(employee);
    }

    @Override
    public Employee getEmployeeByEmployeeId(Integer employeeId) throws EmployeeNotFoundException {
        Optional<Employee> studentOptional = repository.findById(employeeId);
        return studentOptional.orElseThrow(EmployeeNotFoundException::new);

    }

    @Override
    public List<Employee> getEmployeesBySectorId(String sectorId) throws EmployeeNotFoundException {
        List<Employee> employeesBySectorId = repository.findEmployeesBySectorId(sectorId);
        if(employeesBySectorId.isEmpty()){
            throw new EmployeeNotFoundException();
        }
        return employeesBySectorId;
    }

    @Override
    public List<Employee> getEmployeesByBranchIdAndJoiningDate(String branchId, LocalDate joiningDate) throws EmployeeNotFoundException {
        List<Employee> employeesByBranchIdAndEmployeeJoiningDate = repository.findEmployeesByBranchIdAndEmployeeJoiningDate(branchId,joiningDate);
        if(employeesByBranchIdAndEmployeeJoiningDate.isEmpty()){
            throw new EmployeeNotFoundException();
        }
        return employeesByBranchIdAndEmployeeJoiningDate;

    }

    @Override
    public Employee updateEmployeeDepartment(int employeeId, String department) throws EmployeeNotFoundException {
        Optional<Employee> optionalBook = repository.findById(employeeId);
        if(!optionalBook.isPresent()){
            throw new EmployeeNotFoundException();
        }
        Employee employee = optionalBook.get();
        employee.setDepartmentName(department);
        return repository.save(employee);
    }
    /*
      Inject the repository bean
     */

}