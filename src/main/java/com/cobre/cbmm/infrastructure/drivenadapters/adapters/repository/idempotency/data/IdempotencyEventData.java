package com.cobre.cbmm.infrastructure.drivenadapters.adapters.repository.idempotency.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "idempotent_events")
@Builder(toBuilder = true)
public class IdempotencyEventData {

    @Id
    private String eventId;
}
