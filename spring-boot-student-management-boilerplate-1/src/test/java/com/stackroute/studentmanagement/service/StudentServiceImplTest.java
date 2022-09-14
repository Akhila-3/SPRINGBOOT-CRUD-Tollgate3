package com.stackroute.studentmanagement.service;

import com.stackroute.studentmanagement.MethodExecutor;
import com.stackroute.studentmanagement.errorhandler.StudentAlreadyExistsException;
import com.stackroute.studentmanagement.errorhandler.StudentNotFoundException;
import com.stackroute.studentmanagement.model.Student;
import com.stackroute.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private StudentRepository repository;


    @InjectMocks
    private StudentServiceImpl service;

    private Student studentOne, studentTwo, studentThree,studentUpdated;

    @BeforeEach
    public void setUp() {
        studentOne = new Student(0, "Mihir", "mih@gmail.com", "XII","9999277301", LocalDate.now());
        studentTwo = new Student(0, "Shanmukha", "sha@gmail.com", "XI","8888211402", LocalDate.now());
        studentThree = new Student(0, "Neha", "neh@gmail.com","XI", "7777211311", LocalDate.now());
        studentUpdated = new Student(1, "Mihir", "mih@gmail.com", "XII","9955277301", LocalDate.now());

    }

    @AfterEach
    public void tearDown() {
        studentOne = null;
        studentTwo = null;
        studentThree = null;
    }

    @Test
    public void givenStudentWhenDoesNotExistThenReturnCreatedStudent() throws StudentAlreadyExistsException {
        when(MethodExecutor.executeMethod(repository, "findStudentByNameAndEmail",
                new Class[]{String.class, String.class},
                new Object[]{anyString(), any(String.class)}))
                .thenReturn(Optional.empty());
        when(MethodExecutor.executeMethod(repository, "save",
                new Class[]{Object.class},
                new Object[]{any(Student.class)}))
                .thenReturn(studentOne);

        Student student = service.createStudent(studentOne);
        assertEquals(student, studentOne);


    }


    @Test
    public void givenStudentWhenExistsThenThrowException() throws StudentAlreadyExistsException {
        when(MethodExecutor.executeMethod(repository, "findStudentByNameAndEmail",
                new Class[]{String.class, String.class},
                new Object[]{anyString(), any(String.class)}))
                .thenReturn(Optional.of(studentOne));

        assertThrows(StudentAlreadyExistsException.class, () -> service.createStudent(studentOne));
    }

    @Test
    public void givenStudentIdWhenExistsThenReturnStudent() throws StudentNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findById",
                new Class[]{Integer.class},
                new Object[]{anyInt()}))
                .thenReturn(Optional.of(studentOne));

        assertEquals(service.getStudentById(1), studentOne);
    }

    @Test
    public void givenStudentIdWhenDoesNotExistThenThrowException() throws StudentNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findById",
                new Class[]{Integer.class},
                new Object[]{anyInt()}))
                .thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> service.getStudentById(9));
    }


    @Test
    public void givenGradeWhenExistsThenReturnListOfStudents() throws StudentNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findStudentsByGrade",
                new Class[]{String.class},
                new Object[]{anyString()}))
                .thenReturn(List.of(studentTwo, studentThree));

        List<Student> studentsByGrade = service.getStudentsByGrade("XI");
        assertEquals(2, studentsByGrade.size());
    }

    @Test
    public void givenGradeWhenDoesNotExistThenThrowException() throws StudentNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findStudentsByGrade",
                new Class[]{String.class},
                new Object[]{anyString()}))
                .thenReturn(Collections.emptyList());

        assertThrows(StudentNotFoundException.class, () -> service.getStudentsByGrade("IX"));
    }


    @Test
    public void givenAdmissionDateWhenExistsThenReturnListOfStudents() throws StudentNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findStudentsByDateOfAdmission",
                new Class[]{LocalDate.class},
                new Object[]{any(LocalDate.class)}))
                .thenReturn(List.of(studentOne, studentTwo, studentThree));

        List<Student> claims = service.getStudentsByDateOfAdmission(LocalDate.now());
        assertEquals(3, claims.size());
    }

    @Test
    public void givenAdmissionDateWhenDoesNotExistThenThrowException() throws StudentNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findStudentsByDateOfAdmission",
                new Class[]{LocalDate.class},
                new Object[]{any(LocalDate.class)}))
                .thenReturn(Collections.emptyList());

        assertThrows(StudentNotFoundException.class, () -> service.getStudentsByDateOfAdmission(LocalDate.now().minus(Period.ofDays(1))));
    }

    @Test
    public void givenStudentIdWhenExistsThenReturnUpdatedStudent() throws StudentNotFoundException {
        when(MethodExecutor.executeMethod(repository, "findById",
                new Class[]{Integer.class},
                new Object[]{1}))
                .thenReturn(Optional.of(studentOne));
        when(MethodExecutor.executeMethod(repository, "save",
                new Class[]{Object.class},
                new Object[]{any(Student.class)}))
                .thenReturn(studentUpdated);

        assertEquals(studentUpdated,service.updateStudent(studentUpdated));

    }
}
