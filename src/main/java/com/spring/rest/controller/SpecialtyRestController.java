package com.spring.rest.controller;

import com.spring.rest.dto.SpecialtyDto;
import com.spring.rest.mapper.SpecialtyMapper;
import com.spring.rest.model.Specialty;
import com.spring.rest.service.ClinicService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SpecialtyRestController {

    private final ClinicService clinicService;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyRestController(ClinicService clinicService, SpecialtyMapper specialtyMapper) {
        this.clinicService = clinicService;
        this.specialtyMapper = specialtyMapper;
    }

    @GetMapping("/specialties")
    public ResponseEntity<List<SpecialtyDto>> listSpecialties(){

        List<Specialty> specialties = new ArrayList<>(clinicService.findAllSpecialties());

        if (specialties.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<SpecialtyDto>  specialtyDtos = specialties.stream()
                .map(specialtyMapper::toSpecialtyDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(specialtyDtos,HttpStatus.OK);

    }

    @GetMapping("/specialties/{specialtyId}")
    public ResponseEntity<SpecialtyDto> getSpecialty(@PathVariable Integer specialtyId){

        Specialty specialty = clinicService.findSpecialtyById(specialtyId);

        if (specialty == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(specialtyMapper.toSpecialtyDto(specialty),HttpStatus.OK);

    }

    @PostMapping("/specialties")
    public ResponseEntity<SpecialtyDto> addSpecialty(@RequestBody SpecialtyDto specialtyDto){
        HttpHeaders headers  = new HttpHeaders();
        Specialty specialty = specialtyMapper.toSpecialty(specialtyDto);
        clinicService.saveSpecialty(specialty);

        headers.setLocation(UriComponentsBuilder.newInstance().path("/api/specialties/{id}")
                .buildAndExpand(specialty.getId()).toUri());

        return new ResponseEntity<>(specialtyMapper.toSpecialtyDto(specialty),headers,HttpStatus.CREATED);
    }

    @PutMapping("/specialties/{specialtyId}")
    public ResponseEntity<SpecialtyDto> updateSpecialty(@PathVariable Integer specialtyId, @RequestBody SpecialtyDto specialtyDto){
        Specialty currentSpecialty = clinicService.findSpecialtyById(specialtyId);

        if (currentSpecialty == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentSpecialty.setName(specialtyDto.getName());
        clinicService.saveSpecialty(currentSpecialty);

        return new ResponseEntity<>(specialtyMapper.toSpecialtyDto(currentSpecialty),HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/specialties")
    public ResponseEntity<SpecialtyDto> deleteSpecialty(Integer specialtyId){
        Specialty specialty = clinicService.findSpecialtyById(specialtyId);
        if (specialty == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clinicService.deleteSpecialty(specialty);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
