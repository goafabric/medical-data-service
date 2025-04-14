package org.goafabric.medicaldataservice.persistence.extensions;

import jakarta.transaction.Transactional;
import org.goafabric.medicaldataservice.controller.dto.Patient;
import org.goafabric.medicaldataservice.logic.PatientLogic;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Transactional
public class DemoDataImporter implements CommandLineRunner {
    private final PatientLogic patientLogic;

    public DemoDataImporter(PatientLogic patientLogic) {
        this.patientLogic = patientLogic;
    }


    @Override
    public void run(String... args) throws Exception {
        createPatients().forEach(patientLogic::save);
    }

    private List<Patient> createPatients() {
        return List.of(
            new Patient("Homer", "Simpson", "male", LocalDate.of(1978, 5, 12)),
            new Patient("Marge", "Simpson", "female", LocalDate.of(1979, 3, 19)),
            new Patient("Lisa", "Simpson", "female", LocalDate.of(2012, 5, 9)),
            new Patient("Bart", "Simpson", "male", LocalDate.of(2010, 2, 23)),
            new Patient("Maggie", "Simpson", "female", LocalDate.of(2020, 1, 14))
        );
    }

}
