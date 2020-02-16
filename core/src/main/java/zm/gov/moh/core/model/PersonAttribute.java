package zm.gov.moh.core.model;

public class PersonAttribute {

    private String attributeType;
    private String value;

    public PersonAttribute(String attributeType, String value) {
        this.attributeType = attributeType;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getValue() {
        return value;
    }
}
