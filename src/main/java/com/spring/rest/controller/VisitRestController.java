package com.spring.rest.controller;

import com.spring.rest.dto.VisitDto;
import com.spring.rest.mapper.VisitMapper;
import com.spring.rest.model.Visit;
import com.spring.rest.service.ClinicService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class VisitRestController {

    private final ClinicService clinicService;
    private final VisitMapper visitMapper;

    public VisitRestController(ClinicService clinicService, VisitMapper visitMapper) {
        this.clinicService = clinicService;
        this.visitMapper = visitMapper;
    }

    @GetMapping("/visits")
    public ResponseEntity<List<VisitDto>> listVisits(){
        List<Visit> visits = new ArrayList<>(clinicService.findAllVisits());

        if (visits.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<VisitDto> visitDtos = visits.stream()
                .map(visitMapper::toVisitDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(visitDtos, HttpStatus.OK);
    }

    @GetMapping("/visits/{visitId}")
    public ResponseEntity<VisitDto> getVisit(@PathVariable Integer visitId) {
        Visit visit = clinicService.findVisitById(visitId);
        if (visit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(visitMapper.toVisitDto(visit), HttpStatus.OK);
    }

    @PostMapping("/visits")
    public ResponseEntity<VisitDto> addVisit(VisitDto visitDto) {
        HttpHeaders headers = new HttpHeaders();
        Visit visit = visitMapper.toVisit(visitDto);

        clinicService.saveVisit(visit);

        headers.setLocation(UriComponentsBuilder.newInstance().path("/api/visits/{id}")
                .buildAndExpand(visit.getId()).toUri());

        return new ResponseEntity<>(visitMapper.toVisitDto(visit), headers, HttpStatus.CREATED);
    }

    @PutMapping("/visits/{visitId}")
    public ResponseEntity<VisitDto> updateVisit(@PathVariable Integer visitId,@RequestBody VisitDto visitDto) {
        Visit currentVisit = this.clinicService.findVisitById(visitId);

        if (currentVisit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentVisit.setDate(visitDto.getDate());
        currentVisit.setDescription(visitDto.getDescription());

        clinicService.saveVisit(currentVisit);
        return new ResponseEntity<>(visitMapper.toVisitDto(currentVisit), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/visits/{visitId}")
    @Transactional
    public ResponseEntity<VisitDto> deleteVisit(@PathVariable Integer visitId) {
        Visit visit = this.clinicService.findVisitById(visitId);

        if (visit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.clinicService.deleteVisit(visit);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
