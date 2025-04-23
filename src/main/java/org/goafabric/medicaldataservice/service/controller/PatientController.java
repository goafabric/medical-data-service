package org.goafabric.medicaldataservice.service.controller;

import org.goafabric.medicaldataservice.service.controller.dto.Patient;
import org.goafabric.medicaldataservice.service.controller.dto.SocketMessage;
import org.goafabric.medicaldataservice.service.logic.PatientLogic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/patients")
@MessageMapping(value = "patients")
public class PatientController {
    private final PatientLogic logic;

    public PatientController(PatientLogic logic) {
        this.logic = logic;
    }

    @GetMapping("/{id}")
    public Patient getById(@PathVariable String id) {
        return logic.getById(id);
    }

    @GetMapping
    public List<Patient> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return logic.findAll(page, size);
    }

    @PostMapping
    public Patient save(@RequestBody Patient patient) {
        return logic.save(patient);
    }


    @GetMapping("/create-patient")
    @MessageMapping("create-patient")
    @SendTo("/public")
    public SocketMessage createPatient() {
        logic.save(new Patient("Homer", "Simpson " + System.currentTimeMillis(), "male", LocalDate.of(1978, 5, 12)));
        return new SocketMessage("Patient created");
    }

}
