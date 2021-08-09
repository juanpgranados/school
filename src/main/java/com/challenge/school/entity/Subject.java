package com.challenge.school.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="SUBJECTS")
@Getter
@Setter
@ToString
public class Subject implements Serializable {
    private static final long serialVersionUID = 3000638048006877887L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
}
