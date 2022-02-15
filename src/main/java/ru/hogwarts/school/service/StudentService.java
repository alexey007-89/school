package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> students;
    private Long studentIdCounter = 0L;

    StudentService() {
        this.students = new HashMap<>();
    }

    public ResponseEntity<Student> createStudent(Student student) {
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        student.setId(++studentIdCounter);
        Student putStudent = students.put(student.getId(), student);
        return ResponseEntity.ok(putStudent);
    }

    public ResponseEntity<Student> getStudentById(Long id) {
        Student student = students.get(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    public ResponseEntity<Student> updateStudent(Long id, Student student) {
        if (!students.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        Student putStudent = students.put(id, student);
        if (putStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(putStudent);
    }

    public ResponseEntity<Student> removeStudent(Long id) {
        Student student = students.remove(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    public ResponseEntity<List<Student>> getStudentsByAge(int age) {
        List<Student> studentList = students.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
        if (studentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentList);
    }
}
