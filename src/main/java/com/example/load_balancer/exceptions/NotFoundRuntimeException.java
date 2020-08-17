package com.example.load_balancer.exceptions;

public class NotFoundRuntimeException extends RuntimeException {
    public NotFoundRuntimeException() {
        super("Not found");
    }
}
