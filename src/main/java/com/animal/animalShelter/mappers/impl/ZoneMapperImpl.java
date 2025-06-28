package com.animal.animalShelter.mappers.impl;

import com.animal.animalShelter.domain.dto.ZoneDto;
import com.animal.animalShelter.domain.entities.Zone;
import com.animal.animalShelter.mappers.AnimalMapper;
import com.animal.animalShelter.mappers.ZoneMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ZoneMapperImpl implements ZoneMapper {
    private final AnimalMapper animalMapper;

    public ZoneMapperImpl(AnimalMapper animalMapper) {
        this.animalMapper = animalMapper;
    }


    @Override
    public ZoneDto toDto(Zone zone) {
        return new ZoneDto(
                zone.getId(),
                zone.getName(),
                zone.getSize(),
                zone.getDescription(),
                Optional.ofNullable(zone.getAnimals())
                        .map(List::size)
                        .orElse(0),
                Optional.ofNullable(zone.getAnimals())
                        .map(animals ->
                                animals.stream().map(animalMapper::toDto).toList()
                        ).orElse(null)
        );
    }

    @Override
    public Zone fromDto(ZoneDto zoneDto) {
        return new Zone(
            zoneDto.id(),
            zoneDto.name(),
            zoneDto.size(),
            zoneDto.description(),
            Optional.ofNullable(zoneDto.animals())
                    .map(animals -> animals.stream()
                            .map(animalMapper::fromDto)
                            .toList()
                    ).orElse(null)
        );
    }


}
