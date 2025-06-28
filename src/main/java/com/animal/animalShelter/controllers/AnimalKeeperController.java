package com.animal.animalShelter.controllers;

import com.animal.animalShelter.domain.dto.AnimalKeeperDto;
import com.animal.animalShelter.domain.entities.AnimalKeeper;
import com.animal.animalShelter.mappers.AnimalKeeperMapper;
import com.animal.animalShelter.services.AnimalKeeperService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.awt.*;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/animalKeepers")
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
    public Optional<AnimalKeeperDto> getAnimalkeeper(@PathVariable("id")UUID animalKeeperId){
        return animalKeeperService.getAnimalKeeper(animalKeeperId).map(animalKeeperMapper::toDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteAnimalKeeper(@PathVariable("id")UUID animalKeeperId){
        animalKeeperService.deleteAnimalKeeper(animalKeeperId);
    }

    @PostMapping
    public AnimalKeeperDto createAnimalKeeper(@RequestBody AnimalKeeperDto animalKeeperDto){
        AnimalKeeper createdAnimalKeeper = animalKeeperService.createAnimalKeeper(
                animalKeeperMapper.fromDto(animalKeeperDto)
        );
        return animalKeeperMapper.toDto(createdAnimalKeeper);
    }

    @PostMapping(path = "/{id}")
    public AnimalKeeperDto updateAnimalKeeper(@PathVariable("id") UUID animalKeeperId, @RequestBody AnimalKeeperDto animalKeeperDto){
        AnimalKeeper updatedAnimalKeeper = animalKeeperService.updateAnimalKeeper(
                animalKeeperId,
                animalKeeperMapper.fromDto(animalKeeperDto)
        );

        return animalKeeperDto; //khkdjkgjskfgjksgfjksgfjgfksjsfkjgkijgfkjgfkjgfsgfjkisgfjkgsfjksgfj
    }
}
