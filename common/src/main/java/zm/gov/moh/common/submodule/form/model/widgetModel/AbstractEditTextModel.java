package zm.gov.moh.common.submodule.form.model.widgetModel;

public abstract class AbstractEditTextModel extends AbstractWidgetModel implements FormEditTextModel {

    protected String hint;
    protected String text;


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
}
