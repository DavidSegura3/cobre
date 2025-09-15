package com.cobre.cbmm.domain.usecases;

import com.cobre.cbmm.domain.models.movement.CrossBorderMovementEvent;

public interface ProcessCrossBorderMovementUseCase {

    void process(CrossBorderMovementEvent event);
}
