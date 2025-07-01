package com.animal.animalShelter.controllers;

import com.animal.animalShelter.TestData;
import com.animal.animalShelter.domain.entities.AnimalKeeper;
import com.animal.animalShelter.mappers.AnimalKeeperMapper;
import com.animal.animalShelter.services.AnimalKeeperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AnimalKeeperIntegrationTest {

    final MockMvc mockMvc;
    final AnimalKeeperService animalKeeperService;
    final ObjectMapper objectMapper;

    @Autowired
    public AnimalKeeperIntegrationTest(MockMvc mockMvc, AnimalKeeperService animalKeeperService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.animalKeeperService = animalKeeperService;
        this.objectMapper = objectMapper;
    }


    @Test
    public void testThatCreateAnimalKeeperReturnsHttpCreated() throws Exception {
        AnimalKeeper animalKeeper = TestData.CreateTestAnimalKeeperA();
        animalKeeper.setId(null);
        String json = objectMapper.writeValueAsString(animalKeeper);

        mockMvc.perform(MockMvcRequestBuilders.post("/keepers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateAnimalKeeperReturnsSavedAnimalKeeper() throws Exception {
        AnimalKeeper animalKeeper = TestData.CreateTestAnimalKeeperA();
        animalKeeper.setId(null);
        String json = objectMapper.writeValueAsString(animalKeeper);

        mockMvc.perform(MockMvcRequestBuilders.post("/keepers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Bob"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.surname").value("Smith"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.pesel").value("12345"));

    }

    @Test
    public void testThatListAnimalKeepersReturnsHttpStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/keepers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAnimalKeepersReturnsList() throws Exception {
        AnimalKeeper keeper = TestData.CreateTestAnimalKeeperA();
        animalKeeperService.createAnimalKeeper(keeper);

        mockMvc.perform(MockMvcRequestBuilders.get("/keepers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].name").value("Bob"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].surname").value("Smith"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].pesel").value("12345"));
    }

    @Test
    public void testThatGetAnimalKeeperReturnsHttpStatusOk() throws Exception {
        AnimalKeeper keeper = TestData.CreateTestAnimalKeeperA();
        AnimalKeeper saved = animalKeeperService.createAnimalKeeper(keeper);

        mockMvc.perform(MockMvcRequestBuilders.get("/keepers/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAnimalKeeperReturnsData() throws Exception {
        AnimalKeeper keeper = TestData.CreateTestAnimalKeeperA();
        AnimalKeeper saved = animalKeeperService.createAnimalKeeper(keeper);

        mockMvc.perform(MockMvcRequestBuilders.get("/keepers/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Bob"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.surname").value("Smith"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.pesel").value("12345"));
    }

    @Test
    public void testThatUpdateAnimalKeeperReturnsHttpCreated() throws Exception {
        AnimalKeeper keeper = TestData.CreateTestAnimalKeeperA();
        AnimalKeeper saved = animalKeeperService.createAnimalKeeper(keeper);

        saved.setName("Jacob");
        saved.setSurname("Feet");
        saved.setPesel("54321");
        String json = objectMapper.writeValueAsString(saved);

        mockMvc.perform(MockMvcRequestBuilders.put("/keepers/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatUpdateAnimalKeeperReturnsUpdatedData() throws Exception {
        AnimalKeeper keeper = TestData.CreateTestAnimalKeeperA();
        AnimalKeeper saved = animalKeeperService.createAnimalKeeper(keeper);

        saved.setName("Jacob");
        saved.setSurname("Feet");
        saved.setPesel("54321");
        String json = objectMapper.writeValueAsString(saved);

        mockMvc.perform(MockMvcRequestBuilders.put("/keepers/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Jacob"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.surname").value("Feet"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.pesel").value("54321"));
    }

    @Test
    public void testThatDeleteAnimalKeeperReturnsNoContent() throws Exception {
        AnimalKeeper keeper = TestData.CreateTestAnimalKeeperA();
        AnimalKeeper saved = animalKeeperService.createAnimalKeeper(keeper);
        mockMvc.perform(MockMvcRequestBuilders.delete("/keepers/delete/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAnimalKeeperReturnsNoValues() throws Exception {
        AnimalKeeper keeper = TestData.CreateTestAnimalKeeperA();
        AnimalKeeper saved = animalKeeperService.createAnimalKeeper(keeper);
        mockMvc.perform(MockMvcRequestBuilders.delete("/keepers/delete/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                MockMvcResultMatchers.jsonPath("$.name").doesNotExist())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.surname").doesNotExist())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.pesel").doesNotExist());
    }
}

