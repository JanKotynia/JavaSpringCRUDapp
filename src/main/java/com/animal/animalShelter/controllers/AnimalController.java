package com.animal.animalShelter.controllers;

import com.animal.animalShelter.domain.dto.AnimalDto;
import com.animal.animalShelter.domain.entities.Animal;
import com.animal.animalShelter.domain.entities.Zone;
import com.animal.animalShelter.mappers.AnimalMapper;
import com.animal.animalShelter.services.AnimalService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping(path = "/{id}")
    public ResponseEntity<AnimalDto> getAnimalById(@PathVariable("id")UUID animalId) {
        Optional<Animal> animal = animalService.getAnimalById(animalId);
        return animal.map(animalEntity -> {
            AnimalDto animalDto = animalMapper.toDto(animalEntity);
            return new ResponseEntity<>(animalDto, HttpStatusCode.valueOf(200));
                }).orElse(new ResponseEntity<>(HttpStatusCode.valueOf(404)));
    }

    @PostMapping(path = "/{zone_id}/{keeper_id}")
    public ResponseEntity<AnimalDto> createAnimal(
            @PathVariable("zone_id") UUID zoneId, @PathVariable("keeper_id") UUID keeperId,
            @RequestBody AnimalDto animalDto){
        Animal createdAnimal = animalService.createAnimal(
                zoneId,
                keeperId,
                animalMapper.fromDto(animalDto)
        );
        return new ResponseEntity<>(animalMapper.toDto(createdAnimal), HttpStatusCode.valueOf(201));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AnimalDto> updateAnimal(
            @PathVariable("id") UUID animalId, @RequestBody AnimalDto animalDto){
        if(!animalService.isExist(animalId))
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));

        Animal updatedAnimal = animalService.updateAnimal(
                animalId,
                animalMapper.fromDto(animalDto)
        );

        return new ResponseEntity<>(animalMapper.toDto(updatedAnimal), HttpStatusCode.valueOf(201));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AnimalDto> deleteAnimal(@PathVariable("id") UUID aniamId){
        animalService.deleteAnimal(aniamId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }
}
