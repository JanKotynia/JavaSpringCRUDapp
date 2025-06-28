package com.animal.animalShelter.mappers;

import com.animal.animalShelter.domain.dto.ZoneDto;
import com.animal.animalShelter.domain.entities.Zone;

public interface ZoneMapper {
    ZoneDto toDto(Zone zone);

    Zone fromDto(ZoneDto zoneDto);
}
