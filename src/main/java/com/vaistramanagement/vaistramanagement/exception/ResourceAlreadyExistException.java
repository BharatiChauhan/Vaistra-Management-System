package com.vaistramanagement.vaistramanagement.exception;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException() {

    }

    public ResourceAlreadyExistException(String msg) {
        super(msg);
    }
}