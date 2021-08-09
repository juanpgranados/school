package com.challenge.school.entity;

import lombok.*;

import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectGroupKey implements Serializable {
    private static final long serialVersionUID = 7583418401792283197L;
    @ManyToOne
    private Subject subject;
    @ManyToOne
    private Group group;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectGroupKey subjectGroupKey = (SubjectGroupKey) o;
        return subject.getId().equals(subjectGroupKey.getSubject().getId()) &&
                group.getId().equals(subjectGroupKey.getGroup().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, group);
    }
}
