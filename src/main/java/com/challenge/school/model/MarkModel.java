package com.challenge.school.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarkModel {
    @ApiModelProperty(notes = "Student ID", example = "1", required = true, position = 1)
    @JsonProperty
    Long studentId;
    @ApiModelProperty(notes = "Subject ID", example = "5", required = true, position = 2)
    @JsonProperty
    Long subjectId;
    @ApiModelProperty(notes = "Mark", example = "9.5", required = true, position = 3)
    @JsonProperty
    Float mark;
    @JsonProperty
    Date date;
}
