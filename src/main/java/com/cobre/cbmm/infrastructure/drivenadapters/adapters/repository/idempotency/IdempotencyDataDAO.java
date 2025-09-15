package com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.idempotency;

import com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.idempotency.data.IdempotencyEventData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdempotencyDataDAO extends JpaRepository<IdempotencyEventData, String> {

    Optional<IdempotencyEventData> findByEventId(String eventId);
}
