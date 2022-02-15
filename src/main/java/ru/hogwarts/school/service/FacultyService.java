package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties;
    private Long facultyIdCounter = 0L;

    FacultyService() {
        this.faculties = new HashMap<>();
    }

    public ResponseEntity<Faculty> createFaculty(Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        faculty.setId(++facultyIdCounter);
        Faculty putFaculty = faculties.put(faculty.getId(), faculty);
        return ResponseEntity.ok(putFaculty);
    }

    public ResponseEntity<Faculty> getFacultyById(Long id) {
        Faculty faculty = faculties.get(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    public ResponseEntity<Faculty> updateFaculty(Long id, Faculty faculty) {
        if (!faculties.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        Faculty putFaculty = faculties.put(id, faculty);
        if (putFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(putFaculty);
    }

    public ResponseEntity<Faculty> removeFaculty(Long id) {
        Faculty faculty = faculties.remove(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    public ResponseEntity<List<Faculty>> getFacultyByColor(String color) {
        List<Faculty> facultyList = faculties.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
        if (facultyList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyList);
    }
}
