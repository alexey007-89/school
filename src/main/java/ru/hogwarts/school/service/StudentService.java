package ru.hogwarts.school.service;

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

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<Student> createStudent(Student student) {
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentRepository.save(student));
    }

    public ResponseEntity<Student> getStudentById(Long id) {
        Optional<Student> byId = studentRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<Student> updateStudent(Student student) {
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        if (studentRepository.findById(student.getId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentRepository.save(student));
    }

    public ResponseEntity<Student> removeStudent(Long id) {
        studentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Student>> getStudentsByAge(int age) {
        List<Student> studentList = studentRepository.findByAge(age);
        if (studentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentList);
    }

    public ResponseEntity<Collection<Student>> getAll() {
        Collection<Student> studentList = studentRepository.findAll();
        if (studentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentList);
    }

    public ResponseEntity<List<Student>> findByAgeBetween(int minAge, int maxAge) {
        List<Student> studentList = studentRepository.findByAgeBetween(minAge, maxAge);
        if (studentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentList);
    }
}
