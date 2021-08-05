package com.challenge.school.service;

import com.challenge.school.entity.Subject;
import com.challenge.school.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    public static final String SUBJECT_NOT_FOUND = "Subject not found";
    @Autowired
    SubjectRepository subjectRepository;

    public List<Subject> list(){
        return subjectRepository.findAll();
    }
}
