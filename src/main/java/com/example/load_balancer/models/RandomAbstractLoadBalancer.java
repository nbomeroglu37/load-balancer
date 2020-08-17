package com.example.load_balancer.models;

import java.util.Random;

public class RandomAbstractLoadBalancer extends AbstractLoadBalancer {

    private final Random rand;

    public RandomAbstractLoadBalancer() {
        rand = new Random();
    }

    public String get() {
        return get(rand.nextInt());
    }
}
