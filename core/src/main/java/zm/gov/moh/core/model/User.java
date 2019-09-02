package zm.gov.moh.core.model;

import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.Provider;

public class User {

    private String uuid;
    private String display;
    private String username;
    private String systemId;
    private long userId;
    private Location[] location;
    private PersonName personName;
    private Provider provider;

    public String getDisplay() {
        return display;
    }

    public String getSystemId() {
        return systemId;
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    public Location [] getLocation() {
        return location;
    }

    public Provider getProvider() {
        return provider;
    }

    public long getUserId() {
        return userId;
    }

    public PersonName getPersonName() {
        return personName;
    }
}