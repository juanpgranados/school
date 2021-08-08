package com.challenge.school.controller;

import com.challenge.school.exception.ApiException;
import com.challenge.school.model.*;
import com.challenge.school.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/")
public class SchoolController{
    @Autowired
    StudentsService studentsService;
    @Autowired
    MarksService marksService;
    @Autowired
    GroupService groupService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    SubjectTeacherService subjectTeacherService;

    private static final String SUCCESS="success";

    //### ---STUDENTS--- ###
    @GetMapping(value = "/students")
    public List<StudentResponseModel> getAllStudents(){
        return studentsService.list();
    }

    @PostMapping(value="/student")
    public StudentResponseModel createStudent(@RequestBody StudentModel studentDto){
        try {
            return studentsService.create(studentDto);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(),apiException.getMessage());
        }
    }

    @DeleteMapping(value="/student/{studentId}")
    public OperationResult deleteStudent(@PathVariable Long studentId){
        try{
            studentsService.delete(studentId);
            return new OperationResult(SUCCESS);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @GetMapping(value="/student/{studentId}")
    public StudentResponseModel getStudent(@PathVariable Long studentId){
        try{
            return studentsService.getStudentById(studentId);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @PutMapping(value = "/student/{studentId}")
    public StudentResponseModel updateStudent(@PathVariable Long studentId, @RequestBody StudentModel studentDto){
        try{
            return studentsService.update(studentDto, studentId);
        }catch(ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    //### ---REPORTS--- ###
    @GetMapping(value = "/marks/student/{studentId}")
    public List<MarkModel> getMarksByStudent(@PathVariable Long studentId){
        return marksService.listByStudent(studentId);
    }

    @GetMapping(value = "/student/{studentId}/subject/{subjectId}")
    public MarkModel getMarkByStudentAndSubject(@PathVariable Long studentId, @PathVariable Long subjectId){
        return marksService.getStudentSubjectMark(studentId, subjectId);
    }

    @GetMapping(value = "/teacher/{teacherId}/student-count")
    public CountResponse getNumberOfStudents(@PathVariable Long teacherId){
        return subjectTeacherService.countStudentsByTeacherId(teacherId);
    }
    //### ---GROUPS--- ###
    @GetMapping(value = "/group/{groupId}")
    public GroupModel getGroup(@PathVariable Long groupId) {
        try{
            return groupService.getGroupById(groupId);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @GetMapping(value = "/groups")
    public List<GroupModel> getAllGroups() {
        try{
            return groupService.list();
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @PostMapping(value = "/group")
    public GroupModel createGroup(@RequestBody GroupModel groupModel) {
        try{
            return groupService.create(groupModel);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @PutMapping(value = "/group/{groupId}")
    public GroupModel updateGroup(GroupModel groupDto, @PathVariable Long groupId) {
        try{
            return groupService.update(groupDto, groupId);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @DeleteMapping(value = "/group/{groupId}")
    public OperationResult deleteGroup(@PathVariable Long groupId) {
        try{
            groupService.getGroupById(groupId);
            return new OperationResult(SUCCESS);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    //### ---SUBJECTS--- ###
    @GetMapping(value = "/subject/{subjectId}")
    public SubjectModel getSubject(@PathVariable Long subjectId){
        try {
            return subjectService.getSubjectById(subjectId);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @GetMapping(value = "/subjects")
    public List<SubjectModel> getAllSubjects(){
        return subjectService.list();
    }

    @PostMapping(value = "/subject")
    public SubjectModel createSubject(SubjectModel subjectDto){
        try{
            return subjectService.create(subjectDto);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @PutMapping(value = "/subject/{subjectId}")
    public SubjectModel updateSubject(SubjectModel subjectDto, @PathVariable Long subjectId){
        try{
            return subjectService.update(subjectDto, subjectId);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @DeleteMapping(value = "/subject/{subjectId}")
    public OperationResult deleteSubject(@PathVariable Long subjectId){
        try{
            subjectService.delete(subjectId);
            return new OperationResult(SUCCESS);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    //### ---MARKS--- ###
    @GetMapping(value = "/mark/{markId}")
    public MarkModel getMark(@PathVariable Long markId){
        try{
            return marksService.getMarkById(markId);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @GetMapping(value = "/marks")
    public List<MarkModel> getAllMarks(){
        return marksService.list();
    }

    @PostMapping(value = "/mark")
    public MarkModel createMark(MarkModel markDto){
        try{
            return marksService.create(markDto);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @PutMapping(value = "/mark/{markId}")
    public MarkModel updateMark(MarkModel markDto,@PathVariable Long markId){
        try{
            return marksService.update(markDto, markId);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @DeleteMapping(value = "/mark/{markId}")
    public OperationResult deleteMark(@PathVariable Long markId){
        try{
            marksService.delete(markId);
            return new OperationResult(SUCCESS);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    //### ---SUBJECT-TEACHER--- ###
    @GetMapping(value = "/subject/{subjectId}/group/{groupId}")
    public SubjectTeacherModel getSubjectTeacher(@PathVariable Long subjectId, @PathVariable Long groupId){
        try{
            return subjectTeacherService.getSubjectTeacherById(subjectId, groupId);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @GetMapping(value = "/subjects/groups")
    public List<SubjectTeacherModel> getAllSubjectTeacher(){
        return subjectTeacherService.list();
    }

    @PostMapping(value = "/subject/group")
    public SubjectTeacherModel createSubjectTeacher(@RequestBody SubjectTeacherModel subjectTeacherModel){
        try{
            return subjectTeacherService.create(subjectTeacherModel);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @PutMapping(value = "/subject/group")
    public SubjectTeacherModel updateSubjectTeacher(@RequestBody SubjectTeacherModel subjectTeacherModel){
        try{
            return subjectTeacherService.update(subjectTeacherModel);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @DeleteMapping(value = "/subject/{subjectId}/group/{groupId}")
    public OperationResult deleteSubjectTeacher(@PathVariable Long subjectId, @PathVariable Long groupId){
        try{
            subjectTeacherService.delete(subjectId, groupId);
            return new OperationResult(SUCCESS);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }
}
