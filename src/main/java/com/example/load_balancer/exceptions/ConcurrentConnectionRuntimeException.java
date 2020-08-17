package com.example.load_balancer.exceptions;

public class ConcurrentConnectionRuntimeException extends RuntimeException {
    public ConcurrentConnectionRuntimeException() {
        super("Number of concurrent connections exceeds limit");
    }
}
