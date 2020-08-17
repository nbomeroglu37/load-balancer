package com.example.load_balancer.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RoundRobinLoadBalancerTests {
    @Test
    public void testRoundRobinLoadBalancer() {
        List<Provider> providers = new ArrayList<>();
        providers.add(new Provider());
        providers.add(new Provider());

        RoundRobinLoadBalancer roundRobinLoadBalancer = new RoundRobinLoadBalancer();
        roundRobinLoadBalancer.register(providers);
        String uuid1 = roundRobinLoadBalancer.get();
        String uuid2 = roundRobinLoadBalancer.get();
        String uuid3 = roundRobinLoadBalancer.get();

        assertNotEquals(uuid1, uuid2);
        assertEquals(uuid1, uuid3);
    }
}
