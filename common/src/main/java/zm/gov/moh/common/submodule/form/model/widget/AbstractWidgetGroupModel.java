package zm.gov.moh.common.submodule.form.model.widget;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWidgetGroupModel extends AbstractWidgetModel implements WidgetGroupModel {

    protected String title;
    protected List<WidgetModel> widgets;

    protected AbstractWidgetGroupModel(){
        super();
    }

    AbstractWidgetGroupModel(List<WidgetModel> widgets){
        this();
        this.widgets = widgets;
    }

    public void addChild(WidgetModel widgetModel){

        if(widgets == null)
            widgets = new ArrayList<>();

        widgets.add(widgetModel);
    }

    public void addChildren(List<WidgetModel> widgets){

        if(this.widgets == null)
            this.widgets = new ArrayList<>();

        this.widgets.addAll(widgets);
    }

    public WidgetModel getChild(int index){

        if(widgets != null)
            return widgets.get(index);
        return null;

    }

    public List<WidgetModel> getChildren(){

        if(widgets != null)
            return widgets;
        return null;

    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }
}
