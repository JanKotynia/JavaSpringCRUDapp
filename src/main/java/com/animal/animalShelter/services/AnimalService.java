package com.animal.animalShelter.services;

import com.animal.animalShelter.domain.entities.Animal;

import java.util.List;
import java.util.UUID;

public interface AnimalService {
    List<Animal> listAnimals();
    List<Animal> listAnimalsByZone(UUID zoneId);
    List<Animal> listAnimalsByKeeper(UUID keeperId);
    Animal createAnimal(UUID zoneId, UUID keeperId, Animal animal);

}
