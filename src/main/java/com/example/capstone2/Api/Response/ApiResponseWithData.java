package com.example.capstone2.Api.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseWithData<T> {
    private String message;
    private T data;
}
