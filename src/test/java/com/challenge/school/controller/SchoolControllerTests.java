package com.challenge.school.controller;

import com.challenge.school.model.StudentModel;
import com.challenge.school.service.MarksService;
import com.challenge.school.service.StudentsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SchoolController.class)
public class SchoolControllerTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StudentsService studentsService;
    @MockBean
    MarksService marksService;

    @Test
    public void testListStudents() throws Exception {
        //Given
        List<StudentModel> loadedStudents = new ArrayList<>();
        loadedStudents.add(new StudentModel("Juan","Granados", 1L));
        loadedStudents.add(new StudentModel("John","Due", 2L));
        String expectedJson = objectMapper.writeValueAsString(loadedStudents);
        Mockito.when(studentsService.list()).thenReturn(loadedStudents);
        String url = "/students";
        //when
        MvcResult result = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testCreateStudent() throws Exception {
        //Given
        StudentModel newStudent = getTestStudent();
        String url = "/student";
        //when
        mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newStudent))
        ).andExpect(status().isOk());
    }

    @Test
    public void testGetStudent() throws Exception {
        //Given
        StudentModel student = getTestStudent();
        Mockito.when(studentsService.getStudentById(1L)).thenReturn(student);
        String expectedJson = objectMapper.writeValueAsString(student);
        String url = "/student/{studentId}";
        //When
        MvcResult result = mockMvc.perform(get(url,"1")).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        //Given
        StudentModel student = getTestStudent();
        Mockito.when(studentsService.getStudentById(1L)).thenReturn(student);
        String url = "/student/{studentId}";
        //When
        mockMvc.perform(delete(url,"1")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testUpdateStudent() throws Exception {
        //Given
        StudentModel studentUpdate = new StudentModel("J", "Granados", 2L);
        String url = "/student/{studentId}";
        //When
        mockMvc.perform(
                put(url,"1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentUpdate))
        ).andExpect(status().isOk());
    }

    public StudentModel getTestStudent() {
        return new StudentModel("Juan", "Granados", 1L);
    }

}
