package com.example.load_balancer.models;

import java.util.Random;

public class RandomLoadBalancer extends AbstractLoadBalancer {

    private final Random rand;

    public RandomLoadBalancer() {
        rand = new Random();
    }

    public String get() {
        return get(rand.nextInt());
    }
}
