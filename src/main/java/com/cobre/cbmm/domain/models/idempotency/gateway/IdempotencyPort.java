package com.cobre.cbmm.domain.models.idempotency.gateway;

public interface IdempotencyPort {

    boolean isProcessed(String eventId);
    void save(String eventId);
}
