package com.animal.animalShelter.domain.dto;

import com.animal.animalShelter.domain.entities.Animal;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ZoneDto(
        UUID id,
        String name,
        Integer size,
        String description,
        Integer count,
        List<AnimalDto> animals
) {
}
