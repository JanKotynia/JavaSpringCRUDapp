package com.animal.animalShelter.mappers.impl;

import com.animal.animalShelter.domain.dto.AnimalKeeperDto;
import com.animal.animalShelter.domain.entities.AnimalKeeper;
import com.animal.animalShelter.mappers.AnimalKeeperMapper;
import com.animal.animalShelter.mappers.AnimalMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AnimalKeeperMapperImpl implements AnimalKeeperMapper {
    private final AnimalMapper animalMapper;

    public AnimalKeeperMapperImpl(AnimalMapper animalMapper) {
        this.animalMapper = animalMapper;
    }

    @Override
    public AnimalKeeper fromDto(AnimalKeeperDto animalKeeperDto) {
        return new AnimalKeeper(
          animalKeeperDto.id(),
          animalKeeperDto.name(),
          animalKeeperDto.surname(),
          animalKeeperDto.pesel(),
          Optional.ofNullable(animalKeeperDto.animals())
                .map(animals -> animals.stream()
                        .map(animalMapper::fromDto)
                        .toList()
                ).orElse(null)
        );
    }

    @Override
    public AnimalKeeperDto toDto(AnimalKeeper animalKeeper) {
        return new AnimalKeeperDto(
                animalKeeper.getId(),
                animalKeeper.getName(),
                animalKeeper.getSurname(),
                animalKeeper.getPesel(),
                Optional.ofNullable(animalKeeper.getAnimals())
                        .map(List::size)
                        .orElse(0),
                Optional.ofNullable(animalKeeper.getAnimals())
                        .map(animals ->
                                animals.stream().map(animalMapper::toDto).toList()
                        ).orElse(null)
        );
    }
}
