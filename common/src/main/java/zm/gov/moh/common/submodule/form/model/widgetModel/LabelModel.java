package zm.gov.moh.common.submodule.form.model.widgetModel;

public interface LabelModel extends WidgetModel {
    void setLabel(String label);
    String getLabel();

    void setText(String text);
    String getText();

    void setTextSize(int text);
    int getTextSize();
}
