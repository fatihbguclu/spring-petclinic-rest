package com.spring.rest.controller;

import com.spring.rest.dto.OwnerDto;
import com.spring.rest.dto.PetDto;
import com.spring.rest.dto.VisitDto;
import com.spring.rest.mapper.OwnerMapper;
import com.spring.rest.mapper.PetMapper;
import com.spring.rest.mapper.VisitMapper;
import com.spring.rest.model.Owner;
import com.spring.rest.model.Pet;
import com.spring.rest.model.Visit;
import com.spring.rest.service.ClinicService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api")
public class OwnerRestController {

    private final ClinicService clinicService;
    private final OwnerMapper ownerMapper;
    private final PetMapper petMapper;
    private final VisitMapper visitMapper;

    public OwnerRestController(ClinicService clinicService, OwnerMapper ownerMapper, PetMapper petMapper, VisitMapper visitMapper) {
        this.clinicService = clinicService;
        this.ownerMapper = ownerMapper;
        this.petMapper = petMapper;
        this.visitMapper = visitMapper;
    }

    @GetMapping("/owners/{ownerId}")
    public ResponseEntity<OwnerDto> getOwner(@PathVariable("ownerId") Integer ownerId){
        Owner owner = clinicService.findOwnerById(ownerId);

        if (owner == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ownerMapper.toOwnerDto(owner), HttpStatus.OK);
    }

    @GetMapping("/owners")
    public ResponseEntity<List<OwnerDto>> listOwners(@RequestParam(name = "lastName",required = false) String lastname){
        Collection<Owner> owners;

        if (lastname != null){
            owners = clinicService.findOwnerByLastName(lastname);
        }else{
            owners = clinicService.findAllOwners();
        }

        if (owners.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<OwnerDto> ownerList = owners.stream()
                .map(ownerMapper::toOwnerDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ownerList, HttpStatus.OK);
    }

    @PostMapping(value = "/owners",consumes = {"application/json"})
    public ResponseEntity<OwnerDto> addOwner(@Valid @RequestBody OwnerDto ownerDto){
        HttpHeaders headers = new HttpHeaders();

        Owner owner = ownerMapper.toOwner(ownerDto);
        clinicService.saveOwner(owner);
        OwnerDto savedOwnerDto = ownerMapper.toOwnerDto(owner);

        headers.setLocation(UriComponentsBuilder.newInstance().
                path("/api/owners/{id}").buildAndExpand(owner.getId()).toUri());

        return new ResponseEntity<>(savedOwnerDto,headers,HttpStatus.CREATED);
    }

    @PutMapping("/owners/{ownerId}")
    public ResponseEntity<OwnerDto> updateOwner(@PathVariable Integer ownerId, @RequestBody OwnerDto ownerDto){
        Owner currentOwner = clinicService.findOwnerById(ownerId);

        if (currentOwner == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentOwner.setAddress(ownerDto.getAddress());
        currentOwner.setCity(ownerDto.getCity());
        currentOwner.setFirstName(ownerDto.getFirstName());
        currentOwner.setLastName(ownerDto.getLastName());
        currentOwner.setTelephone(ownerDto.getTelephone());
        clinicService.saveOwner(currentOwner);

        return new ResponseEntity<>(ownerMapper.toOwnerDto(currentOwner),HttpStatus.NO_CONTENT);
    }

    @Transactional
    @DeleteMapping("/owners/{ownerId}")
    public ResponseEntity<OwnerDto> deleteOwner(@PathVariable(name = "ownerId") Integer ownerId){
        Owner owner = clinicService.findOwnerById(ownerId);
        if (owner == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        clinicService.deleteOwner(owner);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/owners/{ownerId}/pets")
    public ResponseEntity<PetDto> addPetToOwner(@PathVariable Integer ownerId, @RequestBody PetDto petDto){
        HttpHeaders headers = new HttpHeaders();

        Owner owner = new Owner();
        owner.setId(ownerId);

        Pet pet = petMapper.toPet(petDto);
        pet.setOwner(owner);
        clinicService.savePet(pet);

        headers.setLocation(UriComponentsBuilder.newInstance().
                path("/api/pets/{id}").buildAndExpand(pet.getId()).toUri());

        return new ResponseEntity<>(petMapper.toPetDto(pet),headers,HttpStatus.CREATED);
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits")
    public ResponseEntity<VisitDto> addVisitToOwner(@PathVariable Integer ownerId, @PathVariable Integer petId,
                                                    @RequestBody VisitDto visitDto){
        HttpHeaders headers = new HttpHeaders();
        Owner owner = clinicService.findOwnerById(ownerId);

        if (owner == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (owner.getPets().stream().noneMatch(pet -> pet.getId().equals(petId))){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Visit visit = visitMapper.toVisit(visitDto);
        Optional<Pet> pet = owner.getPets().stream()
                .filter(pet1 -> pet1.getId().equals(petId))
                .findFirst();

        visit.setPet(pet.get());

        clinicService.saveVisit(visit);

        headers.setLocation(UriComponentsBuilder.newInstance()
                .path("/api/visits/{id}").buildAndExpand(visit.getId()).toUri());

        return new ResponseEntity<>(visitMapper.toVisitDto(visit),headers,HttpStatus.CREATED);
    }
}
