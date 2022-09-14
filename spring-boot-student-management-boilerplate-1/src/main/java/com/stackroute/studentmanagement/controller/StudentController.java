package com.stackroute.studentmanagement.controller;

import com.stackroute.studentmanagement.errorhandler.StudentAlreadyExistsException;
import com.stackroute.studentmanagement.errorhandler.StudentNotFoundException;
import com.stackroute.studentmanagement.model.Student;
import com.stackroute.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/*
Add appropriate annotation/s to create a bean for handling http requests for the rest api
 */

@RestController
public class StudentController {

    /*
    Inject the service object here
     */

    /*
    Create API endpoints as per the requirement given below
    */

    /*
     description  :  create new student info
     api endpoint : /api/students
     http request : POST
     request body : claim details
     success response:
            body: created claim    - http status: 201
     failure response:
            If a claim exists with same policy id and claim date
            body: failure message   - http status: 409
     */
    /*
     description  : get a student details by id
     api endpoint : /api/students/{id}
     http request : GET
     success response:
            body: Claim Details   - http status: 200
     failure response:
            If no claims exists for given criteria
            body: failure message - http status: 404
  */

    /*
     description  : get all student by grade
     api endpoint : /api/students/grade/{grade}
     http request : GET
     success response:
            body: list of claims    - http status: 200
     failure response:
            if no claims exists for given criteria
            body: failure message   - http status: 404
  */

    /*
     description  : get all students by admission date
     api endpoint : /api/students
     request query parameters: date
                               ex: <...pathurl>?date=yyyy-MM-dd
     http request : GET
     success response:
            body: list of claims    - http status: 200
     failure response:
            if no claims exists for given criteria
            body: failure message   - http status: 404
  */



    /*
     description  : update the student details by id
     api endpoint : /api/students
     http request : PUT
     request body : student object
     success response:
            body: updated claim    - http status: 200
     failure response:
            If no claims exists for given criteria
            body: failure message - http status: 404
  */

	@Autowired
	private StudentService service;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/students")
	public Student addStudents(@RequestBody Student student) throws StudentAlreadyExistsException {
		return service.createStudent(student);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/api/students/grade/{grade}")
	public List<Student> getStudentByGrade(@PathVariable String grade) throws StudentNotFoundException {
		return service.getStudentsByGrade(grade);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/api/students/{id}")
	public Student getStudentById(@PathVariable int id) throws StudentNotFoundException {
		return service.getStudentById(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/api/students")
	public List<Student> getStudentByAdmissionDate(@RequestParam("date") String date) throws StudentNotFoundException {
		LocalDate date2 = LocalDate.parse(date);
		return service.getStudentsByDateOfAdmission(date2);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/api/students")
	public Student updateClaim(@RequestBody Student student) throws StudentNotFoundException {
		return service.updateStudent(student);
	}


}
