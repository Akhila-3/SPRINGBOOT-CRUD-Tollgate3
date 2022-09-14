# Objective

**Complete the given Spring Boot Rest Application for Student Management**
- The Application in based on creation, managing and updating the Students info
- It uses Spring Data JPA to interact with h2 in-memory database

* Complete the code as per the instructions in this document and the source code
* Make sure all the provided tests are passing for completeness of submission
* Submit within the allotted time.

## Complete the following as per the requirement

### Model class - package com.stackroute.studentmanagement.model

Complete the Student model class by mapping Entity Relations using Spring Data JPA

### Repository Interface - package com.stackroute.studentmanagement.repository

Complete StudentRepository interface for Data access using Spring Data JPA with H2 in memory Database

### Service Class - package com.stackroute.studentmanagement.service

Implementing the methods specified in the StudentService interface. These methods invoke the appropriate repository methods for interacting with the database and throws appropriate custom exceptions.

### ErrorHandling - package com.stackroute.studentmanagement.errorHandler

Implement Global Exception handling for handling Custom Exceptions

### Claims Rest APIs Controller - package com.stackroute.studentmanagement.controller

Implement the handler methods in ClaimController class as per the below REST endpoints definition

### API endpoints to be provided

| HTTP Method  | path                          | Description                               |
|--------------|-------------------------------|-------------------------------------------|
| GET          | /api/students/{id}            | get a student by student id               | 
| POST         | /api/students                 | create a new student                      |
| GET          | /api/students/grade/{grade}   | Get all students for given grade          |
| GET          | /api/students?date=yyyy-mm-dd | Get all students for given admission date |
| PUT          | /api/students                 | update a student's details                |


**IMP. NOTE :**

To complete the code
- Read the instructions given as comments in the class files
- Understand the failure messages when running the provided test cases   