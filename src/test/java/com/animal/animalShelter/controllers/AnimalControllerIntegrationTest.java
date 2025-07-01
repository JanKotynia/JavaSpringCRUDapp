package com.animal.animalShelter.controllers;

import com.animal.animalShelter.TestData;
import com.animal.animalShelter.domain.entities.Animal;
import com.animal.animalShelter.domain.entities.AnimalKeeper;
import com.animal.animalShelter.domain.entities.Species;
import com.animal.animalShelter.domain.entities.Zone;
import com.animal.animalShelter.services.AnimalService;
import com.animal.animalShelter.services.AnimalKeeperService;
import com.animal.animalShelter.services.ZoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AnimalControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final AnimalService animalService;
    private final ZoneService zoneService;
    private final AnimalKeeperService animalKeeperService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AnimalControllerIntegrationTest(MockMvc mockMvc, AnimalService animalService, ZoneService zoneService,
                                           AnimalKeeperService animalKeeperService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.animalService = animalService;
        this.zoneService = zoneService;
        this.animalKeeperService = animalKeeperService;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateAnimalReturnsHttpCreated() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone,keeper);
        animal.setId(null);

        String json = objectMapper.writeValueAsString(animal);

        mockMvc.perform(MockMvcRequestBuilders.post("/animals/" + zone.getId() + "/" + keeper.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateAnimalReturnsSavedAnimal() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone, keeper);
        animal.setId(null);

        String json = objectMapper.writeValueAsString(animal);

        mockMvc.perform(MockMvcRequestBuilders.post("/animals/" + zone.getId() + "/" + keeper.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Jeff"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.description").value("Nice dog"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.species").value("DOG"));
    }

    @Test
    public void testThatListAnimalsReturnsHttpOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/animals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAnimalByIdReturnsHttpOk() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone, keeper);
        Animal saved = animalService.createAnimal(zone.getId(), keeper.getId(), animal);

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/" + saved.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAnimalByIdReturnsAnimal() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone, keeper);
        Animal saved = animalService.createAnimal(zone.getId(), keeper.getId(), animal);

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Jeff"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.description").value("Nice dog"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.species").value("DOG"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.zoneId").value(zone.getId().toString()))
                .andExpect(
                    MockMvcResultMatchers.jsonPath("$.keeperId").value(keeper.getId().toString()));
    }

    @Test
    public void testThatListAnimalsByZoneReturnsHttpOk() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone, keeper);
        animalService.createAnimal(zone.getId(), keeper.getId(), animal);

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/zone/" + zone.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAnimalsByZoneReturnsCorrectData() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone, keeper);
        animalService.createAnimal(zone.getId(), keeper.getId(), animal);

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/zone/" + zone.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].zoneId").value(zone.getId().toString()));
    }

    @Test
    public void testThatListAnimalsByAnimalKeeperReturnsHttpOk() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone, keeper);
        animalService.createAnimal(zone.getId(), keeper.getId(), animal);

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/keeper/" + keeper.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAnimalsByKeeperReturnsCorrectData() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone, keeper);
        animalService.createAnimal(zone.getId(), keeper.getId(), animal);

        mockMvc.perform(MockMvcRequestBuilders.get("/animals/keeper/" + keeper.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].keeperId").value(keeper.getId().toString()));
    }

    @Test
    public void testThatUpdateAnimalReturnsHttpCreated() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone, keeper);
        Animal saved = animalService.createAnimal(zone.getId(), keeper.getId(), animal);

        saved.setName("NewName");
        saved.setDescription("NewDesc");

        String json = objectMapper.writeValueAsString(saved);

        mockMvc.perform(MockMvcRequestBuilders.put("/animals/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatUpdateAnimalReturnsUpdatedAnimal() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone, keeper);
        Animal saved = animalService.createAnimal(zone.getId(), keeper.getId(), animal);

        saved.setName("NewName");
        saved.setDescription("NewDesc");

        String json = objectMapper.writeValueAsString(saved);

        mockMvc.perform(MockMvcRequestBuilders.put("/animals/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("NewName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("NewDesc"));
    }

    @Test
    public void testThatDeleteAnimalReturnsNoValues() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone, keeper);
        Animal saved = animalService.createAnimal(zone.getId(), keeper.getId(), animal);

        mockMvc.perform(MockMvcRequestBuilders.delete("/animals/delete/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.admission_date").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.species").doesNotExist());

    }

    @Test
    public void testThatDeleteAnimalReturnsNoContent() throws Exception {
        Zone zone = zoneService.createZone(TestData.CreateTestZoneA());
        AnimalKeeper keeper = animalKeeperService.createAnimalKeeper(TestData.CreateTestAnimalKeeperA());
        Animal animal = TestData.CreateTestAnimalA(zone, keeper);
        Animal saved = animalService.createAnimal(zone.getId(), keeper.getId(), animal);

        mockMvc.perform(MockMvcRequestBuilders.delete("/animals/delete/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

