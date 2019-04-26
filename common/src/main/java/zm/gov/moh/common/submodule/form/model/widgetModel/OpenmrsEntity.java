package zm.gov.moh.common.submodule.form.model.widgetModel;

public abstract class OpenmrsEntity extends AbstractWidgetModel {
    private String uuid;

    public OpenmrsEntity() {
        super();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
