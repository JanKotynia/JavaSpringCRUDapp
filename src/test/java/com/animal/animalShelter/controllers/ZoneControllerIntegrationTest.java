package com.animal.animalShelter.controllers;

import com.animal.animalShelter.TestData;
import com.animal.animalShelter.domain.entities.Zone;
import com.animal.animalShelter.mappers.ZoneMapper;
import com.animal.animalShelter.services.ZoneService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ZoneControllerIntegrationTest {
    private MockMvc mockMvc;

    private ZoneService zoneService;

    private ZoneMapper zoneMapper;

    ObjectMapper objectMapper;

    @Autowired
    public ZoneControllerIntegrationTest(MockMvc mockMvc, ZoneService zoneService, ZoneMapper zoneMapper, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.zoneService = zoneService;
        this.zoneMapper = zoneMapper;
        this.objectMapper = objectMapper;
    }


    @Test
    public void testThatCreateZoneReturnsHttp201Created() throws Exception {
        Zone zone = TestData.CreateTestZoneA();
        zone.setId(null);
        String zoneJson = objectMapper.writeValueAsString(zone);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/zones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(zoneJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );

    }

    @Test
    public void testThatCreateZoneReturnsSavedZone() throws Exception {
        Zone zone = TestData.CreateTestZoneA();
        zone.setId(null);
        String zoneJson = objectMapper.writeValueAsString(zone);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/zones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(zoneJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("ZoneA")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("Big zone")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.size").isNumber()
        );
    }
}
