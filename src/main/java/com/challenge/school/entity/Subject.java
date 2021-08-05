package com.challenge.school.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SUBJECTS")
@Getter
@Setter
@ToString
public class Subject {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
}
