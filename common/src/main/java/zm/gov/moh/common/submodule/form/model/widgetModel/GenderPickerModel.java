package zm.gov.moh.common.submodule.form.model.widgetModel;

public class GenderPickerModel extends AbstractLabelModel implements Stylable {

    protected String style;

    @Override
    public String getStyle() {
        return style;
    }

    @Override
    public void setStyle(String style) {
        this.style = style;
    }
}
