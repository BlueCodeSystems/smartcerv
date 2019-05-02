package zm.gov.moh.common.submodule.form.model.widgetModel;

public abstract class AbstractButtonModel extends AbstractWidgetModel implements ButtonModel {

    protected String text;

    public AbstractButtonModel(){
        super();
    }

    public void setText(String label){
        this.text = label;
    }

    public String getText() {
        return text;
    }

    public abstract void setLabel(String label);
}