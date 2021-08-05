package com.challenge.school.service;

import com.challenge.school.entity.Mark;
import com.challenge.school.entity.Student;
import com.challenge.school.entity.Subject;
import com.challenge.school.exception.ApiException;
import com.challenge.school.model.MarkModel;
import com.challenge.school.repository.MarkRepository;
import com.challenge.school.repository.StudentRepository;
import com.challenge.school.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MarksService {

    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    public List<Mark> list(){
        return markRepository.findAll();
    }

    public List<MarkModel> listByStudent(Long sid){
        List<Mark> marks = markRepository.findByStudentId(sid);
        List<MarkModel> marksDto = new ArrayList<>();
        marks.stream().forEach((m)->marksDto.add(new MarkModel(m.getStudent().getId(),
                m.getSubject().getId(), m.getMark(), m.getDate())));
        return marksDto;
    }

    public Mark getStudentSubjectMark(Long studentId, Long subjectId){
        List<Mark> marks = markRepository.findByStudentIdAndSubjectId(studentId, subjectId);
        if(!marks.isEmpty())
            return marks.get(0);
        return null;
    }

    public void create(MarkModel markDto){
        Optional<Student> opStudent = studentRepository.findById(markDto.getStudentId());
        Optional<Subject> opSubject = subjectRepository.findById(markDto.getSubjectId());
        if(opStudent.isPresent() && opSubject.isPresent()){
            Mark mark = new Mark();
            mark.setMark(mark.getMark());
            mark.setStudent(opStudent.get());
            mark.setSubject(opSubject.get());
            markRepository.save(mark);
        }else{
            if(opStudent.isEmpty()) throw new ApiException(HttpStatus.BAD_REQUEST, StudentsService.STUDENT_NOT_FOUND);
            if(opSubject.isEmpty()) throw new ApiException(HttpStatus.BAD_REQUEST, SubjectService.SUBJECT_NOT_FOUND);
        }
    }
}
