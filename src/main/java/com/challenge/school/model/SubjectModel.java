package com.challenge.school.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class SubjectModel {
    @ApiModelProperty(notes = "Subject ID", example = "2", position = 1)
    @JsonProperty
    Long id;
    @ApiModelProperty(notes = "Subject Title", example = "Math", required = true, position = 2)
    @JsonProperty
    @NonNull
    String title;
}
