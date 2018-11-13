package zm.gov.moh.common.submodule.form.model.widget;

import java.util.List;

public interface WidgetGroupModel extends WidgetModel {

    void addChild(WidgetModel widgetModel);
    void addChildren(List<WidgetModel> components);
    WidgetModel getChild(int index);
    List<WidgetModel> getChildren();
    void setTitle(String title);
    String getTitle();
}