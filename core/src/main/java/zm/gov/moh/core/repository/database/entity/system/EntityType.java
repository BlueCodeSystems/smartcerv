package zm.gov.moh.core.repository.database.entity.system;

import java.util.HashMap;

import zm.gov.moh.core.repository.database.dao.Synchronizable;
import zm.gov.moh.core.repository.database.entity.domain.Person;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;

public enum EntityType {

    PATIENT(1),
    ENCOUNTER(2),
    VISIT(3);


    private int id;

    EntityType(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
