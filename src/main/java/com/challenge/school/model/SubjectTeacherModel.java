package com.challenge.school.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectTeacherModel {
    @ApiModelProperty(notes = "Subject ID", example = "5", required = true, position = 1)
    @JsonProperty
    @NonNull
    Long subjectId;
    @ApiModelProperty(notes = "Group ID", example = "1", required = true, position = 2)
    @JsonProperty
    @NonNull
    Long groupId;
    @ApiModelProperty(notes = "Teacher ID", example = "2", required = true, position = 3)
    @JsonProperty
    @NonNull
    Long teacherId;
}
