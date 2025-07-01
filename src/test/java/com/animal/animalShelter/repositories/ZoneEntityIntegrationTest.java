package com.animal.animalShelter.repositories;

import com.animal.animalShelter.TestData;
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
public class ZoneEntityIntegrationTest {

    private ZoneRepository repositoryTest;

    @Autowired
    public ZoneEntityIntegrationTest(ZoneRepository repositoryTest) {
        this.repositoryTest = repositoryTest;
    }

    @Test
    public void TestThatZoneCanBeCreatedAndRecalled(){
        Zone zone = TestData.CreateTestZoneA();
        repositoryTest.save(zone);
        Optional<Zone> result = repositoryTest.findById(zone.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(zone);
    }

    @Test
    public void TestThatMultipleZonesCanBeCreatedAndRecalled(){
        Zone zoneA = TestData.CreateTestZoneA();
        Zone zoneB = TestData.CreateTestZoneB();
        repositoryTest.save(zoneA);
        repositoryTest.save(zoneB);

        Iterable<Zone> result = repositoryTest.findAll();
        assertThat(result).hasSize(2).containsExactly(zoneA, zoneB);
    }

    @Test
    public void testThatZoneCanBeUpdated(){
        Zone zone = TestData.CreateTestZoneA();
        repositoryTest.save(zone);
        zone.setName("NewName");
        repositoryTest.save(zone);
        Optional<Zone> result = repositoryTest.findById(zone.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(zone);
    }

    @Test
    public void testThatZoneCanBeDeleted(){
        Zone zone = TestData.CreateTestZoneA();
        repositoryTest.save(zone);
        repositoryTest.delete(zone);
        Optional<Zone> result = repositoryTest.findById(zone.getId());
        assertThat(result).isEmpty();
    }
}
