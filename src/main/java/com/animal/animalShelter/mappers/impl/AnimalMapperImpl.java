package com.animal.animalShelter.mappers.impl;

import com.animal.animalShelter.domain.dto.AnimalDto;
import com.animal.animalShelter.domain.entities.Animal;
import com.animal.animalShelter.mappers.AnimalMapper;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapperImpl implements AnimalMapper {
    @Override
    public Animal fromDto(AnimalDto animalDto) {
        return new Animal(
                animalDto.id(),
                animalDto.name(),
                animalDto.description(),
                null,
                animalDto.species(),
                null,
                null
        );
    }

    @Override
    public AnimalDto toDto(Animal animal) {
        return new AnimalDto(
              animal.getId(),
              animal.getName(),
              animal.getDescription(),
              animal.getAdmissionDate(),
              animal.getSpecies(),
              animal.getZone(),
              animal.getAnimalKeeper()
        );
    }
}
