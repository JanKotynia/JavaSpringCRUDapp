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

import java.util.UUID;

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
    public void testThatCreateZoneReturnsHttpCreated() throws Exception {
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

    @Test
    public void TestThatListZonesReturnsHttpStatusOk() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/zones")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestThatListZonesReturnsListOfZones() throws Exception {
        Zone zone = TestData.CreateTestZoneA();
        zoneService.createZone(zone);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/zones")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("ZoneA")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].description").value("Big zone")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].size").isNumber()
        );
    }

    @Test
    public void TestThatGetZoneReturnsHttpStatusOk() throws Exception {
        Zone zone = TestData.CreateTestZoneA();
        Zone savedZone = zoneService.createZone(zone);
        System.out.println(savedZone.getId());
        String url = "/zones/" + savedZone.getId();
        mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestThatGetZoneReturnsZone() throws Exception {
        Zone zone = TestData.CreateTestZoneA();
        Zone savedZone = zoneService.createZone(zone);
        String url = "/zones/" + savedZone.getId();
        mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
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

    @Test
    public void testThatPutZoneReturnsHttpCreated() throws Exception {
        Zone zone = TestData.CreateTestZoneA();
        Zone savedZone = zoneService.createZone(zone);
        String url = "/zones/" + savedZone.getId();
        savedZone.setName("NewName");
        savedZone.setDescription("NewDesc");
        String zoneJson = objectMapper.writeValueAsString(savedZone);
        mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(zoneJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatPutZoneReturnsUpdatedZone() throws Exception {
        Zone zone = TestData.CreateTestZoneA();
        Zone savedZone = zoneService.createZone(zone);
        String url = "/zones/" + savedZone.getId();
        savedZone.setName("NewName");
        savedZone.setDescription("NewDesc");
        String zoneJson = objectMapper.writeValueAsString(savedZone);
        mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(zoneJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNotEmpty()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("NewName")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("NewDesc")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.size").isNumber()
        );
    }

    @Test
    public void testThatDeleteAuthorReturnesStstusNoContent() throws Exception {
        Zone zone = TestData.CreateTestZoneA();
        Zone savedZone = zoneService.createZone(zone);
        String url = "/zones/delete/" + savedZone.getId();
        mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAuthorReturnesNoValues() throws Exception {
        Zone zone = TestData.CreateTestZoneA();
        Zone savedZone = zoneService.createZone(zone);
        String url = "/zones/delete/" + savedZone.getId();
        mockMvc.perform(
                MockMvcRequestBuilders.delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").doesNotExist()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").doesNotExist()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").doesNotExist()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.size").doesNotExist()
        );
    }


}
