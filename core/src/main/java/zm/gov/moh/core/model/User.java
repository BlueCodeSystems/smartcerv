package zm.gov.moh.core.model;

import zm.gov.moh.core.repository.database.entity.domain.Location;

public class User {

    private String uuid;
    private String display;
    private String username;
    private String systemId;
    private Integer providerId;
    private Integer userId;
    private Location[] location;

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

    public Integer getProviderId() {
        return providerId;
    }

    public Integer getUserId() {
        return userId;
    }
}