package com.springboot.hospital.controller;

import com.springboot.hospital.dto.ConsultDTO;
import com.springboot.hospital.mapper.CiteMapper;
import com.springboot.hospital.service.CiteService;
import com.springboot.hospital.service.ConsultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consult")
@Tag(name = "Consult")

public class ConsultController {

    @Autowired
    private ConsultService consultService;

    @Autowired
    private CiteMapper citeMapper;

    @Autowired
    private CiteService citeService;

    @Operation(summary = "list Consult")
    @GetMapping
    public ResponseEntity<List<ConsultDTO>> listConsult(){
        List<ConsultDTO> consult = consultService.getAllConsult();
        return new ResponseEntity<>(consult, HttpStatus.OK);
    }
    @Operation(summary = "Consult for id")


    @GetMapping("/{id}")
    public ResponseEntity<ConsultDTO> getConsult(@PathVariable Long id){
        Optional<ConsultDTO> consult = consultService.getConsultById(id);
        return consult.map(dto -> new ResponseEntity<>(dto,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Save Consult")

    @PostMapping
    public ResponseEntity<ConsultDTO> saveConsult(@RequestParam Long citeId, @RequestBody ConsultDTO consultDTO) throws ParseException {
        ConsultDTO createdConsult = consultService.createConsult(citeId, consultDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdConsult);
    }
    @Operation(summary = "Update Consult")

    @PutMapping("/{id}")
    public ResponseEntity<ConsultDTO> updateConsult(@PathVariable Long id, @RequestBody ConsultDTO consultDTO) throws ParseException {
        ConsultDTO updateConsult = consultService.updateConsult(id, consultDTO);
        return updateConsult != null
                ? new ResponseEntity<>(updateConsult,HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Delete Consult")

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsult(@PathVariable Long id){
        consultService.deleteConsult(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "list Consult for Info")

    @GetMapping("/search")
    public ResponseEntity<List<ConsultDTO>> listConsultPorInfo(@RequestParam String searchTerm){
        List<ConsultDTO> consultDTOS = consultService.getConsultByInformContaining(searchTerm);
        return new ResponseEntity<>(consultDTOS,HttpStatus.OK);
    }
    @Operation(summary = "list Consult For Cite")

    @GetMapping("/cite/{citeId}")
    public ResponseEntity<List<ConsultDTO>> listConsultForCite(@PathVariable Long citeId) throws ParseException {
        List<ConsultDTO> consultDTOS = consultService.getConsultByCite(citeId);
        return new ResponseEntity<>(consultDTOS,HttpStatus.OK);
    }
}
