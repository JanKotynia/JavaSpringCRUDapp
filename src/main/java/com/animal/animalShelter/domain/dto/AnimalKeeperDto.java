package com.animal.animalShelter.domain.dto;

import com.animal.animalShelter.domain.entities.Animal;

import java.util.List;
import java.util.UUID;

public record AnimalKeeperDto(
        UUID id, String name, String surname, String pesel, Integer count, List<AnimalDto> animals
) {
}
