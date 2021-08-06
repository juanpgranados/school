package com.challenge.school.controller;

import com.challenge.school.entity.Mark;
import com.challenge.school.entity.Student;
import com.challenge.school.exception.ApiException;
import com.challenge.school.model.MarkModel;
import com.challenge.school.model.StudentModel;
import com.challenge.school.service.MarksService;
import com.challenge.school.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SchoolController{
    @Autowired
    StudentsService studentsService;
    @Autowired
    MarksService marksService;

    private static final String SUCCESS="{\"message\":\"success\"}";

    @GetMapping(value = "/students")
    public List<StudentModel> getAllStudents(){
        return studentsService.list();
    }

    @PostMapping(value="/student")
    public String createStudent(@RequestBody StudentModel studentDto){
        try {
            studentsService.create(studentDto);
            return SUCCESS;
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(),apiException.getMessage());
        }
    }

    @DeleteMapping(value="/student/{studentId}")
    public String deleteStudent(@PathVariable Long studentId){
        try{
            studentsService.delete(studentId);
            return SUCCESS;
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @GetMapping(value="/student/{studentId}")
    public StudentModel getStudent(@PathVariable Long studentId){
        try{
            return studentsService.getStudentById(studentId);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @PutMapping(value = "/student/{studentId}")
    public String updateStudent(@PathVariable Long studentId, @RequestBody StudentModel studentDto){
        try{
            studentsService.update(studentDto, studentId);
            return SUCCESS;
        }catch(ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @GetMapping(value = "/marks/student/{studentId}")
    public List<MarkModel> getMarksByStudent(@PathVariable Long studentId){
        return marksService.listByStudent(studentId);
    }

    @GetMapping(value = "/student/{studentId}/subject/{subjectId}")
    public Mark getMarkByStudentAndSubject(@PathVariable Long studentId, @PathVariable Long subjectId){
        return marksService.getStudentSubjectMark(studentId, subjectId);
    }

}
