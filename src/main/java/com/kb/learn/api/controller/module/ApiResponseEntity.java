package com.kb.learn.api.controller.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ApiResponseEntity<T> {

    private final T body;

}
