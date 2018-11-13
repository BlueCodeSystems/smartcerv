package zm.gov.moh.common.submodule.form.model.widget;

public interface LabelModel extends WidgetModel {
    void setLabel(String label);
    String getLabel();

    void setText(String text);
    String getText();

    void setTextSize(int text);
    int getTextSize();
}
