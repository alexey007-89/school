package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAll() {
        return studentService.getAll();
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        return studentService.removeStudent(id);
    }

    @GetMapping("filter/{age}")
    public ResponseEntity<List<Student>> getStudents(@PathVariable int age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("filter")
    public ResponseEntity<List<Student>> getStudents(@RequestParam int minAge,
                                                     @RequestParam int maxAge) {
        return studentService.findByAgeBetween(minAge, maxAge);
    }

    @GetMapping("count-all")
    public ResponseEntity<Long> getAmountOfAllStudents() {
        return studentService.getAmountOfAllStudents();
    }

    @GetMapping("average-age")
    public ResponseEntity<Double> getAverageAgeOfAllStudents() {
        return studentService.getAverageAgeOfAllStudents();
    }

    @GetMapping("last5")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("names")
    public ResponseEntity<Collection<String>> getStudentsNamesStartingWith(@RequestParam String str) {
        return studentService.getStudentsNamesStartingWith(str);
    }

    @GetMapping("average-age-by-stream")
    public ResponseEntity<Double> getAverageAgeOfAllStudentsUsingStream() {
        return studentService.getAverageAgeOfAllStudentsUsingStream();
    }

    @GetMapping("name-list-by-thread")
    public ResponseEntity<Collection<String>> getNameListByThread() {
        return studentService.getNameListByThread();
    }

    @GetMapping("name-list-by-sync-thread")
    public ResponseEntity<Collection<String>> getNameListBySyncThread() {
        return studentService.getNameListBySyncThread();
    }
}
