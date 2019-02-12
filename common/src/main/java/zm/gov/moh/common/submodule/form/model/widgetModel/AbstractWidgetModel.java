package zm.gov.moh.common.submodule.form.model.widgetModel;

import java.util.List;

import zm.gov.moh.common.submodule.form.model.Logic;

public abstract class AbstractWidgetModel implements WidgetModel {

    protected String tag;
    protected String widgetType;
    protected int weight;
    protected List<Logic> logic;

    public AbstractWidgetModel(){
        super();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public List<Logic> getLogic() {
        return logic;
    }

    public void setLogic(List<Logic> logic) {
        this.logic = logic;
    }
}
