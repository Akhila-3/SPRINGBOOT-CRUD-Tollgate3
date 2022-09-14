# Objective

**Complete the given Spring Boot Rest Application for Employee Management**
- The Application in based on creation, managing and updating the Employee Details
- It uses Spring Data JPA to interact with h2 in-memory database

* Complete the code as per the instructions in this document and the source code
* Make sure all the provided tests are passing for completeness of submission
* Submit within the allotted time.

## Complete the following as per the requirement

### Model class - package com.stackroute.model

Complete the Employee model class by mapping Entity Relations using Spring Data JPA

### Repository Interface - package com.stackroute.repository

Complete EmployeeRepository interface for Data access using Spring Data JPA with H2 in memory Database

### Service Class - package com.stackroute.service

Implementing the methods specified in the EmployeeService interface. These methods invoke the appropriate repository methods for interacting with the database and throws appropriate custom exceptions.

### ErrorHandling - package com.stackroute.errorHandler

Implement Global Exception handling for handling Custom Exceptions

### Claims Rest APIs Controller - package com.stackroute.controller

Implement the handler methods in EmployeeController class as per the below REST endpoints definition

### API endpoints to be provided

| HTTP Method  | path                                     | Description                                            |
|--------------|------------------------------------------|--------------------------------------------------------|
| GET          | /api/employee/{id}                       | get an employee by employee id                         | 
| POST         | /api/employee                            | create a new employee                                  |
| GET          | /api/employee/sector/{id}                | Get all employees for given sector id                  |
| GET          | /api/employee?branch=xxx&date=yyyy-mm-dd | Get all employees for given branch Id and joining date |
| PUT          | /api/employee                            | update the employee department                         |


**IMP. NOTE :**

To complete the code
- Read the instructions given as comments in the class files
- Understand the failure messages when running the provided test cases   