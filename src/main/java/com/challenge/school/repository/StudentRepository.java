package com.challenge.school.repository;

import com.challenge.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    long countByGroupIdIn(List<Long> groupIds);
}
