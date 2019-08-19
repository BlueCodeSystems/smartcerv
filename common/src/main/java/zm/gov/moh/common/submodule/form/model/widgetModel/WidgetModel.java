package zm.gov.moh.common.submodule.form.model.widgetModel;

import java.io.Serializable;

public interface WidgetModel extends Serializable {

    String getTag();

    void setTag(String tag);

    String getWidgetType();

    void setWidgetType(String componentType);
}
