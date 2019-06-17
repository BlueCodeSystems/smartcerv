package zm.gov.moh.core.model;

import org.threeten.bp.LocalDateTime;

public class Encounter {

    private String encounterType;
    private LocalDateTime dateTime;
    private Obs[] obs;
    private String providerUuid;
    private String locationUuid;
}
