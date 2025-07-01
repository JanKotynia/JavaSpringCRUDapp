package com.animal.animalShelter.services.impl;

import com.animal.animalShelter.domain.entities.Animal;
import com.animal.animalShelter.domain.entities.AnimalKeeper;
import com.animal.animalShelter.domain.entities.Zone;
import com.animal.animalShelter.repositories.AnimalKeeperRepository;
import com.animal.animalShelter.repositories.AnimalRepository;
import com.animal.animalShelter.repositories.ZoneRepository;
import com.animal.animalShelter.services.AnimalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final ZoneRepository zoneRepository;
    private final AnimalKeeperRepository animalKeeperRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository, ZoneRepository zoneRepository, AnimalKeeperRepository animalKeeperRepository) {
        this.animalRepository = animalRepository;
        this.zoneRepository = zoneRepository;
        this.animalKeeperRepository = animalKeeperRepository;
    }


    @Override
    public List<Animal> listAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public Optional<Animal> getAnimalById(UUID animalId) {
        return animalRepository.findById(animalId);
    }

    @Override
    public List<Animal> listAnimalsByZone(UUID zoneId) {
        return animalRepository.findByZoneId(zoneId);
    }

    @Override
    public List<Animal> listAnimalsByKeeper(UUID keeperId) {
        return animalRepository.findByAnimalKeeperId(keeperId);
    }

    @Override
    public Animal createAnimal(UUID zoneId, UUID keeperId, Animal animal) {
        if(null != animal.getId()) {
            throw new IllegalArgumentException("Task already has an ID!");
        }
        if(null == animal.getName() || animal.getName().isBlank()) {
            throw new IllegalArgumentException("Task must have a title!");
        }

        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Zone ID provided!"));

        AnimalKeeper animalKeeper = animalKeeperRepository.findById(keeperId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid animal keeper ID provided!"));

        LocalDateTime now = LocalDateTime.now();
        Animal animalToSave = new Animal(
                null,
                animal.getName(),
                animal.getDescription(),
                now,
                animal.getSpecies(),
                zone,
                animalKeeper
        );

        return animalRepository.save(animalToSave);
    }

    @Override
    public Animal updateAnimal(UUID animalId, Animal animal) {
        if(null == animal.getId()) {
            throw new IllegalArgumentException("Animal must have an ID");
        }

        if(!Objects.equals(animal.getId(), animalId)) {
            throw new IllegalArgumentException("Attempting to change animal ID, this is not permitted!");
        }

        Animal existingAnimal = animalRepository.findById(animalId).orElseThrow(() ->
                new IllegalArgumentException("Animal not found!"));

        existingAnimal.setName(animal.getName());
        existingAnimal.setDescription(animal.getDescription());
        existingAnimal.setSpecies(animal.getSpecies());
        existingAnimal.setAdmissionDate(animal.getAdmissionDate());
        return animalRepository.save(existingAnimal);
    }

    @Override
    public void deleteAnimal(UUID animaId) {
        animalRepository.deleteById(animaId);
    }

    @Override
    public boolean isExist(UUID animalId) {
        return animalRepository.existsById(animalId);
    }
}
