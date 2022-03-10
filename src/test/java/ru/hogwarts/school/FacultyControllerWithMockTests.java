package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerWithMockTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    private Faculty faculty;
    private JSONObject jsonObject;
    private List<Faculty> facultyList;

    @BeforeEach
    void setUpData() throws JSONException {
        faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("TestName");
        faculty.setColor("TestColor");

        jsonObject = new JSONObject();
        jsonObject.put("name", faculty.getName());
        jsonObject.put("color", faculty.getColor());

        Faculty faculty1 = new Faculty();
        faculty1.setId(2L);
        faculty1.setName("TestName");
        faculty1.setColor("TestColor");
        facultyList = List.of(faculty, faculty1);
    }

    @Test
    void createFacultyTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void getFacultyTest() throws Exception {
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void putFacultyTest() throws Exception {
        Faculty updateFaculty = new Faculty();
        updateFaculty.setId(1L);
        updateFaculty.setName("123");
        updateFaculty.setName("321");
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(updateFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updateFaculty.getId()))
                .andExpect(jsonPath("$.name").value(updateFaculty.getName()))
                .andExpect(jsonPath("$.color").value(updateFaculty.getColor()));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        doNothing().when(facultyRepository).deleteById(any());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + faculty.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void getAllFacultiesTest() throws Exception {
        when(facultyRepository.findAll()).thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(facultyList)));
    }

    @Test
    void getFacultiesByColorTest() throws Exception {

        when(facultyRepository.findByColor(faculty.getColor())).thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter/" + faculty.getColor())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(facultyList)));
    }

    @Test
    void getFacultyByNameOrColorTest() throws Exception {
        when(facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(any(), any())).thenReturn(facultyList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/filter/?str=" + faculty.getName())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(facultyList)));
    }
}

