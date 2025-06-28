package com.animal.animalShelter.services.impl;

import com.animal.animalShelter.domain.entities.AnimalKeeper;
import com.animal.animalShelter.domain.entities.Zone;
import com.animal.animalShelter.repositories.AnimalKeeperRepository;
import com.animal.animalShelter.services.AnimalKeeperService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnimalKeeperServiceImpl implements AnimalKeeperService {

    private final AnimalKeeperRepository animalKeeperRepository;

    public AnimalKeeperServiceImpl(AnimalKeeperRepository animalKeeperRepository) {
        this.animalKeeperRepository = animalKeeperRepository;
    }

    @Override
    public List<AnimalKeeper> listAnimalKeepers() {
        return animalKeeperRepository.findAll();
    }

    @Override
    public Optional<AnimalKeeper> getAnimalKeeper(UUID animalKeeperId) {
        return animalKeeperRepository.findById(animalKeeperId);
    }

    @Override
    public AnimalKeeper createAnimalKeeper(AnimalKeeper animalKeeper) {
        if(null != animalKeeper.getId()) {
            throw new IllegalArgumentException("Animal keeper already has an ID!");
        }
        if(null == animalKeeper.getName() || animalKeeper.getName().isBlank()) {
            throw new IllegalArgumentException("Empty keeper name!");
        }

        return animalKeeperRepository.save( new AnimalKeeper(
                null,
                animalKeeper.getName(),
                animalKeeper.getSurname(),
                animalKeeper.getPesel(),
                null
                ));
    }

    @Override
    public void deleteAnimalKeeper(UUID animalKeeperId) {
        animalKeeperRepository.deleteById(animalKeeperId);
    }

    @Override
    public AnimalKeeper updateAnimalKeeper(UUID animalKeeperId, AnimalKeeper animalKeeper) {
        if(null == animalKeeper.getId()) {
            throw new IllegalArgumentException("AnimalKeeper must have an ID");
        }

        if(!Objects.equals(animalKeeper.getId(), animalKeeperId)) {
            throw new IllegalArgumentException("Attempting to change animal keeper ID, this is not permitted!");
        }

        AnimalKeeper existingAnimalKeeper = animalKeeperRepository.findById(animalKeeperId).orElseThrow(() ->
                new IllegalArgumentException("Animal Keeper not found!"));

        existingAnimalKeeper.setName(animalKeeper.getName());
        existingAnimalKeeper.setSurname(animalKeeper.getSurname());
        existingAnimalKeeper.setPesel(animalKeeper.getPesel());
        return animalKeeperRepository.save(existingAnimalKeeper);
    }
}
