package com.animal.animalShelter.repositories;

import com.animal.animalShelter.domain.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, UUID>{
    Optional<Animal> findById(UUID id);
    List<Animal> findByZoneId(UUID zoneId);
    Optional<Animal> findByZoneIdAndId(UUID zoneId, UUID id);
    List<Animal> findByAnimalKeeperId(UUID animalKeeperId);
    Optional<Animal> findByAnimalKeeperIdAndId(UUID animalKeeperId, UUID id);
}
