package zm.gov.moh.common.submodule.form.model;

import java.io.Serializable;
import java.util.List;

import zm.gov.moh.common.submodule.form.model.attribute.FormAttribute;
import zm.gov.moh.common.submodule.form.model.widgetModel.WidgetSectionModel;

public class FormModel implements Serializable {

    private FormAttribute attributes;
    private List<WidgetSectionModel> widgetGroup;

    public void setAttributes(FormAttribute attributes) {
        this.attributes = attributes;
    }

    public FormAttribute getAttributes() {
        return attributes;
    }

    public List<WidgetSectionModel> getWidgetGroup() {
        return widgetGroup;
    }

    public void setWidgetGroup(List<WidgetSectionModel> widgetGroup) {
        this.widgetGroup = widgetGroup;
    }
}
