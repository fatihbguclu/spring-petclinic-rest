package com.spring.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.rest.dto.OwnerDto;
import com.spring.rest.dto.PetDto;
import com.spring.rest.dto.PetTypeDto;
import com.spring.rest.dto.VisitDto;
import com.spring.rest.mapper.OwnerMapper;
import com.spring.rest.mapper.VisitMapper;
import com.spring.rest.model.Owner;
import com.spring.rest.service.ClinicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class OwnerRestControllerTest {

    @Autowired
    private OwnerRestController ownerRestController;

    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private VisitMapper visitMapper;

    @MockBean
    private ClinicService clinicService;

    private MockMvc mockMvc;
    private List<OwnerDto> owners;
    private List<PetDto> pets;
    private List<VisitDto> visits;

    @BeforeEach
    void initOwners(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(ownerRestController)
                .build();

        owners = new ArrayList<>();

        OwnerDto ownerWithPet = new OwnerDto();
        List<PetDto> petDtos = new ArrayList<>();
        petDtos.add(getTestPetWithIdAndName(ownerWithPet, 1, "Rosy"));

        owners.add(OwnerDto.builder().id(1).
                firstName("George").lastName("Franklin").address("110 W. Liberty St.").
                city("Madison").telephone("6085551023").pets(petDtos).build()
        );

        OwnerDto owner = new OwnerDto();
        owners.add(OwnerDto.builder().id(2).firstName("Betty").lastName("Davis").address("638 Cardinal Ave.").city("Sun Prairie").telephone("6085551749").build());

        owner = new OwnerDto();
        owners.add(OwnerDto.builder().id(3).firstName("Eduardo").lastName("Rodriquez").address("2693 Commerce St.").city("McFarland").telephone("6085558763").build());

        owner = new OwnerDto();
        owners.add(OwnerDto.builder().id(4).firstName("Harold").lastName("Davis").address("563 Friendly St.").city("Windsor").telephone("6085553198").build());

        PetTypeDto petType = PetTypeDto.builder().id(2).name("dog").build();

        pets = new ArrayList<>();
        pets.add(PetDto.builder().id(3).name("Rosy")
                .birthDate(LocalDate.now())
                .type(petType).build());

        pets.add(PetDto.builder().id(4).name("Rosy")
                .birthDate(LocalDate.now())
                .type(petType).build());

        visits  = new ArrayList<>();

        VisitDto visit = new VisitDto();
        visit.setId(2);
        visit.setPetId(4);
        visit.setDate(LocalDate.now());
        visit.setDescription("rabies shot");
        visits.add(visit);

        visit = new VisitDto();
        visit.setId(3);
        visit.setPetId(4);
        visit.setDate(LocalDate.now());
        visit.setDescription("neutered");
        visits.add(visit);
    }

    private PetDto getTestPetWithIdAndName(final OwnerDto owner, final int id, final String name) {
        PetTypeDto petType = new PetTypeDto();
        PetDto pet = new PetDto();
        pet.setVisits(new ArrayList<>());
        List<VisitDto> visitDtos  = pet.getVisits();
        visitDtos.add(getTestVisitForPet(pet,1));
        PetDto.builder().id(id).name(name).birthDate(LocalDate.now()).type(PetTypeDto.builder().id(2).name("dog").build()).visits(visitDtos).build();
        return pet;
    }

    private VisitDto getTestVisitForPet(final PetDto pet, final int id) {
        VisitDto visit = new VisitDto();
        return VisitDto.builder().id(id).date(LocalDate.now()).description("test" + id).build();
    }

    @Test
    void testGetOwnerSuccess() throws Exception {

        given(clinicService.findOwnerById(1)).willReturn(ownerMapper.toOwner(owners.get(0)));

        mockMvc.perform(get("/api/owners/1").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("George"));
    }

    @Test
    void testGetOwnerNotFound() throws Exception{
        given(clinicService.findOwnerById(2)).willReturn(null);
        mockMvc.perform(get("/api/owners/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetOwnersListSuccess() throws Exception{
        owners.remove(0);
        owners.remove(1);

        List<Owner> ownerList = owners.stream()
                .map(ownerMapper::toOwner)
                .collect(Collectors.toList());

        given(clinicService.findOwnerByLastName("Davis")).willReturn(ownerList);

        mockMvc.perform(get("/api/owners?=lastName=Davis").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(2))
                .andExpect(jsonPath("$.[0].firstName").value("Betty"))
                .andExpect(jsonPath("$.[1].id").value(4))
                .andExpect(jsonPath("$.[1].firstName").value("Harold"));
    }

    @Test
    void testGetOwnersListNotFound() throws Exception{
        owners.clear();

        List<Owner> ownerList = owners.stream()
                .map(ownerMapper::toOwner)
                .collect(Collectors.toList());
        given(clinicService.findOwnerByLastName("0")).willReturn(ownerList);
        mockMvc.perform(get("/api/owners?lastName=0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllOwnersSuccess() throws Exception{
        owners.remove(0);
        owners.remove(1);

        List<Owner> ownerList = owners.stream()
                .map(ownerMapper::toOwner)
                .collect(Collectors.toList());
        given(clinicService.findAllOwners()).willReturn(ownerList);

        mockMvc.perform(get("/api/owners/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(2))
                .andExpect(jsonPath("$.[0].firstName").value("Betty"))
                .andExpect(jsonPath("$.[1].id").value(4))
                .andExpect(jsonPath("$.[1].firstName").value("Harold"));
    }

    @Test
    void testGetAllOwnersNotFound() throws Exception {
        owners.clear();

        List<Owner> ownerList = owners.stream()
                .map(ownerMapper::toOwner)
                .collect(Collectors.toList());
        given(clinicService.findOwnerByLastName("0")).willReturn(ownerList);
        mockMvc.perform(get("/api/owners/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOwnerSuccess() throws Exception {
        OwnerDto newOwnerDto = owners.get(0);
        newOwnerDto.setId(null);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String newOwnerAsJSON = objectMapper.writeValueAsString(newOwnerDto);

        mockMvc.perform(post("/api/owners/")
                    .content(newOwnerAsJSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test // TODO Be sure entity has valid fields
    void testCreateOwnerError() throws Exception {
        OwnerDto newOwnerDto = owners.get(0);
        newOwnerDto.setId(null);
        newOwnerDto.setFirstName(null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String newOwnerAsJSON = mapper.writeValueAsString(newOwnerDto);

        mockMvc.perform(post("/api/owners/")
                        .content(newOwnerAsJSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateOwnerSuccess() throws Exception{
        given(this.clinicService.findOwnerById(1)).willReturn(ownerMapper.toOwner(owners.get(0)));
        int ownerId = owners.get(0).getId();
        OwnerDto updatedOwnerDto = new OwnerDto();

        updatedOwnerDto.setId(ownerId);
        updatedOwnerDto.setFirstName("GeorgeI");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String newOwnerAsJSON = mapper.writeValueAsString(updatedOwnerDto);

        mockMvc.perform(put("/api/owners/" + ownerId)
                        .content(newOwnerAsJSON).accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/owners/" + ownerId)
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(ownerId))
                .andExpect(jsonPath("$.firstName").value("GeorgeI"));
    }

    @Test
    void testDeleteOwnerSuccess() throws Exception {
        OwnerDto newOwnerDto = owners.get(0);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String newOwnerAsJSON = objectMapper.writeValueAsString(newOwnerDto);

        final Owner owner = ownerMapper.toOwner(newOwnerDto);

        given(clinicService.findOwnerById(1)).willReturn(owner);
        mockMvc.perform(delete("/api/owners/1")
                        .content(newOwnerAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }
    @Test
    void testDeleteOwnerError() throws Exception {
        OwnerDto newOwnerDto = owners.get(0);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String newOwnerAsJSON = mapper.writeValueAsString(newOwnerDto);

        given(this.clinicService.findOwnerById(999)).willReturn(null);
        mockMvc.perform(delete("/api/owners/999")
                        .content(newOwnerAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreatePetSuccess() throws Exception {
        PetDto newPet = pets.get(0);
        newPet.setId(999);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String newPetAsJSON = mapper.writeValueAsString(newPet);

        mockMvc.perform(post("/api/owners/1/pets/")
                        .content(newPetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test // TODO Be sure entity has valid fields
    void testCreatePetError() throws Exception  {
        PetDto newPet = pets.get(0);
        newPet.setId(null);
        newPet.setName(null);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        String newPetAsJSON = mapper.writeValueAsString(newPet);

        mockMvc.perform(post("/api/owners/1/pets/")
                        .content(newPetAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testCreateVisitSuccess() throws Exception {
        VisitDto newVisit = visits.get(0);
        newVisit.setId(999);

        OwnerDto ownerDto = owners.get(0);
        PetDto petDto = pets.get(0);

        ownerDto.setPets(new ArrayList<>());
        ownerDto.getPets().add(petDto);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String newVisitAsJSON = mapper.writeValueAsString(visitMapper.toVisit(newVisit));

        System.out.println("newVisitAsJSON " + newVisitAsJSON);
        given(clinicService.findOwnerById(1)).willReturn(ownerMapper.toOwner(ownerDto));

        pets.forEach(pet -> System.out.println(pet.getId()));

        mockMvc.perform(post("/api/owners/1/pets/3/visits")
                        .content(newVisitAsJSON).accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

}
