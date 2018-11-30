package zm.gov.moh.common.submodule.form.model.widgetModel;

public class DistrictFacilityPickerModel extends AbstractWidgetModel{

    protected String facilityText;
    protected String districtText;
    public DistrictFacilityPickerModel(){
        super();
    }

    public void setDistrictText(String districtText) {
        this.districtText = districtText;
    }

    public void setFacilityText(String facilityText) {
        this.facilityText = facilityText;
    }

    public String getDistrictText() {
        return districtText;
    }

    public String getFacilityText() {
        return facilityText;
    }
}

