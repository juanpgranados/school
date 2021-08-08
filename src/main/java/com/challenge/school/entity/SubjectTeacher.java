package com.challenge.school.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SUBJECT_TEACHER")
@Getter
@Setter
@ToString
public class SubjectTeacher {
    @EmbeddedId
    SubjectGroupKey subjectGroupKey;
    Long teacherId;
}
