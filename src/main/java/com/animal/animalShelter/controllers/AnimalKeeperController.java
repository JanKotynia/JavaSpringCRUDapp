package com.animal.animalShelter.controllers;

import com.animal.animalShelter.domain.dto.AnimalKeeperDto;
import com.animal.animalShelter.domain.entities.AnimalKeeper;
import com.animal.animalShelter.mappers.AnimalKeeperMapper;
import com.animal.animalShelter.services.AnimalKeeperService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.awt.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/keepers")
public class AnimalKeeperController {
    private final AnimalKeeperService animalKeeperService;
    private final AnimalKeeperMapper animalKeeperMapper;

    public AnimalKeeperController(AnimalKeeperService animalKeeperService, AnimalKeeperMapper animalKeeperMapper) {
        this.animalKeeperService = animalKeeperService;
        this.animalKeeperMapper = animalKeeperMapper;
    }

    @GetMapping
    public List<AnimalKeeperDto> listAnimalKeepers(){
        return animalKeeperService.listAnimalKeepers()
                .stream()
                .map(animalKeeperMapper::toDto)
                .toList();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AnimalKeeperDto> getAnimalkeeper(@PathVariable("id")UUID animalKeeperId){
        Optional<AnimalKeeper> animalKeeper = animalKeeperService.getAnimalKeeper(animalKeeperId);

        return animalKeeper.map(animalKeeperEntity -> {
            AnimalKeeperDto animalKeeperDto = animalKeeperMapper.toDto(animalKeeperEntity);
            return new ResponseEntity<>(animalKeeperDto, HttpStatusCode.valueOf(201));
        }).orElse(new ResponseEntity<>(HttpStatusCode.valueOf(404)));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<AnimalKeeperDto> deleteAnimalKeeper(@PathVariable("id")UUID animalKeeperId){
        animalKeeperService.deleteAnimalKeeper(animalKeeperId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }

    @PostMapping
    public ResponseEntity<AnimalKeeperDto> createAnimalKeeper(@RequestBody AnimalKeeperDto animalKeeperDto){
        AnimalKeeper createdAnimalKeeper = animalKeeperService.createAnimalKeeper(
                animalKeeperMapper.fromDto(animalKeeperDto)
        );
        return new ResponseEntity<>(animalKeeperMapper.toDto(createdAnimalKeeper), HttpStatusCode.valueOf(201));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AnimalKeeperDto> updateAnimalKeeper(@PathVariable("id") UUID animalKeeperId, @RequestBody AnimalKeeperDto animalKeeperDto){
        if(!animalKeeperService.isExist(animalKeeperId))
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));

        AnimalKeeper updatedAnimalKeeper = animalKeeperService.updateAnimalKeeper(
                animalKeeperId,
                animalKeeperMapper.fromDto(animalKeeperDto)
        );

        return new ResponseEntity<>(animalKeeperMapper.toDto(updatedAnimalKeeper), HttpStatusCode.valueOf(201));
    }
}
