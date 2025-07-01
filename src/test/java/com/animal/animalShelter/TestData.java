package com.animal.animalShelter;

import com.animal.animalShelter.domain.dto.AnimalDto;
import com.animal.animalShelter.domain.dto.AnimalKeeperDto;
import com.animal.animalShelter.domain.dto.ZoneDto;
import com.animal.animalShelter.domain.entities.Animal;
import com.animal.animalShelter.domain.entities.AnimalKeeper;
import com.animal.animalShelter.domain.entities.Species;
import com.animal.animalShelter.domain.entities.Zone;

import java.time.LocalDateTime;

public final class TestData {

    private TestData(){}

    public static Animal CreateTestAnimalA(final Zone zone, final AnimalKeeper animalKeeper){
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 15, 30, 0);
        return Animal.builder()
                .name("Jeff")
                .description("Nice dog")
                .admissionDate(dateTime)
                .species(Species.DOG)
                .zone(zone)
                .animalKeeper(animalKeeper)
                .build();
    }

    public static Animal CreateTestAnimalB(final Zone zone, final AnimalKeeper animalKeeper){
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 15, 30, 0);
        return Animal.builder()
                .name("Beff")
                .description("Nice cat")
                .admissionDate(dateTime)
                .species(Species.CAT)
                .zone(zone)
                .animalKeeper(animalKeeper)
                .build();
    }


    public static Zone CreateTestZoneA(){
        return Zone.builder()
                .name("ZoneA")
                .description("Big zone")
                .size(0)
                .build();
    }

    public static Zone CreateTestZoneB(){
        return Zone.builder()
                .name("ZoneB")
                .description("Small zone")
                .size(0)
                .build();
    }

    public static AnimalKeeper CreateTestAnimalKeeperA(){
        return AnimalKeeper.builder()
                .name("Bob")
                .surname("Smith")
                .pesel("12345")
                .build();
    }

    public static AnimalKeeper CreateTestAnimalKeeperB(){
        return AnimalKeeper.builder()
                .name("Sam")
                .surname("Smith")
                .pesel("54321")
                .build();
    }

    public static AnimalDto CreateTestAnimalDto(final ZoneDto zoneDto, final AnimalKeeperDto animalKeeperDto){
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 15, 30, 0);
        return AnimalDto.builder()
                .name("Jeff")
                .description("Nice dog")
                .admissionDate(dateTime)
                .species(Species.DOG)
                .zoneId(zoneDto.id())
                .keeperId(animalKeeperDto.id())
                .build();
    }

    public static ZoneDto CreateTestZoneDto(){
        return ZoneDto.builder()
                .name("Zone")
                .description("Big zone")
                .build();
    }

    public static AnimalKeeperDto CreateTestAnimalKeeperDto(){
        return AnimalKeeperDto.builder()
                .name("Sam")
                .surname("Smith")
                .pesel("54321")
                .build();
    }
}
