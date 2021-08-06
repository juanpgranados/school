package com.challenge.school.service;

import com.challenge.school.entity.Group;
import com.challenge.school.entity.Student;
import com.challenge.school.exception.ApiException;
import com.challenge.school.model.StudentModel;
import com.challenge.school.repository.GroupRepository;
import com.challenge.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentsService {
    public static final String STUDENT_NOT_FOUND = "Student not found";
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;

    public List<StudentModel> list(){
        List<Student> students = studentRepository.findAll();
        List<StudentModel> studentDtos = new ArrayList<>();
        students.stream().forEach((s)->studentDtos.add(new StudentModel(s.getFirstName(),s.getLastName(),s.getGroup().getId())));
        return studentDtos;
    }

    public StudentModel getStudentById(Long studentId) throws ApiException{
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if(opStudent.isPresent()){
            Student student = opStudent.get();
            StudentModel studentDto = new StudentModel(student.getFirstName(),student.getLastName(),student.getGroup().getId());
            return studentDto;
        }else{
            throw new ApiException(HttpStatus.BAD_REQUEST, STUDENT_NOT_FOUND);
        }
    }

    public void create(StudentModel studentModel) throws ApiException {
        Optional<Group> opGroup = groupRepository.findById(studentModel.getGroupId());
        if(opGroup.isPresent()) {
            Student student = new Student();
            student.setFirstName(studentModel.getFirstName());
            student.setLastName(studentModel.getLastName());
            student.setGroup(opGroup.get());
            studentRepository.save(student);
        }else {
            throw new ApiException(HttpStatus.BAD_REQUEST, GroupService.GROUP_NOT_FOUND);
        }

    }

    public void delete(Long studentId) throws ApiException {
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if(opStudent.isPresent()){
            studentRepository.delete(opStudent.get());
        }else{
            throw new ApiException(HttpStatus.BAD_REQUEST, STUDENT_NOT_FOUND);
        }
    }

    public void update(StudentModel studentModel, Long studentId) throws ApiException{
        Optional<Student> opStudent = studentRepository.findById(studentId);
        Optional<Group> opGroup = groupRepository.findById(studentModel.getGroupId());
        if(opStudent.isPresent() && opGroup.isPresent()){
            Student student = opStudent.get();
            student.setFirstName(studentModel.getFirstName());
            student.setLastName(studentModel.getLastName());
            student.setGroup(opGroup.get());
            studentRepository.save(student);
        }else{
            if(opStudent.isEmpty()) throw new ApiException(HttpStatus.BAD_REQUEST, STUDENT_NOT_FOUND);
            if(opGroup.isEmpty()) throw new ApiException(HttpStatus.BAD_REQUEST, GroupService.GROUP_NOT_FOUND);
        }
    }
}
