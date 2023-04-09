package com.kb.learn.module;

import jakarta.validation.constraints.NotEmpty;


public record ApiResponse<T>(@NotEmpty T body) {

}
