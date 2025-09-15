package com.cobre.cbmm.infrastructure.drivenadapters.adapters.in.file;

import com.cobre.cbmm.domain.models.movement.CrossBorderMovementEvent;
import com.cobre.cbmm.domain.usecases.ProcessCrossBorderMovementUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@RequiredArgsConstructor
public class FileProcessorService {

    private final ProcessCrossBorderMovementUseCase processCrossBorderMovementUseCase;
    private final ObjectMapper objectMapper;
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    public void processEventsFromFile(File inputFile) throws Exception {

        CrossBorderMovementEvent[] events = objectMapper.readValue(inputFile, CrossBorderMovementEvent[].class);
        List.of(events).forEach(event -> executor.submit(() -> processCrossBorderMovementUseCase.process(event)));
    }
}
