package zm.gov.moh.core.repository.api.model;

import java.util.Date;

public class Client {

    long clientID;
    private String firstName;
    private String secondName;
    private String gender;
    private Date dateOfBirth;

    public Client(final long clientID, final String firstName, final String secondName, final String gender, final Date dateOfBirth){

        this.clientID = clientID;
        this.firstName = firstName;
        this.secondName = secondName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
}
