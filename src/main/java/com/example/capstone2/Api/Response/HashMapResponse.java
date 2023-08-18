package com.example.capstone2.Api.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class HashMapResponse {
    private HashMap<?, ?> response;
}
