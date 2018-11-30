package zm.gov.moh.common.submodule.form.model.widgetModel;

public abstract class AbstractWidgetModel implements WidgetModel {

    protected String tag;
    protected String widgetType;
    protected int weight;

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
}
