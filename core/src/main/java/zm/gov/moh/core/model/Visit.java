package zm.gov.moh.core.model;

import org.threeten.bp.LocalDateTime;

public class Visit {

    private String patientUuid;
    private String visitType;
    private LocalDateTime dateTimeStarted;
    private LocalDateTime dateTimeStopped;
    private Encounter[] encounters;
}
