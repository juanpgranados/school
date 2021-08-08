package com.challenge.school.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @ApiModelProperty(notes = "User", example = "schoolapiuser", required = true, position = 1)
    @JsonProperty
    @NonNull
    private String user;
    @ApiModelProperty(notes = "User", example = "school2021", required = true, position = 2)
    @JsonProperty
    @NonNull
    private String password;
}
