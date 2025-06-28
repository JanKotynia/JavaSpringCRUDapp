package com.animal.animalShelter.controllers;

import com.animal.animalShelter.mappers.AnimalKeeperMapper;
import com.animal.animalShelter.mappers.ZoneMapper;
import com.animal.animalShelter.services.AnimalKeeperService;
import com.animal.animalShelter.services.ZoneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/all")
public class DefaultController {
    private final AnimalKeeperService animalKeeperService;
    private final AnimalKeeperMapper animalKeeperMapper;
    private final ZoneService zoneService;
    private final ZoneMapper zoneMapper;

    public DefaultController(AnimalKeeperService animalKeeperService, AnimalKeeperMapper animalKeeperMapper, ZoneService zoneService, ZoneMapper zoneMapper) {
        this.animalKeeperService = animalKeeperService;
        this.animalKeeperMapper = animalKeeperMapper;
        this.zoneService = zoneService;
        this.zoneMapper = zoneMapper;
    }

    @GetMapping
    public List<Object> getData(){
        List<Object> list1 = animalKeeperService.listAnimalKeepers()
                .stream()
                .map(animalKeeperMapper::toDto)
                .map(dto -> (Object) dto)
                .toList();

        List<Object> list2 = zoneService.listZones()
                .stream()
                .map(zoneMapper::toDto)
                .map(dto -> (Object) dto)
                .toList();

        list1.addAll(list2);
        return list1;
    }
}
