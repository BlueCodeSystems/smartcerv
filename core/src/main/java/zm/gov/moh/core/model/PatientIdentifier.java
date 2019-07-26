package zm.gov.moh.core.model;

public class PatientIdentifier {

    private String identifierType;
    private String identifier;
    private String location;
    private Short preferred;

    public String getIdentifier() {
        return identifier;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setIdentifierType(String identifierTypeUuid) {
        this.identifierType = identifierTypeUuid;
    }

    public void setLocation(String locationUuid) {
        this.location = locationUuid;
    }

    public String getLocation() {
        return location;
    }

    public void setPreferred(Short preferred) {
        this.preferred = preferred;
    }

    public Short getPreferred() {
        return preferred;
    }
}