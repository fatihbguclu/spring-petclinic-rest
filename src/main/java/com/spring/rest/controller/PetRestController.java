package com.spring.rest.controller;

import com.spring.rest.dto.PetDto;
import com.spring.rest.mapper.PetMapper;
import com.spring.rest.model.Pet;
import com.spring.rest.service.ClinicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PetRestController {

    private final ClinicService clinicService;

    private final PetMapper petMapper;

    public PetRestController(ClinicService clinicService, PetMapper petMapper) {
        this.clinicService = clinicService;
        this.petMapper = petMapper;
    }

    @GetMapping("/pets/{petId}")
    public ResponseEntity<PetDto>  getPet(@PathVariable Integer petId){
        PetDto petDto = petMapper.toPetDto(clinicService.findPetById(petId));

        if (petDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(petDto, HttpStatus.OK);
    }

    @GetMapping("/pets")
    public ResponseEntity<List<PetDto>> listPets(){
        List<Pet> petList = (List<Pet>) clinicService.findAllPets();

        if (petList == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<PetDto> petDtoList = petList.stream()
                .map(petMapper::toPetDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(petDtoList, HttpStatus.OK);
    }

    @PutMapping("/pets/{petId}")
    public ResponseEntity<PetDto> updatePet(@PathVariable Integer petId, @RequestBody PetDto petDto){
        Pet currentPet = clinicService.findPetById(petId);
        if (currentPet == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentPet.setBirthDate(petDto.getBirthDate());
        currentPet.setName(petDto.getName());
        currentPet.setType(petMapper.toPetType(petDto.getType()));

        clinicService.savePet(currentPet);

        return new ResponseEntity<>(petMapper.toPetDto(currentPet), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/pets/{petId}")
    @Transactional
    public ResponseEntity<PetDto> deletePet(Integer petId){
        Pet pet  = clinicService.findPetById(petId);
        if (pet == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clinicService.deletePet(pet);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
