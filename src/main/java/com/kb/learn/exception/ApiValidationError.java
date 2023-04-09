package com.kb.learn.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public final class ApiValidationError {

    @JsonIgnore
    private String object;

    @JsonIgnore
    private String field;

    @JsonIgnore
    private Object rejectedValue;

    @JsonIgnore
    private String message;

    public ApiValidationError(String object, String message) {
        super();
        this.object = object;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiValidationError [object=" + object + ", field=" + field + ", rejectedValue=" + rejectedValue
                + ", message=" + message + "]";
    }

}