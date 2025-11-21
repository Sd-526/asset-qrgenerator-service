package com.example.asset_qrgenerator_service.util;

import lombok.Data;

@Data
public class ApiErrorResponse<T> {
    private T data;
    private String message;
    private int statuscode;
}
