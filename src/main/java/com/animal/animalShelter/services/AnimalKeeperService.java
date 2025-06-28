package com.animal.animalShelter.services;

import com.animal.animalShelter.domain.dto.AnimalKeeperDto;
import com.animal.animalShelter.domain.entities.AnimalKeeper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnimalKeeperService {
    List<AnimalKeeper> listAnimalKeepers();
    Optional<AnimalKeeper> getAnimalKeeper(UUID animalKeeperId);
    AnimalKeeper createAnimalKeeper(AnimalKeeper animalKeeper);
    void deleteAnimalKeeper(UUID animalKeeperId);
    AnimalKeeper updateAnimalKeeper(UUID animalKeeperId, AnimalKeeper animalKeeper);
}
