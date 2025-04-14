package org.goafabric.medicaldataservice.controller;

import org.goafabric.medicaldataservice.controller.dto.Patient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final List<Patient> patients = new ArrayList<>();


    @GetMapping("/{id}")
    public Patient getById(@PathVariable String id) {
        return null;
    }

    @PostMapping
    public Patient save(@RequestBody Patient patient) {
        return null;
    }

}
