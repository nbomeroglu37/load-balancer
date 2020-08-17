package com.example.load_balancer.models;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

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

    @Test
    public void testRoundRobinLoadBalancerManualInclude() {
        List<Provider> providers = new ArrayList<>();
        Provider willBeIncluded = new Provider();
        String specialUuid = willBeIncluded.get();

        providers.add(new Provider());
        providers.add(new Provider());

        RoundRobinLoadBalancer roundRobinLoadBalancer = new RoundRobinLoadBalancer();
        roundRobinLoadBalancer.register(providers);
        roundRobinLoadBalancer.manualInclude(willBeIncluded);

        Set<String> uuids = new HashSet<>();
        uuids.add(roundRobinLoadBalancer.get());
        uuids.add(roundRobinLoadBalancer.get());
        uuids.add(roundRobinLoadBalancer.get());

        assertTrue(uuids.contains(specialUuid));
    }


    @Test
    public void testRoundRobinLoadBalancerManualExclude() {
        List<Provider> providers = new ArrayList<>();
        Provider willBeExcluded = new Provider();
        String specialUuid = willBeExcluded.get();

        providers.add(new Provider());
        providers.add(new Provider());
        providers.add(willBeExcluded);

        RoundRobinLoadBalancer roundRobinLoadBalancer = new RoundRobinLoadBalancer();
        roundRobinLoadBalancer.register(providers);
        roundRobinLoadBalancer.manualExclude(willBeExcluded);

        Set<String> uuids = new HashSet<>();
        uuids.add(roundRobinLoadBalancer.get());
        uuids.add(roundRobinLoadBalancer.get());
        uuids.add(roundRobinLoadBalancer.get());

        assertFalse(uuids.contains(specialUuid));
    }
}
