package zm.gov.moh.common.submodule.form.model.widgetModel;

public class BasicDrugWidgetModel extends OpenmrsEntity {

    private String drugUuid;

    public BasicDrugWidgetModel() {
        super();
    }

    public String getDrugUuid() {
        return drugUuid;
    }

    public void setDrugUuid(String drugUuid) {
        this.drugUuid = drugUuid;
    }
}
