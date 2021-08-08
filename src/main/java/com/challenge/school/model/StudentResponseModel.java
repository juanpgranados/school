package com.challenge.school.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentResponseModel extends StudentModel{
    @ApiModelProperty(notes = "Student ID", example = "2")
    @JsonProperty
    Long id;
    public StudentResponseModel(Long id, String firstName, String lastName, Long groupId){
        super(firstName, lastName, groupId);
        this.id = id;
    }
}
