package zm.gov.moh.common.submodule.form.model;

public class Action{

    String type;
    Metadata metadata;

    public String getType() {
        return type;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}