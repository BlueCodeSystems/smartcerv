package zm.gov.moh.common.submodule.form.model.widgetModel;

import java.io.Serializable;
import java.util.List;

public class WidgetSectionModel extends AbstractWidgetGroupModel implements Serializable {

    public WidgetSectionModel(){
        super();
    }

    public WidgetSectionModel(List<WidgetModel> components){
        super(components);
    }
}
