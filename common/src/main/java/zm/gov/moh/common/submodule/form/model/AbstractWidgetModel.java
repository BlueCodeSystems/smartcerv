package zm.gov.moh.common.submodule.form.model;

public abstract class AbstractWidgetModel implements WidgetModel {

    protected String tag;
    protected String widgetType;

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
}
