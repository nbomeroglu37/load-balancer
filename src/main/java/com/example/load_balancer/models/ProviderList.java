package com.example.load_balancer.models;

import com.example.load_balancer.exceptions.NotEnoughCapacityRuntimeException;

import java.util.*;

public class ProviderList {

    private int maxCapacity;

    private List<Provider> unmodifiableProviders;
    private Set<Provider> unmodifiableExcludedProviders;

    private List<Provider> newVersionOfProviders;
    private Set<Provider> newVersionOfExcludedProviders;

    public ProviderList(int maxCapacity) {
        this.maxCapacity = maxCapacity;

        unmodifiableProviders = Collections.unmodifiableList(new ArrayList<>());
        unmodifiableExcludedProviders = Collections.unmodifiableSet(new HashSet<>());

        newVersionOfProviders = null;
        newVersionOfExcludedProviders = null;
    }

    private synchronized List<Provider> getProviders() {
        if (newVersionOfProviders != null) {
            unmodifiableProviders = Collections.unmodifiableList(newVersionOfProviders);
            newVersionOfProviders = null;
        }
        return unmodifiableProviders;
    }

    private synchronized Set<Provider> getExcludedProviders() {
        if (newVersionOfExcludedProviders != null) {
            unmodifiableExcludedProviders = Collections.unmodifiableSet(newVersionOfExcludedProviders);
            newVersionOfExcludedProviders = null;
        }
        return unmodifiableExcludedProviders;
    }

    public synchronized void setProviders(List<Provider> providers) {
        newVersionOfProviders = providers;
    }

    public synchronized void setExcludedProviders(Set<Provider> excludedProviders) {
        newVersionOfExcludedProviders = excludedProviders;
    }

    public String getProvider(int index) {
        List<Provider> providers = getProviders();

        if (!providers.isEmpty()) {
            Provider provider = providers.get(index % providers.size());
            return provider.get();
        }
        return null;
    }

    public int size() {
        return getProviders().size();
    }

    public void add(Provider provider) {
        List<Provider> providers = getProviders();
        if (providers.size() > maxCapacity) {
            throw new NotEnoughCapacityRuntimeException();
        }

        List<Provider> providersPlus1 = new ArrayList<>(providers);
        providersPlus1.add(provider);

        setProviders(providersPlus1);
    }

    public boolean remove(Provider provider) {
        List<Provider> providers = getProviders();
        List<Provider> providersMinus1 = new ArrayList<>(providers);

        boolean isRemoved = providersMinus1.remove(provider);

        if (isRemoved) {
            setProviders(providersMinus1);
        }
        return isRemoved;
    }

    public void checkProviders() {

        Set<Provider> unhealthyProviders = new HashSet<>();
        // state changes: healthy -> unhealthy
        for (Provider provider : getProviders()) {
            if (!provider.check()) {
                unhealthyProviders.add(provider);
            }
        }

        Set<Provider> healthyProviders = new HashSet<>();
        // state changes: unhealthy -> healthy
        for (Provider provider : getExcludedProviders()) {
            if (provider.check()) {
                healthyProviders.add(provider);
            }
        }

        if (unhealthyProviders.isEmpty() && healthyProviders.isEmpty()) {
            return;
        }

        Set<Provider> modifiablePrevHealthyProviders = new HashSet<>(getProviders());
        Set<Provider> modifiablePrevUnhealthyProviders = new HashSet<>(getExcludedProviders());

        modifiablePrevHealthyProviders.removeAll(unhealthyProviders);
        modifiablePrevHealthyProviders.addAll(healthyProviders);

        modifiablePrevUnhealthyProviders.removeAll(healthyProviders);
        modifiablePrevUnhealthyProviders.addAll(unhealthyProviders);

        setProviders(new ArrayList<>(modifiablePrevHealthyProviders));
        setExcludedProviders(modifiablePrevUnhealthyProviders);
    }
}
