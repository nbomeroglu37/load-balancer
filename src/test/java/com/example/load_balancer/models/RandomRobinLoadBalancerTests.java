package com.example.load_balancer.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RandomRobinLoadBalancerTests {
    @Test
    public void testRandomRobinLoadBalancer() {
        List<Provider> providers = new ArrayList<>();
        providers.add(new Provider());

        RandomLoadBalancer randomLoadBalancer = new RandomLoadBalancer();
        randomLoadBalancer.register(providers);
        String uuid1 = randomLoadBalancer.get();
        String uuid2 = randomLoadBalancer.get();

        assertEquals(uuid1, uuid2);
    }

}
