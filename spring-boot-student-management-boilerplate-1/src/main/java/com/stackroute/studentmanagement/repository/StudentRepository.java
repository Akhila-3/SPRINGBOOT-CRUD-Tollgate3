package com.stackroute.studentmanagement.repository;

import com.stackroute.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
Add appropriate annotation/s and implement required interface/s to create a bean for handling database interactions using Data JPA
*/
@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    /* **USE FIND KEYWORD SYNTAX to define data access methods** */

    /*
         Define a method to get student by name and email, returning an Optional of student
    */
    // findStudentByNameAndEmail(String name, String email);

    /*
        Define a method to get all students by admission date
    */
    // findStudentsByDateOfAdmission(LocalDate dateOfAdmission);

    /*
        Define a method to get all students by grade
    */
    // findStudentsByGrade(String grade);
	
	Optional<Student> findStudentByNameAndEmail(String name, String email);
	List<Student> findStudentsByDateOfAdmission(LocalDate dateOfAdmission);
	List<Student> findStudentsByGrade(String grade);
	
}
