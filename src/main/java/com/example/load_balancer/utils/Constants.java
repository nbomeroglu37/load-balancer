package com.example.load_balancer.utils;

public final class Constants {
    //the maximum number of providers accepted from the load balancer is 10
    public static final int MAX_PROVIDER_COUNT = 10;
    public static final int CHECK_TIME_INTERVAL_MS = 1000;
    public static final int MUST_SUCCEED_HEARTBEAT_CHECK_COUNT = 2;
    public static final int MAX_PARALLEL_REQUEST_PER_PROVIDER = 5;

    public static final int SIMULATION_OF_WORK_SLEEP_MS = 10;

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
