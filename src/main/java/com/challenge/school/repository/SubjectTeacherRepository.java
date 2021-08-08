package com.challenge.school.repository;

import com.challenge.school.entity.SubjectGroupKey;
import com.challenge.school.entity.SubjectTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectTeacherRepository  extends JpaRepository<SubjectTeacher, SubjectGroupKey> {
    List<SubjectTeacher> findByTeacherId(Long teacherId);
}
