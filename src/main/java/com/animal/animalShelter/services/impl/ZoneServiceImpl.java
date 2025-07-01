package com.animal.animalShelter.services.impl;

import com.animal.animalShelter.domain.entities.Zone;
import com.animal.animalShelter.repositories.ZoneRepository;
import com.animal.animalShelter.services.ZoneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {
    private final ZoneRepository zoneRepository;

    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public List<Zone> listZones() {
        return zoneRepository.findAll();
    }

    @Override
    public Zone createZone(Zone zone) {
        if(null != zone.getId()) {
            throw new IllegalArgumentException("Zone already has an ID!");
        }
        if(null == zone.getName() || zone.getName().isBlank()) {
            throw new IllegalArgumentException("Empty zone name!");
        }

        return zoneRepository.save(new Zone(
                null,
                zone.getName(),
                zone.getSize(),
                zone.getDescription(),
                null
        ));
    }

    @Override
    public Optional<Zone> getZonesById(UUID id) {
        return zoneRepository.findById(id);
    }

    @Override
    public Zone updateZone(UUID zoneId, Zone zone) {
        if(null == zone.getId()) {
            throw new IllegalArgumentException("Zone must have an ID");
        }

        if(!Objects.equals(zone.getId(), zoneId)) {
            throw new IllegalArgumentException("Attempting to change zone ID, this is not permitted!");
        }

        Zone existingZone = zoneRepository.findById(zoneId).orElseThrow(() ->
                new IllegalArgumentException("Zone not found!"));

        existingZone.setName(zone.getName());
        existingZone.setDescription(zone.getDescription());
        existingZone.setSize(zone.getSize());
        return zoneRepository.save(existingZone);
    }

    @Override
    public void deleteZone(UUID zoneId) {
        zoneRepository.deleteById(zoneId);
    }

    @Override
    public boolean isExist(UUID zoneId) {
        return zoneRepository.existsById(zoneId);
    }
}
