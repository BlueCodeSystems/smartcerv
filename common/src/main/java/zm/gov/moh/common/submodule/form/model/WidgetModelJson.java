package zm.gov.moh.common.submodule.form.model;

import java.util.List;

import zm.gov.moh.common.submodule.form.model.widget.WidgetModel;

public class WidgetModelJson {

    protected String tag;
    protected String widgetType;
    protected String title;
    protected List<WidgetModel> widgets;
    protected String hint;
    protected String text;
    protected String label;
    protected int weight;
    protected int textSize;
    protected String facilityText;
    protected String districtText;


    public void setWidgetType(String widgetType) {
        this.widgetType = widgetType;
    }

    public void setWidgets(List<WidgetModel> components) {
        this.widgets = components;
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
        return widgets;
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

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public List<WidgetModel> getWidgets() {
        return widgets;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setDistrictText(String districtText) {
        this.districtText = districtText;
    }

    public void setFacilityText(String faciltyText) {
        this.facilityText = faciltyText;
    }

    public String getDistrictText() {
        return districtText;
    }

    public String getFacilityText() {
        return facilityText;
    }
}
