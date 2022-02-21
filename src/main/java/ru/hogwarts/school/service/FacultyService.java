package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public ResponseEntity<Faculty> createFaculty(Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    public ResponseEntity<Faculty> getFacultyById(Long id) {
        Optional<Faculty> byId = facultyRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<Faculty> updateFaculty(Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.badRequest().build();
        }
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    public ResponseEntity<Faculty> removeFaculty(Long id) {
        facultyRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Faculty>> getFacultiesByColor(String color) {
        List<Faculty> facultyList = facultyRepository.findByColor(color);
        if (facultyList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyList);
    }

    public ResponseEntity<Collection<Faculty>> getAll() {
        Collection<Faculty> facultyList = facultyRepository.findAll();
        if (facultyList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyList);
    }

    public ResponseEntity<List<Faculty>>
    findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String str) {
        List<Faculty> facultyList = facultyRepository
                .findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(str,str);
        if (facultyList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyList);
    }
}
