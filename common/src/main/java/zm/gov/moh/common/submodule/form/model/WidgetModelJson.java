package zm.gov.moh.common.submodule.form.model;

import java.util.List;

public class WidgetModelJson {

    protected String tag;
    protected String widgetType;
    protected String title;
    protected List<WidgetModel> widgetModels;
    protected String hint;
    protected String text;

    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public void setComponents(List<WidgetModel> components) {
        this.widgetModels = components;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidgetType() {
        return widgetType;
    }

    public List<WidgetModel> getComponents() {
        return widgetModels;
    }

    public String getTag() {
        return tag;
    }

    public String getHint() {
        return hint;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }
}
