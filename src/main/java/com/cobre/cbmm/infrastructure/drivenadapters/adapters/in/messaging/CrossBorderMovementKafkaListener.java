package com.cobre.cbmm.infrastructure.drivenadapters.adapters.in.messaging;

import com.cobre.cbmm.domain.models.movement.CrossBorderMovementEvent;
import com.cobre.cbmm.domain.usecases.ProcessCrossBorderMovementUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableRetry
@Slf4j
public class CrossBorderMovementKafkaListener {

    private final ProcessCrossBorderMovementUseCase processCrossBorderMovementUseCase;

    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            autoCreateTopics = "false"
    )
    @KafkaListener(topics = "cbmm.requests", groupId = "accounts-module-group")
    public void listen(@Payload CrossBorderMovementEvent event) {

        log.info("Received CBMM event: {}", event.getEventId());

        try {
            processCrossBorderMovementUseCase.process(event);
            log.info("Successfully processed CBMM event: {}", event.getEventId());
        } catch (Exception e) {
            log.error("Failed to process CBMM event: {}", event.getEventId(), e);
        }
    }
}
