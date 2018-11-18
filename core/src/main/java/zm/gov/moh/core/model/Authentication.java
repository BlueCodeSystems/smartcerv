package zm.gov.moh.core.model;

class User {

    private String uuid;
    private String display;
    private String username;
    private String systemId;

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
}

public class Authentication {

    private User user;
    private String token;

    public User getUser() {
        return user;
    }

    public String getUuid() {
        return user.getUuid();
    }
}