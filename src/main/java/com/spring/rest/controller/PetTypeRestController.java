package com.spring.rest.controller;

import com.spring.rest.dto.PetTypeDto;
import com.spring.rest.mapper.PetTypeMapper;
import com.spring.rest.model.PetType;
import com.spring.rest.service.ClinicService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PetTypeRestController {
    private final ClinicService clinicService;
    private final PetTypeMapper petTypeMapper;

    public PetTypeRestController(ClinicService clinicService, PetTypeMapper petTypeMapper) {
        this.clinicService = clinicService;
        this.petTypeMapper = petTypeMapper;
    }

    @GetMapping("/pettypes")
    public ResponseEntity<List<PetTypeDto>> listPetTypes(){
        Collection<PetType> petTypes = clinicService.findAllPetTypes();
        if (petTypes == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<PetTypeDto> petTypeDtos = petTypes.stream()
                .map(petTypeMapper::toPetTypeDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(petTypeDtos,HttpStatus.OK);
    }

    @GetMapping("/pettypes/{petTypeId}")
    public ResponseEntity<PetTypeDto> getPetType(Integer petTypeId){
        PetType petType = clinicService.findPetTypeById(petTypeId);

        if (petType == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(petTypeMapper.toPetTypeDto(petType), HttpStatus.OK);
    }

    @PostMapping("/pettypes")
    public ResponseEntity<PetTypeDto> addPetType(PetTypeDto petTypeDto){
        HttpHeaders headers  = new HttpHeaders();

        PetType petType = petTypeMapper.toPetType(petTypeDto);
        clinicService.savePetType(petType);

        headers.setLocation(UriComponentsBuilder.newInstance().path("/api/pettypes/{id}")
                .buildAndExpand(petType.getId()).toUri());

        return new ResponseEntity<>(petTypeMapper.toPetTypeDto(petType),headers,HttpStatus.CREATED);
    }

    @PutMapping("/pettypes/{petTypeId}")
    public ResponseEntity<PetTypeDto> updatePetType(@PathVariable Integer petTypeId, @RequestBody PetTypeDto petTypeDto){
        PetType currentPetType = clinicService.findPetTypeById(petTypeId);

        if(currentPetType == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentPetType.setName(petTypeDto.getName());
        clinicService.savePetType(currentPetType);

        return new ResponseEntity<>(petTypeMapper.toPetTypeDto(currentPetType), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/pettypes/{petTypeId}")
    public ResponseEntity<PetTypeDto> deletePetType(@PathVariable Integer petTypeId){
        PetType petType = clinicService.findPetTypeById(petTypeId);

        if (petType == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clinicService.deletePetType(petType);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
