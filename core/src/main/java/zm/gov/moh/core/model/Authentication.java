package zm.gov.moh.core.model;

import zm.gov.moh.core.repository.database.entity.domain.Location;



public class Authentication {

    private User user;
    private String token;

    public User getUser() {
        return user;
    }

    public String getUserUuid() {
        return user.getUuid();
    }

    public String getToken() {
        return token;
    }
}