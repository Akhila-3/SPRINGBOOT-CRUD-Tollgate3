# Objective

**Complete the given Spring Boot Rest Application for Library Management System**
  - The Application in based on creation, managing and updating the Library books
  - It uses Spring Data JPA to interact with h2 in-memory database 

 * Complete the code as per the instructions in this document and the source code  
 * Make sure all the provided tests are passing for completeness of submission
 * Submit within the allotted time.

## Complete the following as per the requirement

### Model class - package com.library.bookservice.model 

 Complete the Book model class by mapping Entity Relations using Spring Data JPA

### Repository Interface - package com.library.bookservice.repository

Complete BookRepository interface for Data access using Spring Data JPA with H2 in memory Database

### Service Class - package com.library.bookservice.service

Implementing the methods specified in the BookService interface. These methods invoke the appropriate repository methods for interacting with the database and throws appropriate custom exceptions.

### ErrorHandling - package com.library.bookservice.errorHandler

Implement Global Exception handling for handling Custom Exceptions

### Books Rest APIs Controller - package com.library.bookservice.controller

Implement the handler methods in BookController class as per the below REST endpoints definition

### API endpoints to be provided

| HTTP Method  | path                                   | Description                                                |
|--------------|----------------------------------------|------------------------------------------------------------|
| GET          | /api/books/{id}                        | get an book by book id                                     | 
| POST         | /api/books                             | add a new book                                             |
| GET          | /api/books/author/{id}                 | Get all books for given author id                          |
| GET          | /api/books?library=xxx&date=yyyy-mm-dd | Get all books for given library Id and book published date |
| PUT          | /api/books                             | update the book status                                     |


**IMP. NOTE :** 

To complete the code
- Read the instructions given as comments in the class files
- Understand the failure messages when running the provided test cases   