package zm.gov.moh.core.repository.database.entity.system;

import java.util.HashMap;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

public enum EntityType {

    PATIENT(1),
    ENCOUNTER(2),
    VISIT(3),
    OBS(4),
    PERSON(5),
    PERSON_NAME(6),
    PERSON_ADDRESS(7),
    CONCEPT(8),
    CONCEPT_NAME(9),
    CONCEPT_ANSWER(10),
    LOCATION(11);


    private int id;

    EntityType(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
