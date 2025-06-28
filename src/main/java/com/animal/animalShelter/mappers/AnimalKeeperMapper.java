package com.animal.animalShelter.mappers;

import com.animal.animalShelter.domain.dto.AnimalKeeperDto;
import com.animal.animalShelter.domain.entities.AnimalKeeper;

public interface AnimalKeeperMapper {
    AnimalKeeper fromDto(AnimalKeeperDto animalKeeperDto);
    AnimalKeeperDto toDto(AnimalKeeper animalKeeper);
}
