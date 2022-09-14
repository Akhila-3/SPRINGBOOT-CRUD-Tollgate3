package com.stackroute.studentmanagement.service;

import com.stackroute.studentmanagement.errorhandler.StudentAlreadyExistsException;
import com.stackroute.studentmanagement.errorhandler.StudentNotFoundException;
import com.stackroute.studentmanagement.model.Student;

import java.time.LocalDate;
import java.util.List;

public interface StudentService {

    Student createStudent(Student newStudent) throws StudentAlreadyExistsException;

    Student getStudentById(Integer id) throws StudentNotFoundException;

    List<Student> getStudentsByGrade(String grade) throws StudentNotFoundException;

    List<Student> getStudentsByDateOfAdmission(LocalDate dateOfAdmission) throws StudentNotFoundException;

    Student updateStudent(Student student) throws StudentNotFoundException;
}
