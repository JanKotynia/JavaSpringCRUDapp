package com.animal.animalShelter.repositories;

import com.animal.animalShelter.TestData;
import com.animal.animalShelter.domain.entities.Animal;
import com.animal.animalShelter.domain.entities.AnimalKeeper;
import com.animal.animalShelter.domain.entities.Zone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AnimalEntityIntegrationTest {
    private AnimalRepository repositoryTest;
    private ZoneRepository zoneRepositoryTest;
    private AnimalKeeperRepository animalKeeperRepositoryTest;

    @Autowired
    public AnimalEntityIntegrationTest(AnimalRepository repositoryTest, ZoneRepository zoneRepository, AnimalKeeperRepository animalKeeperRepository) {
        this.repositoryTest = repositoryTest;
        this.zoneRepositoryTest = zoneRepository;
        this.animalKeeperRepositoryTest = animalKeeperRepository;
    }


    @Test
    public void testThatAnimalCanBeCreatedAndRecalled(){
        Zone zone = TestData.CreateTestZoneA();
        AnimalKeeper animalKeeper = TestData.CreateTestAnimalKeeperA();
        Animal animal = TestData.CreateTestAnimalA(zone, animalKeeper);
        zoneRepositoryTest.save(zone);
        animalKeeperRepositoryTest.save(animalKeeper);
        repositoryTest.save(animal);
        Optional<Animal> result = repositoryTest.findById(animal.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison().ignoringFields("zone", "animalKeeper").isEqualTo(animal);


        Optional<Animal> result1 = repositoryTest.findByZoneIdAndId(zone.getId(), animal.getId());
        assertThat(result1).isPresent();
        assertThat(result1.get()).usingRecursiveComparison().ignoringFields("zone", "animalKeeper").isEqualTo(animal);

        Optional<Animal> result2 = repositoryTest.findByAnimalKeeperIdAndId(animalKeeper.getId(), animal.getId());
        assertThat(result2).isPresent();
        assertThat(result2.get()).usingRecursiveComparison().ignoringFields("zone", "animalKeeper").isEqualTo(animal);


        Optional<Animal> result3 = repositoryTest.findByZoneIdAndId(zone.getId(), animal.getId());
        assertThat(result3).isPresent();
        assertThat(result3.get()).usingRecursiveComparison().ignoringFields("zone", "animalKeeper").isEqualTo(animal);

    }

    @Test
    public void testThatMultipleAnimalsCanBeCreatedAndRecall(){
        Zone zone = TestData.CreateTestZoneA();
        AnimalKeeper animalKeeper = TestData.CreateTestAnimalKeeperA();
        Animal animalA = TestData.CreateTestAnimalA(zone, animalKeeper);
        Animal animalB = TestData.CreateTestAnimalB(zone, animalKeeper);
        zoneRepositoryTest.save(zone);
        animalKeeperRepositoryTest.save(animalKeeper);
        repositoryTest.save(animalA);
        repositoryTest.save(animalB);

        Iterable<Animal> result = repositoryTest.findAll();
        assertThat(result).hasSize(2);
        assertThat(result).usingRecursiveFieldByFieldElementComparatorIgnoringFields("zone", "animalKeeper")
                .containsExactlyInAnyOrder(animalA, animalB);
    }

    @Test
    public void testThatAnimalCanBeUpdated(){
        Zone zone = TestData.CreateTestZoneA();
        AnimalKeeper animalKeeper = TestData.CreateTestAnimalKeeperA();
        Animal animal = TestData.CreateTestAnimalA(zone, animalKeeper);

        repositoryTest.save(animal);
        animal.setName("Updated");
        repositoryTest.save(animal);

        Optional<Animal> result = repositoryTest.findById(animal.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(animal);
    }

    @Test
    public void testThatAnimalCanBeDeleted(){
        Zone zone = TestData.CreateTestZoneA();
        AnimalKeeper animalKeeper = TestData.CreateTestAnimalKeeperA();
        Animal animal = TestData.CreateTestAnimalA(zone, animalKeeper);
        repositoryTest.save(animal);
        repositoryTest.deleteById(animal.getId());
        Optional<Animal> result = repositoryTest.findById(animal.getId());
        assertThat(result).isEmpty();
    }
}
