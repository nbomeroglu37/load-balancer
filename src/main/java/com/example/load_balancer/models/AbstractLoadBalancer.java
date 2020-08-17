package com.example.load_balancer.models;

import com.example.load_balancer.exceptions.ConcurrentConnectionRuntimeException;
import com.example.load_balancer.exceptions.NoCapacityRuntimeException;
import com.example.load_balancer.utils.Constants;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.load_balancer.utils.Constants.MAX_PROVIDER_COUNT;

public abstract class AbstractLoadBalancer {
    private final ProviderList providerList;
    private AtomicInteger concurrentConnections;

    protected AbstractLoadBalancer() {
        providerList = new ProviderList(MAX_PROVIDER_COUNT);
        concurrentConnections = new AtomicInteger(0);

        final Timer healthChecktimer = new Timer();
        final TimerTask healthChecker = new TimerTask() {
            @Override
            public synchronized void run () {
                providerList.checkProviders();
            }
        };
        healthChecktimer.schedule(healthChecker, Constants.CHECK_TIME_INTERVAL_MS);
    }

    protected String get(int nextIndex) {
        try {
            int concurrentConnectionLimit = providerList.size() * Constants.MAX_PARALLEL_REQUEST_PER_PROVIDER;
            if (concurrentConnections.incrementAndGet() >= concurrentConnectionLimit ) {
                throw new ConcurrentConnectionRuntimeException();
            }

            String result = providerList.getProvider(nextIndex);
            if (result == null) {
                throw new NoCapacityRuntimeException();
            }
            return result;

        } finally {
            concurrentConnections.decrementAndGet();
        }
    }

    protected abstract String get();

    // Register a list of provider instances to the Load Balancer
    public void register(List<Provider> providers) {
        providerList.setProviders(providers.subList(0, Math.min(MAX_PROVIDER_COUNT, providers.size())));
    }

    public void manualInclude(Provider provider) {
        providerList.add(provider);
    }

    public boolean manualExclude(Provider provider) {
        return providerList.remove(provider);
    }
}

