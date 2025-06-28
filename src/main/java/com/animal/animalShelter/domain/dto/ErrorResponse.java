package com.animal.animalShelter.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
