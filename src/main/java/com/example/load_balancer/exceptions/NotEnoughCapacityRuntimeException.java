package com.example.load_balancer.exceptions;

public class NotEnoughCapacityRuntimeException extends RuntimeException {
    public NotEnoughCapacityRuntimeException() {
        super("Not enough capacity");
    }
}
