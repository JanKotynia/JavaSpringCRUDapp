package com.animal.animalShelter.mappers;

import com.animal.animalShelter.domain.dto.AnimalDto;
import com.animal.animalShelter.domain.entities.Animal;

public interface AnimalMapper {
    Animal fromDto(AnimalDto animalDto);

    AnimalDto toDto(Animal animal);
}
