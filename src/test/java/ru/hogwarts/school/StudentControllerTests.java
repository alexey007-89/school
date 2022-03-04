package ru.hogwarts.school;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTests {
    @LocalServerPort
    private int port;

    private String url;
    private Student student;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository repository;

    @BeforeEach
    void setUrl() {
        url ="http://localhost:"+port +"/student/";
    }

    @BeforeEach
    void setStudent() {
        student = new Student();
        student.setId(1L);
        student.setAge(20);
        student.setName("TestStudent");
    }
    @AfterEach
    void clearData() {
        repository.deleteAll();
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void testCreateStudent() throws Exception {

        ResponseEntity<Student> response = restTemplate.postForEntity(url, student, Student.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getId(), student.getId());
        assertEquals(response.getBody().getAge(), student.getAge());
        assertEquals(response.getBody().getName(), student.getName());
    }

    @Test
    public void testGetStudent() throws Exception {

        restTemplate.postForObject(url,student,Student.class);
        ResponseEntity<Student> response = restTemplate.getForEntity(url + 2,Student.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getId(), 2);
        assertEquals(response.getBody().getAge(), student.getAge());
        assertEquals(response.getBody().getName(), student.getName());
    }

    @Test
    public void testUpdateStudent() throws Exception {

        restTemplate.postForObject(url,student,Student.class);
        Student updatedStudent = new Student();
        updatedStudent.setId(3);
        updatedStudent.setAge(15);
        updatedStudent.setName("Test");
        HttpEntity<Student> entity = new HttpEntity<>(updatedStudent);
        ResponseEntity<Student> response = restTemplate.exchange(url , HttpMethod.PUT, entity, Student.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getId(), updatedStudent.getId());
        assertEquals(response.getBody().getAge(), updatedStudent.getAge());
        assertEquals(response.getBody().getName(), updatedStudent.getName());
    }

    @Test
    public void testDeleteStudent() throws Exception {

        restTemplate.postForObject(url,student,Student.class);
        ResponseEntity<Student> response = restTemplate.exchange(url + "{id}", HttpMethod.DELETE, null, Student.class, 4);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void testGetAllStudents() throws Exception {

        restTemplate.postForObject(url,student,Student.class);
        Student student1 = new Student();
        student1.setName("123");
        student1.setAge(15);
        restTemplate.postForObject(url,student1,Student.class);
        ResponseEntity<List<Student>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
        });
        List<Student> students = response.getBody();
        assertFalse(students.isEmpty());
        assertEquals(students.size(), 2);
        assertEquals(students.get(0).getName(), student.getName());
        assertEquals(students.get(0).getAge(), student.getAge());
        assertEquals(students.get(1).getAge(), student1.getAge());
        assertEquals(students.get(1).getName(), student1.getName());
    }

    @Test
    public void testGetStudentsByAge() throws Exception {

        int age = student.getAge();
        restTemplate.postForObject(url,student,Student.class);
        Student student1 = new Student();
        student1.setName("123");
        student1.setAge(15);
        restTemplate.postForObject(url,student1,Student.class);
        ResponseEntity<List<Student>> response = restTemplate.exchange(url + "filter/" + age, HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
        });
        List<Student> students = response.getBody();
        assertFalse(students.isEmpty());
        assertEquals(students.size(), 1);
        assertEquals(students.get(0).getName(), student.getName());
        assertEquals(students.get(0).getAge(), age);
    }

    @Test
    public void testGetStudentsByMinAndMaxAge() throws Exception {

        restTemplate.postForObject(url,student,Student.class);
        Student student1 = new Student();
        student1.setName("123");
        student1.setAge(15);
        restTemplate.postForObject(url,student1,Student.class);
        Student student2 = new Student();
        student2.setName("321");
        student2.setAge(25);
        restTemplate.postForObject(url,student2,Student.class);
        ResponseEntity<List<Student>> response = restTemplate.exchange(url + "filter/" + "?minAge=" +
                student1.getAge() + "&maxAge=" + student.getAge(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
        });
        List<Student> students = response.getBody();
        assertFalse(students.isEmpty());
        assertEquals(students.size(), 2);
        assertEquals(students.get(0).getName(), student.getName());
        assertEquals(students.get(0).getAge(), student.getAge());
        assertEquals(students.get(1).getName(), student1.getName());
        assertEquals(students.get(1).getAge(), student1.getAge());
    }

}
