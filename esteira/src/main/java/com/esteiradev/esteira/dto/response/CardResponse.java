package com.esteiradev.esteira.dto.response;

import com.esteiradev.esteira.enums.PriorityEnum;
import com.esteiradev.esteira.enums.StatusCard;

import java.time.LocalDateTime;
import java.util.UUID;

public record CardResponse(
        UUID id,
        String title,
        String description,
        Integer position,
        StatusCard status,
        Integer estimateHours,
        Integer hoursUsed,
        Integer hoursRemainning,
        LocalDateTime dateCreate,
        LocalDateTime  dateUpdated,
        LocalDateTime  dateResolved,
        PriorityEnum priority,
        UUID esteiraID
) {}
