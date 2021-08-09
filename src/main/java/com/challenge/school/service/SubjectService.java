package com.challenge.school.service;

import com.challenge.school.entity.Subject;
import com.challenge.school.exception.ApiException;
import com.challenge.school.model.SubjectModel;
import com.challenge.school.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    public static final String SUBJECT_NOT_FOUND = "Subject not found";
    @Autowired
    SubjectRepository subjectRepository;

    public List<SubjectModel> list(){
        List<Subject> subjects = subjectRepository.findAll();
        List<SubjectModel> subjectDtos = new ArrayList<>();
        subjects.forEach(s->subjectDtos.add(new SubjectModel(s.getId(), s.getTitle())));
        return subjectDtos;
    }

    public SubjectModel getSubjectById(Long subjectId) throws ApiException {
        Optional<Subject> opSubject = subjectRepository.findById(subjectId);
        if(opSubject.isPresent()){
            Subject subject = opSubject.get();
            return new SubjectModel(subject.getId(), subject.getTitle());
        }else{
            throw new ApiException(HttpStatus.NOT_FOUND, SUBJECT_NOT_FOUND);
        }
    }

    public SubjectModel create(SubjectModel subjectModel){
        Subject subject = new Subject();
        subject.setTitle(subjectModel.getTitle());
        Subject savedSubject = subjectRepository.save(subject);
        return new SubjectModel(savedSubject.getId(),savedSubject.getTitle());
    }

    public SubjectModel update(SubjectModel subjectModel, Long subjectId) throws ApiException{
        Optional<Subject> opSubject = subjectRepository.findById(subjectId);
        if(opSubject.isPresent()){
            Subject subject = opSubject.get();
            subject.setTitle(subjectModel.getTitle());
            Subject updatedSubject = subjectRepository.save(subject);
            return new SubjectModel(updatedSubject.getId(), updatedSubject.getTitle());
        }else{
            throw new ApiException(HttpStatus.BAD_REQUEST, SUBJECT_NOT_FOUND);
        }
    }

    public void delete(Long subjectId) throws ApiException {
        Optional<Subject> opSubject = subjectRepository.findById(subjectId);
        if(opSubject.isPresent()){
            subjectRepository.delete(opSubject.get());
        }else{
            throw new ApiException(HttpStatus.BAD_REQUEST, SUBJECT_NOT_FOUND);
        }
    }
}
