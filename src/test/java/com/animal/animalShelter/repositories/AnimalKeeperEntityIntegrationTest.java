package com.animal.animalShelter.repositories;

import com.animal.animalShelter.TestData;
import com.animal.animalShelter.domain.entities.AnimalKeeper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AnimalKeeperEntityIntegrationTest {

    private AnimalKeeperRepository repositoryTest;

    @Autowired
    public AnimalKeeperEntityIntegrationTest(AnimalKeeperRepository repositoryTest) {
        this.repositoryTest = repositoryTest;
    }


    @Test
    public void TestThatZoneCanBeCreatedAndRecalled(){
        AnimalKeeper animalKeeper = TestData.CreateTestAnimalKeeperA();
        repositoryTest.save(animalKeeper);
        Optional<AnimalKeeper> result = repositoryTest.findById(animalKeeper.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(animalKeeper);
    }

    @Test
    public void TestThatMultipleZonesCanBeCreatedAndRecalled(){
        AnimalKeeper animalKeeperA = TestData.CreateTestAnimalKeeperA();
        AnimalKeeper animalKeeperB = TestData.CreateTestAnimalKeeperB();
        repositoryTest.save(animalKeeperA);
        repositoryTest.save(animalKeeperB);

        Iterable<AnimalKeeper> result = repositoryTest.findAll();
        assertThat(result).hasSize(2).containsExactly(animalKeeperA, animalKeeperB);
    }

    @Test
    public void testThatZoneCanBeUpdated(){
        AnimalKeeper animalKeeper = TestData.CreateTestAnimalKeeperA();
        repositoryTest.save(animalKeeper);
        animalKeeper.setName("NewName");
        repositoryTest.save(animalKeeper);
        Optional<AnimalKeeper> result = repositoryTest.findById(animalKeeper.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(animalKeeper);
    }

    @Test
    public void testThatZoneCanBeDeleted(){
        AnimalKeeper animalKeeper = TestData.CreateTestAnimalKeeperA();
        repositoryTest.save(animalKeeper);
        repositoryTest.delete(animalKeeper);
        Optional<AnimalKeeper> result = repositoryTest.findById(animalKeeper.getId());
        assertThat(result).isEmpty();
    }
}