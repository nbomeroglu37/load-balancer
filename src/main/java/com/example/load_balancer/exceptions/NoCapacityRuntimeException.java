package com.example.load_balancer.exceptions;

public class NoCapacityRuntimeException extends RuntimeException {
    public NoCapacityRuntimeException() {
        super("No registered provider");
    }
}
