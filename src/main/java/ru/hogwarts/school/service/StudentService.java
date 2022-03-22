package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<Student> createStudent(Student student) {
        logger.info("Was invoked method to create student");
        if (student == null) {
            logger.error("Can't create student, when student is null");
            return ResponseEntity.badRequest().build();
        }
        logger.debug("Was created {}",student);
        return ResponseEntity.ok(studentRepository.save(student));
    }

    public ResponseEntity<Student> getStudentById(Long id) {
        logger.info("Was invoked method to find student by id={}", id);
        Optional<Student> byId = studentRepository.findById(id);
        if (byId.isEmpty()) {
            logger.error("There is no student with id={}", id);
            return ResponseEntity.notFound().build();
        }
        logger.debug("Student was founded by id={}", id);
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<Student> updateStudent(Student student) {
        logger.info("Was invoked method to update student");
        if (student == null) {
            logger.error("Can't update student, when student is null");
            return ResponseEntity.badRequest().build();
        }
        if (studentRepository.findById(student.getId()).isEmpty()) {
            logger.error("There is no student with your id");
            return ResponseEntity.notFound().build();
        }
        logger.debug("{} was updated", student);
        return ResponseEntity.ok(studentRepository.save(student));
    }

    public ResponseEntity<Student> removeStudent(Long id) {
        logger.info("Was invoked method to delete student");
        studentRepository.deleteById(id);
        logger.debug("Student was deleted by id={}", id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Student>> getStudentsByAge(int age) {
        logger.info("Was invoked method to find student by age");
        List<Student> studentList = studentRepository.findByAge(age);
        if (studentList.isEmpty()) {
            logger.error("There is no student with age={}", age);
            return ResponseEntity.notFound().build();
        }
        logger.debug("Founded students by age={}: {}", age, studentList);
        return ResponseEntity.ok(studentList);
    }

    public ResponseEntity<Collection<Student>> getAll() {
        logger.info("Was invoked method to find all students");
        Collection<Student> studentList = studentRepository.findAll();
        if (studentList.isEmpty()) {
            logger.error("There is no students at all");
            return ResponseEntity.notFound().build();
        }
        logger.debug("List of all students: {}", studentList);
        return ResponseEntity.ok(studentList);
    }

    public ResponseEntity<List<Student>> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method to find students with age between {} and {}", minAge, maxAge);
        List<Student> studentList = studentRepository.findByAgeBetween(minAge, maxAge);
        if (studentList.isEmpty()) {
            logger.error("There is no students with age between {} and {}", minAge, maxAge);
            return ResponseEntity.notFound().build();
        }
        logger.debug("List of with age between {} and {} students: {}", minAge, maxAge, studentList);
        return ResponseEntity.ok(studentList);
    }

    public ResponseEntity<Long> getAmountOfAllStudents() {
        logger.info("Was invoked method to get amount off all students");
        Long amountOfAllStudents = studentRepository.getAmountOfAllStudents();
        logger.debug("The amount off all students is {}", amountOfAllStudents);
        return ResponseEntity.ok(amountOfAllStudents);
    }

    public ResponseEntity<Double> getAverageAgeOfAllStudents() {
        logger.info("Was invoked method to get average age off all students");
        Double averageAgeOfAllStudents = studentRepository.getAverageAgeOfAllStudents();
        logger.debug("The average age off all students is {}", averageAgeOfAllStudents);
        return ResponseEntity.ok(averageAgeOfAllStudents);
    }

    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        logger.info("Was invoked method to get last 5 added students");
        Long amount = studentRepository.getAmountOfAllStudents();
        Collection<Student> studentList = studentRepository.getLastFiveStudents(amount);
        if (studentList.isEmpty()) {
            logger.error("The list of students is empty");
            return ResponseEntity.notFound().build();
        }
        logger.debug("List of last 5 added students: {}", studentList);
        return ResponseEntity.ok(studentList);
    }
}
