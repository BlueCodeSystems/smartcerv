package zm.gov.moh.common.submodule.form.model.widgetModel;

public abstract class AbstractCodedConceptWidgetModel extends AbstractConceptWidgetModel {

    protected String style;

    public AbstractCodedConceptWidgetModel(){
        super();
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
