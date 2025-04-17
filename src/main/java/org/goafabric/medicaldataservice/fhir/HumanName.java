package org.goafabric.medicaldataservice.fhir;

import java.util.List;

public record HumanName(
        String use,       // "usual", "official", "temp", "nickname", etc.
        String family,
        List<String> given
) {}