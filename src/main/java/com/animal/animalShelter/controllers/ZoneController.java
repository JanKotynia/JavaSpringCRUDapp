package com.animal.animalShelter.controllers;

import com.animal.animalShelter.domain.dto.ZoneDto;
import com.animal.animalShelter.domain.entities.Zone;
import com.animal.animalShelter.mappers.ZoneMapper;
import com.animal.animalShelter.services.ZoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/zones")
public class ZoneController {
    private final ZoneMapper zoneMapper;
    private final ZoneService zoneService;

    public ZoneController(ZoneMapper zoneMapper, ZoneService zoneService) {
        this.zoneMapper = zoneMapper;
        this.zoneService = zoneService;
    }


    @GetMapping
    public List<ZoneDto> listZones(){
        return zoneService.listZones()
                .stream()
                .map(zoneMapper::toDto)
                .toList();
    }

    @GetMapping(path = "/{id}")
    public Optional<ZoneDto> listZonesById(@PathVariable("id") UUID zoneId){
        return zoneService.getZonesById(zoneId).map(zoneMapper::toDto);
    }

    @PostMapping
    public ZoneDto createZone(@RequestBody ZoneDto zoneDto){
        Zone createdZone = zoneService.createZone(
                zoneMapper.fromDto(zoneDto)
        );
        return zoneMapper.toDto(createdZone);
    }

    @PutMapping(path = "/{id}")
    public ZoneDto updateZone(@PathVariable("id") UUID zoneId, @RequestBody ZoneDto zoneDto){
        Zone updatedZone = zoneService.updateZone(
                zoneId,
                zoneMapper.fromDto(zoneDto)
        );

        return zoneMapper.toDto(updatedZone);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteZone(@PathVariable("id") UUID zoneId){
        zoneService.deleteZone(zoneId);
    }
}
