package com.challenge.school.service;

import com.challenge.school.entity.*;
import com.challenge.school.exception.ApiException;
import com.challenge.school.model.CountResponse;
import com.challenge.school.model.SubjectTeacherModel;
import com.challenge.school.repository.GroupRepository;
import com.challenge.school.repository.SubjectRepository;
import com.challenge.school.repository.SubjectTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectTeacherService {
    public static final String SUBJECT_TEACHER_NOT_FOUND = "Teacher for subject/group not found";
    @Autowired
    SubjectTeacherRepository subjectTeacherRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    StudentsService studentsService;

    public CountResponse countStudentsByTeacherId(Long teacherId){
        List<SubjectTeacher> subjectTeacherList = subjectTeacherRepository.findByTeacherId(teacherId);
        List<Long> groupIds = new ArrayList<>();
        subjectTeacherList.forEach(st->groupIds.add(st.getSubjectGroupKey().getGroup().getId()));
        return new CountResponse(studentsService.countStudentsByGroups(groupIds));
    }

    public SubjectTeacherModel getSubjectTeacherById(Long subjectId, Long groupId) throws ApiException {
        Optional<Group> opGroup = groupRepository.findById(groupId);
        Optional<Subject> opSubject = subjectRepository.findById(subjectId);
        if(opGroup.isPresent() && opSubject.isPresent()){
            SubjectGroupKey subjectGroupKey = new SubjectGroupKey();
            subjectGroupKey.setSubject(opSubject.get());
            subjectGroupKey.setGroup(opGroup.get());
            Optional<SubjectTeacher> opSubjectTeacher = subjectTeacherRepository.findById(subjectGroupKey);
            if(opSubjectTeacher.isPresent()){
                SubjectTeacher subjectTeacher = opSubjectTeacher.get();
                return new SubjectTeacherModel(subjectTeacher.getSubjectGroupKey().getSubject().getId(),
                        subjectTeacher.getSubjectGroupKey().getGroup().getId(), subjectTeacher.getTeacherId());
            }else{
                throw new ApiException(HttpStatus.NOT_FOUND, SUBJECT_TEACHER_NOT_FOUND);
            }
        }else{
            if(opSubject.isEmpty()) throw new ApiException(HttpStatus.BAD_REQUEST, SubjectService.SUBJECT_NOT_FOUND);
            else throw new ApiException(HttpStatus.BAD_REQUEST, GroupService.GROUP_NOT_FOUND);
        }
    }

    public List<SubjectTeacherModel> list(){
        List<SubjectTeacher> subjectTeacherList = subjectTeacherRepository.findAll();
        List<SubjectTeacherModel> subjectTeacherModelList = new ArrayList<>();
        subjectTeacherList.forEach(st->subjectTeacherModelList.add(
                new SubjectTeacherModel(st.getSubjectGroupKey().getSubject().getId(),
                        st.getSubjectGroupKey().getGroup().getId(), st.getTeacherId()))
        );
        return subjectTeacherModelList;
    }

    public SubjectTeacherModel create(SubjectTeacherModel subjectTeacherModel){
        Optional<Group> opGroup = groupRepository.findById(subjectTeacherModel.getGroupId());
        Optional<Subject> opSubject = subjectRepository.findById(subjectTeacherModel.getGroupId());
        if(opGroup.isPresent() && opSubject.isPresent()){
            SubjectGroupKey subjectGroupKey = new SubjectGroupKey();
            subjectGroupKey.setSubject(opSubject.get());
            subjectGroupKey.setGroup(opGroup.get());
            SubjectTeacher subjectTeacher = new SubjectTeacher();
            subjectTeacher.setSubjectGroupKey(subjectGroupKey);
            subjectTeacher.setTeacherId(subjectTeacherModel.getTeacherId());
            SubjectTeacher savedSubjectTeacher = subjectTeacherRepository.save(subjectTeacher);
            return new SubjectTeacherModel(savedSubjectTeacher.getSubjectGroupKey().getSubject().getId(),
                    savedSubjectTeacher.getSubjectGroupKey().getGroup().getId(), savedSubjectTeacher.getTeacherId());
        }else{
            if(opSubject.isEmpty()) throw new ApiException(HttpStatus.BAD_REQUEST, SubjectService.SUBJECT_NOT_FOUND);
            else throw new ApiException(HttpStatus.BAD_REQUEST, GroupService.GROUP_NOT_FOUND);
        }
    }

    public SubjectTeacherModel update(SubjectTeacherModel subjectTeacherModel){
        Optional<Group> opGroup = groupRepository.findById(subjectTeacherModel.getGroupId());
        Optional<Subject> opSubject = subjectRepository.findById(subjectTeacherModel.getSubjectId());
        if(opGroup.isPresent() && opSubject.isPresent()){
            SubjectGroupKey subjectGroupKey = new SubjectGroupKey();
            subjectGroupKey.setSubject(opSubject.get());
            subjectGroupKey.setGroup(opGroup.get());
            Optional<SubjectTeacher> opSubjectTeacher = subjectTeacherRepository.findById(subjectGroupKey);
            if(opSubjectTeacher.isPresent()){
                SubjectTeacher subjectTeacher = opSubjectTeacher.get();
                subjectTeacher.setTeacherId(subjectTeacherModel.getTeacherId());
                SubjectTeacher savedSubjectTeacher = subjectTeacherRepository.save(subjectTeacher);
                return new SubjectTeacherModel(savedSubjectTeacher.getSubjectGroupKey().getSubject().getId(),
                        savedSubjectTeacher.getSubjectGroupKey().getGroup().getId(), savedSubjectTeacher.getTeacherId());
            }else{
                throw new ApiException(HttpStatus.BAD_REQUEST, SUBJECT_TEACHER_NOT_FOUND);
            }
        }else{
            if(opSubject.isEmpty()) throw new ApiException(HttpStatus.BAD_REQUEST, SubjectService.SUBJECT_NOT_FOUND);
            else throw new ApiException(HttpStatus.BAD_REQUEST, GroupService.GROUP_NOT_FOUND);
        }
    }

    public void delete(Long subjectId, Long groupId) throws ApiException {
        Optional<Group> opGroup = groupRepository.findById(groupId);
        Optional<Subject> opSubject = subjectRepository.findById(subjectId);
        if (opGroup.isPresent() && opSubject.isPresent()) {
            SubjectGroupKey subjectGroupKey = new SubjectGroupKey();
            subjectGroupKey.setSubject(opSubject.get());
            subjectGroupKey.setGroup(opGroup.get());
            Optional<SubjectTeacher> opSubjectTeacher = subjectTeacherRepository.findById(subjectGroupKey);
            if (opSubjectTeacher.isPresent()) {
                SubjectTeacher subjectTeacher = opSubjectTeacher.get();
                subjectTeacherRepository.delete(subjectTeacher);
            } else {
                throw new ApiException(HttpStatus.BAD_REQUEST, SUBJECT_TEACHER_NOT_FOUND);
            }
        } else {
            if (opSubject.isEmpty()) throw new ApiException(HttpStatus.BAD_REQUEST, SubjectService.SUBJECT_NOT_FOUND);
            else throw new ApiException(HttpStatus.BAD_REQUEST, GroupService.GROUP_NOT_FOUND);
        }
    }
}
