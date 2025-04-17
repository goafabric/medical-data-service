package org.goafabric.medicaldataservice.service.fhir;

import java.util.List;

public record HumanName(
        String use,       // "usual", "official", "temp", "nickname", etc.
        String family,
        List<String> given
) {}