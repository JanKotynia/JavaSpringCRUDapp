package com.animal.animalShelter.domain.dto;

import com.animal.animalShelter.domain.entities.Species;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AnimalDto(
    UUID id,
    String name,
    String description,
    LocalDateTime admissionDate,
    Species species,
    UUID zoneId,
    UUID keeperId
){
}
