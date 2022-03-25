package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public ResponseEntity<Faculty> createFaculty(Faculty faculty) {
        logger.info("Was invoked method to create faculty");
        if (faculty == null) {
            logger.error("Can't create faculty, when faculty is null");
            return ResponseEntity.badRequest().build();
        }
        logger.debug("Was created {}",faculty);
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    public ResponseEntity<Faculty> getFacultyById(Long id) {
        logger.info("Was invoked method to find faculty by id={}", id);
        Optional<Faculty> byId = facultyRepository.findById(id);
        if (byId.isEmpty()) {
            logger.error("There is no faculty with id={}", id);
            return ResponseEntity.notFound().build();
        }
        logger.debug("Faculty was founded by id={}", id);
        return ResponseEntity.ok(byId.get());
    }

    public ResponseEntity<Faculty> updateFaculty(Faculty faculty) {
        logger.info("Was invoked method to update faculty");
        if (faculty == null) {
            logger.error("Can't update faculty, when faculty is null");
            return ResponseEntity.badRequest().build();
        }
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            logger.error("There is no faculty with your id");
            return ResponseEntity.notFound().build();
        }
        logger.debug("{} was updated", faculty);
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    public ResponseEntity<Faculty> removeFaculty(Long id) {
        logger.info("Was invoked method to delete faculty");
        facultyRepository.deleteById(id);
        logger.debug("Faculty was deleted by id={}", id);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<Faculty>> getFacultiesByColor(String color) {
        logger.info("Was invoked method to find faculty by color");
        List<Faculty> facultyList = facultyRepository.findByColor(color);
        if (facultyList.isEmpty()) {
            logger.error("There is no faculty with color={}", color);
            return ResponseEntity.notFound().build();
        }
        logger.debug("Founded faculties by color={}: {}", color, facultyList);
        return ResponseEntity.ok(facultyList);
    }

    public ResponseEntity<Collection<Faculty>> getAll() {
        logger.info("Was invoked method to find all faculties");
        Collection<Faculty> facultyList = facultyRepository.findAll();
        if (facultyList.isEmpty()) {
            logger.error("There is no faculties at all");
            return ResponseEntity.notFound().build();
        }
        logger.debug("List of all faculties: {}", facultyList);
        return ResponseEntity.ok(facultyList);
    }

    public ResponseEntity<List<Faculty>> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(String str) {
        logger.info("Was invoked method to find all faculties with name or color containing {} wit ignoring case", str);
        List<Faculty> facultyList = facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(str, str);
        if (facultyList.isEmpty()) {
            logger.error("There is no faculties with name or color containing {} wit ignoring case", str);
            return ResponseEntity.notFound().build();
        }
        logger.debug("List of faculties with name or color containing {} wit ignoring case: {}", str, facultyList);
        return ResponseEntity.ok(facultyList);
    }

    public ResponseEntity<String> getFacultyNameWithMaxLength() {
        logger.info("Was invoked method to find faculty name with max length");
        Collection<Faculty> facultyList = facultyRepository.findAll();
        Optional<String> maxFacultyName = facultyList.stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length));
        if (maxFacultyName.isEmpty()) {
            logger.error("There is no faculties at all");
            return ResponseEntity.notFound().build();
        } else {
            logger.debug("Faculty name with max length: {}", maxFacultyName.get());
            return ResponseEntity.ok(maxFacultyName.get());
        }
    }
}
