package com.cobre.cbmm.infrastructure.drivenadapters.adapters;

import com.cobre.cbmm.domain.models.idempotency.gateway.IdempotencyPort;
import com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.idempotency.IdempotencyDataDAO;
import com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.idempotency.data.IdempotencyEventData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdempotencyAdapter implements IdempotencyPort {

    private final IdempotencyDataDAO idempotencyDataDAO;

    @Override
    public boolean isProcessed(String eventId) {
        return idempotencyDataDAO.findById(eventId).isPresent();
    }

    @Override
    public void save(String eventId) {

        IdempotencyEventData idempotencyEventData = IdempotencyEventData
                .builder()
                .eventId(eventId)
                .build();

        idempotencyDataDAO.save(idempotencyEventData);
    }
}
