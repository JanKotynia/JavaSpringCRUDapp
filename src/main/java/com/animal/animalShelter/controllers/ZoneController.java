package com.animal.animalShelter.controllers;

import com.animal.animalShelter.domain.dto.ZoneDto;
import com.animal.animalShelter.domain.entities.Zone;
import com.animal.animalShelter.mappers.ZoneMapper;
import com.animal.animalShelter.services.ZoneService;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.hibernate.jdbc.Expectation;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ZoneDto> getZoneById(@PathVariable("id") UUID zoneId){
        Optional<Zone> zone = zoneService.getZonesById(zoneId);
        return zone.map(zoneEntity -> {
            ZoneDto zoneDto = zoneMapper.toDto(zoneEntity);
            return new ResponseEntity<>(zoneDto, HttpStatusCode.valueOf(200));
        }).orElse(new ResponseEntity<>(HttpStatusCode.valueOf(404)));
    }

    @PostMapping
    public ResponseEntity<ZoneDto> createZone(@RequestBody ZoneDto zoneDto){
        Zone createdZone = zoneService.createZone(
                zoneMapper.fromDto(zoneDto)
        );
        return new ResponseEntity<>(zoneMapper.toDto(createdZone), HttpStatusCode.valueOf(201));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ZoneDto> updateZone(@PathVariable("id") UUID zoneId, @RequestBody ZoneDto zoneDto){
        if(!zoneService.isExist(zoneId))
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));

        Zone updatedZone = zoneService.updateZone(
                zoneId,
                zoneMapper.fromDto(zoneDto)
        );

        return new ResponseEntity<>(zoneMapper.toDto(updatedZone), HttpStatusCode.valueOf(201));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<ZoneDto> deleteZone(@PathVariable("id") UUID zoneId){
        zoneService.deleteZone(zoneId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }
}
