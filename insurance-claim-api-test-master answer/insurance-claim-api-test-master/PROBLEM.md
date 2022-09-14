# Objective

**Complete the given Spring Boot Rest Application for Insurance Claim Management**
  - The Application in based on creation, managing and updating the Medical Insurance Claims
  - It uses Spring Data JPA to interact with h2 in-memory database 

 * Complete the code as per the instructions in this document and the source code  
 * Make sure all the provided tests are passing for completeness of submission
 * Submit within the allotted time.

## Complete the following as per the requirement

### Model class - package com.insurance.claimservice.model 

 Complete the Claim model class by mapping Entity Relations using Spring Data JPA

### Repository Interface - package com.insurance.claimservice.repository

Complete ClaimRepository interface for Data access using Spring Data JPA with H2 in memory Database

### Service Class - package com.insurance.claimservice.service

Implementing the methods specified in the ClaimService interface. These methods invoke the appropriate repository methods for interacting with the database and throws appropriate custom exceptions.

### ErrorHandling - package com.insurance.claimservice.errorHandler

Implement Global Exception handling for handling Custom Exceptions

### Claims Rest APIs Controller - package com.insurance.claimservice.controller

Implement the handler methods in ClaimController class as per the below REST endpoints definition

### API endpoints to be provided

| HTTP Method  | path                                     | Description                                         |
|--------------|------------------------------------------|-----------------------------------------------------|
| GET          | /api/claims/{id}                         | get an claim by claim id                            | 
| POST         | /api/claims                              | create a new claim                                  |
| GET          | /api/claims/policy/{id}                  | Get all claims for given policy id                  |
| GET          | /api/claims?hospital=xxx&date=yyyy-mm-dd | Get all claims for given hospital Id and claim date |
| PUT          | /api/claims                              | update the claim status                             |


**IMP. NOTE :** 

To complete the code
- Read the instructions given as comments in the class files
- Understand the failure messages when running the provided test cases   