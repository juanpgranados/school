package com.challenge.school.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentModel {
    @ApiModelProperty(notes = "First Name", example = "Jhon", required = true, position = 1)
    @JsonProperty
    String firstName;
    @ApiModelProperty(notes = "Last Name", example = "Due", required = true, position = 2)
    @JsonProperty
    String lastName;
    @ApiModelProperty(notes = "Group ID", example = "2", required = true, position = 3)
    @JsonProperty
    Long groupId;
}
