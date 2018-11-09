package zm.gov.moh.common.submodule.form.model;

public abstract class AbstractButtonModel extends AbstractWidgetModel implements ButtonModel {

    protected String label;

    public AbstractButtonModel(){
        super();
    }

    public void setLabel(String label){
        this.label = label;
    }
}
