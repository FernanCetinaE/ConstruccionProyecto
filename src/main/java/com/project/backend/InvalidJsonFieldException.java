package com.project.backend;

public class InvalidJsonFieldException extends Exception {
    public InvalidJsonFieldException(String message) {
        super(message);
    }
}
