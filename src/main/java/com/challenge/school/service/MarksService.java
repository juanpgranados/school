package com.challenge.school.service;

import com.challenge.school.entity.Mark;
import com.challenge.school.entity.Student;
import com.challenge.school.entity.Subject;
import com.challenge.school.exception.ApiException;
import com.challenge.school.model.MarkModel;
import com.challenge.school.repository.MarkRepository;
import com.challenge.school.repository.StudentRepository;
import com.challenge.school.repository.SubjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MarksService {
    public static final String MARK_NOT_FOUND = "Mark not found";

    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public List<MarkModel> list(){
        List<Mark> marks = markRepository.findAll();
        List<MarkModel> markDtos = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        marks.forEach((m)->markDtos.add(modelMapper.map(m,MarkModel.class)));
        return markDtos;
    }

    public MarkModel getMarkById(Long markId){
        Optional<Mark> opMark = markRepository.findById(markId);
        if(opMark.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(opMark.get(), MarkModel.class);
        }else{
            throw new ApiException(HttpStatus.NOT_FOUND, MARK_NOT_FOUND);
        }
    }

    public List<MarkModel> listByStudent(Long sid){
        List<Mark> marks = markRepository.findByStudentId(sid);
        List<MarkModel> marksDto = new ArrayList<>();
        marks.forEach((m)->marksDto.add(new MarkModel(m.getStudent().getId(),
                m.getSubject().getId(), m.getMark(), m.getDate())));
        return marksDto;
    }

    public MarkModel getStudentSubjectMark(Long studentId, Long subjectId){
        List<Mark> marks = markRepository.findByStudentIdAndSubjectId(studentId, subjectId);
        if(!marks.isEmpty()){
            Mark mark = marks.get(0);
            return new MarkModel(mark.getStudent().getId(), mark.getSubject().getId(), mark.getMark(), mark.getDate());
        }
        return null;
    }

    public MarkModel create(MarkModel markDto) throws ApiException{
        Optional<Student> opStudent = studentRepository.findById(markDto.getStudentId());
        Optional<Subject> opSubject = subjectRepository.findById(markDto.getSubjectId());
        if(opStudent.isPresent() && opSubject.isPresent()){
            Mark mark = new Mark();
            mark.setMark(markDto.getMark());
            mark.setStudent(opStudent.get());
            mark.setSubject(opSubject.get());
            Mark savedMark = markRepository.save(mark);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(savedMark, MarkModel.class);
        }else{
            if(opStudent.isEmpty()) throw new ApiException(HttpStatus.BAD_REQUEST, StudentsService.STUDENT_NOT_FOUND);
            else throw new ApiException(HttpStatus.BAD_REQUEST, SubjectService.SUBJECT_NOT_FOUND);
        }
    }

    public MarkModel update(MarkModel markDto, Long markId) throws ApiException{
        Optional<Mark> opMark = markRepository.findById(markId);
        Optional<Student> opStudent = studentRepository.findById(markDto.getStudentId());
        Optional<Subject> opSubject = subjectRepository.findById(markDto.getSubjectId());
        if(opMark.isPresent() && opStudent.isPresent() && opSubject.isPresent()){
            Mark mark = opMark.get();
            mark.setMark(markDto.getMark());
            mark.setStudent(opStudent.get());
            mark.setSubject(opSubject.get());
            Mark savedMark = markRepository.save(mark);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(savedMark, MarkModel.class);
        }else{
            if(opMark.isEmpty()) throw new ApiException(HttpStatus.BAD_REQUEST, MARK_NOT_FOUND);
            else if(opStudent.isEmpty()) throw new ApiException(HttpStatus.BAD_REQUEST,
                StudentsService.STUDENT_NOT_FOUND);
            else throw new ApiException(HttpStatus.BAD_REQUEST, SubjectService.SUBJECT_NOT_FOUND);
        }
    }

    public void delete(Long markId) throws ApiException{
        Optional<Mark> opMark = markRepository.findById(markId);
        if(opMark.isPresent()){
            markRepository.delete(opMark.get());
        }else{
            throw new ApiException(HttpStatus.BAD_REQUEST, MARK_NOT_FOUND);
        }
    }
}
