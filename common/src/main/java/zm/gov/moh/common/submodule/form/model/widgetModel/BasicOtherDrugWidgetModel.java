package zm.gov.moh.common.submodule.form.model.widgetModel;

public class BasicOtherDrugWidgetModel extends OpenmrsEntity {

    private String drugUuid;

    public BasicOtherDrugWidgetModel() {
        super();
    }

    public String getDrugUuid() {
        return drugUuid;
    }

    public void setDrugUuid(String drugUuid) {
        this.drugUuid = drugUuid;
    }
}