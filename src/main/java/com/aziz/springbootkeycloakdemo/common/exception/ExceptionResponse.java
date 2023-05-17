package com.aziz.springbootkeycloakdemo.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    @JsonProperty
    private String errorCode;

    @JsonProperty
    private String errorMessage;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long errorEntityId;

    public ExceptionResponse(String name, String errorMessage) {
        this.errorCode = name;
        this.errorMessage = errorMessage;
    }
}