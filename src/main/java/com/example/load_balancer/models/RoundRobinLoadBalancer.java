package com.example.load_balancer.models;

public class RoundRobinLoadBalancer extends AbstractLoadBalancer {
    private int nextIndex;

    public RoundRobinLoadBalancer() {
        nextIndex = 0;
    }

    public synchronized String get() {
        nextIndex = (nextIndex + 1) % Integer.MAX_VALUE;
        return get(nextIndex);
    }
}
