package com.challenge.school.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="GROUPS")
@Getter
@Setter
@ToString
public class Group implements Serializable {
    private static final long serialVersionUID = -2077849081358066240L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
