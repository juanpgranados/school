package com.challenge.school.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    @ApiModelProperty(notes = "Token", example = "", required = true, position = 1)
    @JsonProperty
    @NonNull
    String token;
}
