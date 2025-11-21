package com.example.asset_qrgenerator_service.exception;

public class DuplicateSerialNumException extends RuntimeException {
    public DuplicateSerialNumException(String message) {
        super(message);
    }
}
