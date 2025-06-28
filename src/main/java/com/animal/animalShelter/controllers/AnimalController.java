package com.animal.animalShelter.controllers;

import com.animal.animalShelter.domain.dto.AnimalDto;
import com.animal.animalShelter.domain.entities.Animal;
import com.animal.animalShelter.mappers.AnimalMapper;
import com.animal.animalShelter.services.AnimalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/animals")
public class AnimalController {

    private final AnimalMapper animalMapper;
    private final AnimalService animalService;

    public AnimalController(AnimalMapper animalMapper, AnimalService animalService) {
        this.animalMapper = animalMapper;
        this.animalService = animalService;
    }

    @GetMapping
    public List<AnimalDto> listAnimals(){
        return animalService.listAnimals()
                .stream()
                .map(animalMapper::toDto)
                .toList();
    }

    @GetMapping(path = "/zone/{id}")
    public List<AnimalDto> listAnimalsByZone(@PathVariable("id") UUID zoneId) {
        return animalService.listAnimalsByZone(zoneId)
                .stream()
                .map(animalMapper::toDto)
                .toList();
    }

    @GetMapping(path = "/keeper/{id}")
    public List<AnimalDto> listAnimalsByKeeper(@PathVariable("id") UUID keeperId) {
        return animalService.listAnimalsByKeeper(keeperId)
                .stream()
                .map(animalMapper::toDto)
                .toList();
    }

    @PostMapping(path = "/{zone_id}/{keeper_id}")
    public AnimalDto createAnimal(
            @PathVariable("zone_id") UUID zoneId, @PathVariable("keeper_id") UUID keeperId,
            @RequestBody AnimalDto animalDto){
        Animal createdAnimal = animalService.createAnimal(
                zoneId,
                keeperId,
                animalMapper.fromDto(animalDto)
        );
        return animalMapper.toDto(createdAnimal);
    }
}
