package com.example.demo.exceptions;

public class FileFormartExceprtion extends RuntimeException {
    public FileFormartExceprtion(String message) {
        super(message);
    }

    public FileFormartExceprtion(String message, Throwable cause) {
        super(message, cause);
    }
}
