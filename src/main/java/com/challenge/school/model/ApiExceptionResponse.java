package com.challenge.school.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiExceptionResponse {
    @ApiModelProperty(notes = "Status description", example = "HTTP_STATUS_DESC", position = 0)
    @JsonProperty
    private HttpStatus status;
    @ApiModelProperty(notes = "Error message", example = "Detailed error message", position = 1)
    @JsonProperty
    private String message;
}
