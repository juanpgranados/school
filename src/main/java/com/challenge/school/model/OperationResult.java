package com.challenge.school.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OperationResult {
    @ApiModelProperty(notes = "Result message", example = "success", required = true, position = 1)
    @JsonProperty
    @NonNull
    String message;
}
