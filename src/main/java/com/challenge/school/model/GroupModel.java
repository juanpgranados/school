package com.challenge.school.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class GroupModel {
    @ApiModelProperty(notes = "Group Name", example = "Delta", required = true, position = 1)
    @JsonProperty
    @NonNull
    String name;
    @ApiModelProperty(notes = "Group ID", example = "2", required = false, position = 2)
    @JsonProperty
    Long id;
}
