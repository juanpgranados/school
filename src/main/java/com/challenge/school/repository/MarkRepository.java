package com.challenge.school.repository;

import com.challenge.school.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {

    List<Mark> findByStudentId(Long studentId);
    List<Mark> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
