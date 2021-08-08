package com.challenge.school.controller;

import com.challenge.school.model.*;
import com.challenge.school.security.JwtAuthenticationEntryPoint;
import com.challenge.school.security.JwtTokenUtil;
import com.challenge.school.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
    @MockBean
    GroupService groupService;
    @MockBean
    SubjectService subjectService;
    @MockBean
    SubjectTeacherService subjectTeacherService;
    @MockBean
    JwtUserDetailsService jwtUserDetailsService;
    @MockBean
    JwtTokenUtil jwtTokenUtil;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    static final String AUTH_HEADER = "Bearer token";

    @BeforeEach
    public void mockSecurity(){
        Mockito.when(jwtTokenUtil.getUsernameFromToken(any(String.class))).thenReturn("testuser");
        Mockito.when(jwtTokenUtil.validateToken(any(String.class), any(UserDetails.class))).thenReturn(true);
        Mockito.when(jwtUserDetailsService.loadUserByUsername("testuser")).thenReturn(new User("testUser", "p",
                new ArrayList<>()));
    }

    @Test
    public void testListStudents() throws Exception {
        //Given
        List<StudentResponseModel> loadedStudents = new ArrayList<>();
        loadedStudents.add(new StudentResponseModel(1L, "Juan","Granados", 1L));
        loadedStudents.add(new StudentResponseModel(2L, "John","Doe", 2L));
        String expectedJson = objectMapper.writeValueAsString(loadedStudents);
        Mockito.when(studentsService.list()).thenReturn(loadedStudents);
        String url = "/students";
        //when
        MvcResult result = mockMvc.perform(
                get(url).header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
        ).andExpect(status().isOk()).andReturn();
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
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
        ).andExpect(status().isOk());
    }

    @Test
    public void testGetStudent() throws Exception {
        //Given
        StudentResponseModel student = getTestStudent();
        Mockito.when(studentsService.getStudentById(1L)).thenReturn(student);
        String expectedJson = objectMapper.writeValueAsString(student);
        String url = "/student/{studentId}";
        //When
        MvcResult result = mockMvc.perform(
                get(url,"1").header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testDeleteStudent() throws Exception {
        //Given
        String expectedJson = objectMapper.writeValueAsString(getSuccessResult());
        String url = "/student/{studentId}";
        //When
        MvcResult result = mockMvc.perform(
                delete(url,"1").header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
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
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(studentUpdate))
        ).andExpect(status().isOk());
    }

    public StudentResponseModel getTestStudent() {
        return new StudentResponseModel(1L, "Juan", "Granados", 1L);
    }

    public OperationResult getSuccessResult(){
        return new OperationResult("success");
    }

    @Test
    public void testCreateGroup() throws Exception {
        //Given
        GroupModel groupDto = new GroupModel("Gamma");
        String expectedJson = objectMapper.writeValueAsString(new GroupModel("Gamma", 1L));
        Mockito.when(groupService.create(any(GroupModel.class))).thenReturn(new GroupModel("Gamma", 1L));
        String url = "/group";
        //When
        MvcResult result = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(groupDto))
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testGetGroup() throws Exception{
        //Given
        Mockito.when(groupService.getGroupById(1L)).thenReturn(new GroupModel("Alpha", 1L));
        String expectedJson = objectMapper.writeValueAsString(new GroupModel("Alpha", 1L));
        String url = "/group/{groupId}";
        //When
        MvcResult result = mockMvc.perform(
                get(url,"1").header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testUpdateGroup() throws Exception{
        //Given
        GroupModel groupDto = new GroupModel("Alpha");
        String expectedJson = objectMapper.writeValueAsString(new GroupModel("Alpha", 1L));
        Mockito.when(groupService.update(any(GroupModel.class), any(Long.class)))
                .thenReturn(new GroupModel("Alpha", 1L));
        String url = "/group/{groupId}";
        //When
        MvcResult result = mockMvc.perform(
                put(url, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(groupDto))
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testDeleteGroup() throws Exception{
        //Given
        String expectedJson = objectMapper.writeValueAsString(getSuccessResult());
        String url = "/group/{groupId}";
        //When
        MvcResult result = mockMvc.perform(
                delete(url,"1").header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testCreateSubject() throws Exception {
        //Given
        SubjectModel subjectDto = new SubjectModel("Art");
        String expectedJson = objectMapper.writeValueAsString(new SubjectModel(1L, "Art"));
        Mockito.when(subjectService.create(any(SubjectModel.class))).thenReturn(new SubjectModel(1L, "Art"));
        String url = "/subject";
        //When
        MvcResult result = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(subjectDto))
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testGetSubject() throws Exception{
        //Given
        Mockito.when(subjectService.getSubjectById(1L)).thenReturn(new SubjectModel(1L, "Art"));
        String expectedJson = objectMapper.writeValueAsString(new SubjectModel(1L, "Art"));
        String url = "/subject/{subjectId}";
        //When
        MvcResult result = mockMvc.perform(
                get(url,"1").header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testUpdateSubject() throws Exception{
        //Given
        SubjectModel subjectDto = new SubjectModel("Art");
        String expectedJson = objectMapper.writeValueAsString(new SubjectModel(1L, "Art"));
        Mockito.when(subjectService.update(any(SubjectModel.class), any(Long.class)))
                .thenReturn(new SubjectModel(1L, "Art"));
        String url = "/subject/{subjectId}";
        //When
        MvcResult result = mockMvc.perform(
                put(url, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(subjectDto))
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testDeleteSubject() throws Exception{
        //Given
        String expectedJson = objectMapper.writeValueAsString(getSuccessResult());
        String url = "/subject/{subjectId}";
        //When
        MvcResult result = mockMvc.perform(
                delete(url,"1").header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testCreateMark() throws Exception {
        //Given
        MarkModel markDto = new MarkModel(1L, 1L, 9.8F, null);
        String expectedJson = objectMapper.writeValueAsString(markDto);
        Mockito.when(marksService.create(any(MarkModel.class))).thenReturn(markDto);
        String url = "/mark";
        //When
        MvcResult result = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(markDto))
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testGetMark() throws Exception{
        //Given
        Mockito.when(marksService.getMarkById(1L)).thenReturn(new MarkModel(1L, 1L, 9.8F, null));
        String expectedJson = objectMapper.writeValueAsString(new MarkModel(1L, 1L, 9.8F, null));
        String url = "/mark/{markId}";
        //When
        MvcResult result = mockMvc.perform(
                get(url,"1").header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testUpdateMark() throws Exception{
        //Given
        MarkModel markDto = new MarkModel(1L, 1L, 9.8F, null);
        String expectedJson = objectMapper.writeValueAsString(markDto);
        Mockito.when(marksService.update(any(MarkModel.class), any(Long.class)))
                .thenReturn(new MarkModel(1L, 1L, 9.8F, null));
        String url = "/mark/{markId}";
        //When
        MvcResult result = mockMvc.perform(
                put(url, "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                        .content(objectMapper.writeValueAsString(markDto))
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void testDeleteMark() throws Exception{
        //Given
        String expectedJson = objectMapper.writeValueAsString(getSuccessResult());
        String url = "/mark/{markId}";
        //When
        MvcResult result = mockMvc.perform(
                delete(url,"1").header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
        ).andExpect(status().isOk()).andReturn();
        String actualJson = result.getResponse().getContentAsString();
        //Then
        assertThat(actualJson).isEqualToIgnoringWhitespace(expectedJson);
    }

}
