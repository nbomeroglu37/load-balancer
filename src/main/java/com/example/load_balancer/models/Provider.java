package com.example.load_balancer.models;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static com.example.load_balancer.utils.Constants.*;

public class Provider {

    private final String id;
    private boolean alive;
    private int successfulHealthChecks;

    public Provider() {
        id = UUID.randomUUID().toString();
        alive = true;
        successfulHealthChecks = 0;
    }

    public String get() {
        return id;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean check() {
        boolean currentIsAlive = new Random().nextBoolean();
        if (alive == currentIsAlive) {
            return currentIsAlive;
        }

        if (!currentIsAlive) {
            alive = false;
            successfulHealthChecks = 0;
        } else {
            successfulHealthChecks ++;
            if (successfulHealthChecks >= MUST_SUCCEED_HEARTBEAT_CHECK_COUNT) {
                alive = true;
            }
        }

        return alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Provider provider = (Provider) o;
        return Objects.equals(id, provider.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
