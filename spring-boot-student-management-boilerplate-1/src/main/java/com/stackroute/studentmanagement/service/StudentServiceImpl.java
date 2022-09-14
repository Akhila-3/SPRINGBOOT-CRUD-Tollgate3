package com.stackroute.studentmanagement.service;

import com.stackroute.studentmanagement.errorhandler.StudentAlreadyExistsException;
import com.stackroute.studentmanagement.errorhandler.StudentNotFoundException;
import com.stackroute.studentmanagement.model.Student;
import com.stackroute.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*
Add appropriate annotation/s to create a bean for service layer
Implement all the functionality based on the StudentService Interface
 */
@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepository repository;

	@Override
	public Student createStudent(Student newStudent) throws StudentAlreadyExistsException {
		Optional<Student> slist = repository.findStudentByNameAndEmail(newStudent.getName(), newStudent.getEmail());
		if (slist.isPresent()) {
			throw new StudentAlreadyExistsException();
		}
		return repository.save(newStudent);
	}

	@Override
	public Student getStudentById(Integer id) throws StudentNotFoundException {
		Optional<Student> optional =repository.findById(id);
		if(optional.isEmpty()) {
			throw new StudentNotFoundException();
		}
		return optional.get();
	}

	@Override
	public List<Student> getStudentsByGrade(String grade) throws StudentNotFoundException {
		List<Student> students = repository.findStudentsByGrade(grade);
		if(students.isEmpty()) {
			throw new StudentNotFoundException();
		}
		return students;
	}

	@Override
	public List<Student> getStudentsByDateOfAdmission(LocalDate dateOfAdmission) throws StudentNotFoundException {
		List<Student> students = repository.findStudentsByDateOfAdmission(dateOfAdmission);
		if(students.isEmpty()) {
			throw new StudentNotFoundException();
		}
		return students;
	}

	@Override
	public Student updateStudent(Student student) throws StudentNotFoundException {
		Optional<Student> optional = repository.findById(student.getStudentId());
		if(optional.isEmpty()) {
			throw new StudentNotFoundException();
		}
		Student student2 = optional.get();
		return repository.save(student2);
	}

    /*
      Inject the repository bean
     */

}
