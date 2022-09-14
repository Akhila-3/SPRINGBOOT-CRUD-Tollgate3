package com.stackroute.studentmanagement.commander.repository;

import com.stackroute.studentmanagement.MethodExecutor;
import com.stackroute.studentmanagement.model.Student;
import com.stackroute.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    private Student studentOne, studentTwo, studentThree;

    @BeforeEach
    public void setUp() {
        studentOne = new Student(0, "Mihir", "mih@gmail.com", "XII","9999277301", LocalDate.now());
        studentTwo = new Student(0, "Shanmukha", "sha@gmail.com", "XI","8888211402", LocalDate.now());
        studentThree = new Student(0, "Neha", "neh@gmail.com","XI", "7777211311", LocalDate.now());

        MethodExecutor.executeMethod(studentRepository, "save",
                new Class[]{Object.class},
                new Object[]{studentOne});
        MethodExecutor.executeMethod(studentRepository, "save",
                new Class[]{Object.class},
                new Object[]{studentTwo});
        MethodExecutor.executeMethod(studentRepository, "save",
                new Class[]{Object.class},
                new Object[]{studentThree});
    }

    @Test
    public void givenStudentIdWhenNotExistsThenReturnEmptyOptional() {
        Optional<Student> optionalStudent = (Optional<Student>) MethodExecutor.executeMethod(studentRepository, "findById",
                new Class[]{Object.class},
                new Object[]{999});
        assertThat(optionalStudent).isEmpty();
    }

    @Test
    public void givenGradeWhenExistsThenReturnOptionalWithStudent() {
        List<Student> students = (List<Student>) MethodExecutor.executeMethod(studentRepository, "findStudentsByGrade",
                new Class[]{String.class},
                new Object[]{"XI"});
        assertThat(students.size()).isEqualTo(2);
    }

    @Test
    public void givenGradeWhenNotExistsThenReturnEmptyList() {

        List<Student> students = (List<Student>) MethodExecutor.executeMethod(studentRepository, "findStudentsByGrade",
                new Class[]{String.class},
                new Object[]{"IX"});
        assertThat(students.size()).isZero();

    }

    @Test
    public void givenNameAndEmailWhenExistsThenReturnOptionalWithStudent() {
        Optional<Student> optionalStudent = (Optional<Student>) MethodExecutor.executeMethod(studentRepository, "findStudentByNameAndEmail",
                new Class[]{String.class, String.class},
                new Object[]{"Mihir", "mih@gmail.com"});
        assertThat(optionalStudent).isPresent();
        assertThat(optionalStudent.get().getGrade()).isEqualTo("XII");
    }

    @Test
    public void givenAdmissionDateWhenExistsThenReturnList() {
        List<Student> students = (List<Student>) MethodExecutor.executeMethod(studentRepository, "findStudentsByDateOfAdmission",
                new Class[]{LocalDate.class},
                new Object[]{LocalDate.now()});

        assertThat(students.size()).isEqualTo(3);

    }

    @AfterEach
    public void tearDown() {
        MethodExecutor.executeMethod(studentRepository, "deleteAll",
                new Class[]{},
                new Object[]{});
        studentOne = null;
        studentTwo = null;
        studentThree = null;

    }
}
