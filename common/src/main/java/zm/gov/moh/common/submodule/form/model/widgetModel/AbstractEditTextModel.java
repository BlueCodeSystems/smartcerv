package zm.gov.moh.common.submodule.form.model.widgetModel;

public abstract class AbstractEditTextModel extends AbstractLabelModel implements FormEditTextModel {

    protected String hint;
    protected String text;
    protected String dataType;


    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getHint() {
        return hint;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
