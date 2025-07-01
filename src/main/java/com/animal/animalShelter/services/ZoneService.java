package com.animal.animalShelter.services;

import com.animal.animalShelter.domain.entities.Zone;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ZoneService {
    List<Zone> listZones();
    Zone createZone(Zone zone);
    Optional<Zone> getZonesById(UUID id);
    Zone updateZone(UUID zoneId, Zone zone);
    void deleteZone(UUID zoneId);
    boolean isExist(UUID zoneId);
}
