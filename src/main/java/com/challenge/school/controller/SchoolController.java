package com.challenge.school.controller;

import com.challenge.school.exception.ApiException;
import com.challenge.school.model.*;
import com.challenge.school.service.*;
import io.swagger.annotations.ApiImplicitParam;
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
    @Autowired
    GroupService groupService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    SubjectTeacherService subjectTeacherService;

    private static final String SUCCESS="success";

    //### ---STUDENTS--- ###
    @GetMapping(value = "/students")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public List<StudentResponseModel> getAllStudents(){
        return studentsService.list();
    }

    @PostMapping(value="/student")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public StudentResponseModel createStudent(@RequestBody StudentModel studentDto){
        try {
            return studentsService.create(studentDto);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(),apiException.getMessage());
        }
    }

    @DeleteMapping(value="/student/{studentId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public OperationResult deleteStudent(@PathVariable Long studentId){
        try{
            studentsService.delete(studentId);
            return new OperationResult(SUCCESS);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @GetMapping(value="/student/{studentId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public StudentResponseModel getStudent(@PathVariable Long studentId){
        try{
            return studentsService.getStudentById(studentId);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @PutMapping(value = "/student/{studentId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public StudentResponseModel updateStudent(@PathVariable Long studentId, @RequestBody StudentModel studentDto){
        try{
            return studentsService.update(studentDto, studentId);
        }catch(ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    //### ---REPORTS--- ###
    @GetMapping(value = "/marks/student/{studentId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public List<MarkModel> getMarksByStudent(@PathVariable Long studentId){
        return marksService.listByStudent(studentId);
    }

    @GetMapping(value = "/student/{studentId}/subject/{subjectId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public MarkModel getMarkByStudentAndSubject(@PathVariable Long studentId, @PathVariable Long subjectId){
        return marksService.getStudentSubjectMark(studentId, subjectId);
    }

    @GetMapping(value = "/teacher/{teacherId}/student-count")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public CountResponse getNumberOfStudents(@PathVariable Long teacherId){
        return subjectTeacherService.countStudentsByTeacherId(teacherId);
    }
    //### ---GROUPS--- ###
    @GetMapping(value = "/group/{groupId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public GroupModel getGroup(@PathVariable Long groupId) {
        try{
            return groupService.getGroupById(groupId);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @GetMapping(value = "/groups")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public List<GroupModel> getAllGroups() {
        try{
            return groupService.list();
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @PostMapping(value = "/group")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public GroupModel createGroup(@RequestBody GroupModel groupModel) {
        try{
            return groupService.create(groupModel);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @PutMapping(value = "/group/{groupId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public GroupModel updateGroup(GroupModel groupDto, @PathVariable Long groupId) {
        try{
            return groupService.update(groupDto, groupId);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @DeleteMapping(value = "/group/{groupId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
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
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public SubjectModel getSubject(@PathVariable Long subjectId){
        try {
            return subjectService.getSubjectById(subjectId);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @GetMapping(value = "/subjects")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public List<SubjectModel> getAllSubjects(){
        return subjectService.list();
    }

    @PostMapping(value = "/subject")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public SubjectModel createSubject(SubjectModel subjectDto){
        try{
            return subjectService.create(subjectDto);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @PutMapping(value = "/subject/{subjectId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public SubjectModel updateSubject(SubjectModel subjectDto, @PathVariable Long subjectId){
        try{
            return subjectService.update(subjectDto, subjectId);
        }catch (ApiException exception){
            throw new ApiException(exception.getStatus(), exception.getMessage());
        }
    }

    @DeleteMapping(value = "/subject/{subjectId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
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
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public MarkModel getMark(@PathVariable Long markId){
        try{
            return marksService.getMarkById(markId);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @GetMapping(value = "/marks")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public List<MarkModel> getAllMarks(){
        return marksService.list();
    }

    @PostMapping(value = "/mark")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public MarkModel createMark(MarkModel markDto){
        try{
            return marksService.create(markDto);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @PutMapping(value = "/mark/{markId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public MarkModel updateMark(MarkModel markDto,@PathVariable Long markId){
        try{
            return marksService.update(markDto, markId);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @DeleteMapping(value = "/mark/{markId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
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
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public SubjectTeacherModel getSubjectTeacher(@PathVariable Long subjectId, @PathVariable Long groupId){
        try{
            return subjectTeacherService.getSubjectTeacherById(subjectId, groupId);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @GetMapping(value = "/subjects/groups")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public List<SubjectTeacherModel> getAllSubjectTeacher(){
        return subjectTeacherService.list();
    }

    @PostMapping(value = "/subject/group")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public SubjectTeacherModel createSubjectTeacher(@RequestBody SubjectTeacherModel subjectTeacherModel){
        try{
            return subjectTeacherService.create(subjectTeacherModel);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @PutMapping(value = "/subject/group")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public SubjectTeacherModel updateSubjectTeacher(@RequestBody SubjectTeacherModel subjectTeacherModel){
        try{
            return subjectTeacherService.update(subjectTeacherModel);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }

    @DeleteMapping(value = "/subject/{subjectId}/group/{groupId}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example
            = "Bearer access_token")
    public OperationResult deleteSubjectTeacher(@PathVariable Long subjectId, @PathVariable Long groupId){
        try{
            subjectTeacherService.delete(subjectId, groupId);
            return new OperationResult(SUCCESS);
        }catch (ApiException apiException){
            throw new ApiException(apiException.getStatus(), apiException.getMessage());
        }
    }
}
