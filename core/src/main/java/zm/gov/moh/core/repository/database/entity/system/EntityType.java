package zm.gov.moh.core.repository.database.entity.system;

import java.util.HashMap;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

public enum EntityType {

    PERSON(1),
    PERSON_NAME(2),
    PERSON_ADDRESS(3),
    PATIENT(4),
    PATIENT_IDENTIFIER(5),
    OBS(6),
    ENCOUNTER(7),
    VISIT(8);


    private int id;

    EntityType(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
