package org.goafabric.medicaldataservice.service.controller;

import org.goafabric.medicaldataservice.service.controller.dto.MedicalRecord;
import org.goafabric.medicaldataservice.service.logic.MedicalRecordLogic;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/MedicalRecords")
public class MedicalRecordController {
    private final MedicalRecordLogic logic;

    public MedicalRecordController(MedicalRecordLogic logic) {
        this.logic = logic;
    }

    @GetMapping("/{id}")
    public MedicalRecord getById(@PathVariable String id) {
        return logic.getById(id);
    }

    @GetMapping("/")
    public List<MedicalRecord> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return logic.findAll(page, size);
    }

    @PostMapping
    public MedicalRecord save(@RequestBody MedicalRecord MedicalRecord) {
        return null;
    }

}
