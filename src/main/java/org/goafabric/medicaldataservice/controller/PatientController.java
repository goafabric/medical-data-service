package org.goafabric.medicaldataservice.controller;

import org.goafabric.medicaldataservice.controller.dto.Patient;
import org.goafabric.medicaldataservice.logic.PatientLogic;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientLogic patientLogic;

    public PatientController(PatientLogic patientLogic) {
        this.patientLogic = patientLogic;
    }

    @GetMapping("/{id}")
    public Patient getById(@PathVariable String id) {
        return patientLogic.getById(id);
    }

    @GetMapping("/")
    public List<Patient> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return patientLogic.findAll(page, size);
    }

    @PostMapping
    public Patient save(@RequestBody Patient patient) {
        return null;
    }

}
