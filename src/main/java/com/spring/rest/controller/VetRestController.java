package com.spring.rest.controller;

import com.spring.rest.dto.VetDto;
import com.spring.rest.mapper.SpecialtyMapper;
import com.spring.rest.mapper.VetMapper;
import com.spring.rest.model.Specialty;
import com.spring.rest.model.Vet;
import com.spring.rest.service.ClinicService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class VetRestController {

    private final ClinicService clinicService;
    private final VetMapper vetMapper;
    private final SpecialtyMapper specialtyMapper;

    public VetRestController(ClinicService clinicService, VetMapper vetMapper, SpecialtyMapper specialtyMapper) {
        this.clinicService = clinicService;
        this.vetMapper = vetMapper;
        this.specialtyMapper = specialtyMapper;
    }

    @GetMapping("/vets")
    public ResponseEntity<List<VetDto>> listVets(){
        List<Vet> vets = new ArrayList<>(clinicService.findAllVets());

        if (vets.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<VetDto> vetDtos = vets.stream()
                .map(vetMapper::toVetDto).collect(Collectors.toList());

        return new ResponseEntity<>(vetDtos, HttpStatus.OK);
    }

    @GetMapping("/vets/{vetId}")
    public ResponseEntity<VetDto> getVet(@PathVariable Integer vetId)  {
        Vet vet = clinicService.findVetById(vetId);

        if (vet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(vetMapper.toVetDto(vet), HttpStatus.OK);
    }

    @PostMapping("/vets")
    public ResponseEntity<VetDto> addVet(@RequestBody VetDto vetDto) {
        HttpHeaders headers = new HttpHeaders();
        Vet vet = vetMapper.toVet(vetDto);

        this.clinicService.saveVet(vet);

        headers.setLocation(UriComponentsBuilder.newInstance().path("/api/vets/{id}").buildAndExpand(vet.getId()).toUri());
        return new ResponseEntity<>(vetMapper.toVetDto(vet), headers, HttpStatus.CREATED);
    }

    @PutMapping("/vets/{vetId}")
    public ResponseEntity<VetDto> updateVet(@PathVariable Integer vetId,@RequestBody VetDto vetDto)  {
        Vet currentVet = this.clinicService.findVetById(vetId);

        if (currentVet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentVet.setFirstName(vetDto.getFirstName());
        currentVet.setLastName(vetDto.getLastName());
        Set<Specialty> specialtySet = currentVet.getSpecialties();

        specialtySet.addAll(
                vetDto.getSpecialties().stream()
                .map(specialtyMapper::toSpecialty)
                .collect(Collectors.toList())
        );

        currentVet.setSpecialties(specialtySet);

        this.clinicService.saveVet(currentVet);


        return new ResponseEntity<>(vetMapper.toVetDto(currentVet), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/vets/{vetId}")
    @Transactional
    public ResponseEntity<VetDto> deleteVet(@PathVariable Integer vetId) {
        Vet vet = this.clinicService.findVetById(vetId);
        if (vet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deleteVet(vet);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
