package com.springboot.hospital.controller;

import com.springboot.hospital.dto.CiteDTO;
import com.springboot.hospital.mapper.CitaMapper;
import com.springboot.hospital.mapper.DoctorMapper;
import com.springboot.hospital.mapper.PatientMapper;
import com.springboot.hospital.model.Cite;
import com.springboot.hospital.model.StatusCite;
import com.springboot.hospital.service.CiteService;
import com.springboot.hospital.service.DoctorService;
import com.springboot.hospital.service.PatientService;
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
@RequestMapping("/api/cite")
@Tag(name = "Cite")
public class CiteController {

    @Autowired
    private CiteService citeService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Operation(summary = "get all cite")
    @GetMapping
    public ResponseEntity<List<CiteDTO>> listCite(){
        List<CiteDTO> cite = citeService.getAllCite();
        return new ResponseEntity<>(cite, HttpStatus.OK);
    }

    @Operation(summary = "get all cite for id")

    @GetMapping("/{id}")
    public ResponseEntity<CiteDTO> listCitePorId(@PathVariable Long id){
        Optional<CiteDTO> citaDTOOptional = citeService.getCiteById(id);
        return citaDTOOptional.map(cite -> new ResponseEntity<>(cite,HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "save cite")

    @PostMapping("/{idPatient}/{idDoctor}")
    public ResponseEntity<CiteDTO> saveCite(@RequestBody CiteDTO citeDTO, @PathVariable Long idPatient, @PathVariable Long idDoctor) throws ParseException {
        Cite newCite = citeService.createCite(citeDTO,idPatient,idDoctor);

        if(newCite == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CiteDTO newCiteDTO = citaMapper.toDTO(newCite);
        return new ResponseEntity<>(citeDTO,HttpStatus.CREATED);
    }

    @Operation(summary = "update cite")


    @PutMapping("/{id}")
    public ResponseEntity<CiteDTO> updateCite(@PathVariable Long id, @RequestBody CiteDTO citeDTO) throws ParseException {
        CiteDTO citeUpdate = citeService.updateCite(id, citeDTO);
        if(citeUpdate != null){
            return ResponseEntity.ok(citeUpdate);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "delete cite")


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCite(@PathVariable Long id){
        citeService.deleteCite(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "list Cite For PatientId")

    @GetMapping("/patient/{PatientId}")
    public List<CiteDTO> listCiteForPatientId(@PathVariable Long PatientId){
        return citeService.getCiteByPatientId(PatientId);
    }

    @Operation(summary = "list Cite For DoctorId")


    @GetMapping("/doctor/{doctorId}")
    public List<CiteDTO> listCiteForDoctorId(@PathVariable Long doctorId){
        return citeService.getCitesByDoctorId(doctorId);
    }

    @Operation(summary = "list Cite For Status")


    @GetMapping("/status/{statusCite}")
    public List<CiteDTO> listCiteForStatus(@PathVariable StatusCite statusCite){
        return citeService.getCitesByStatusCite(statusCite);
    }
}
