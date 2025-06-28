package com.animal.animalShelter.repositories;

import com.animal.animalShelter.domain.entities.AnimalKeeper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnimalKeeperRepository extends JpaRepository<AnimalKeeper, UUID> {
}
