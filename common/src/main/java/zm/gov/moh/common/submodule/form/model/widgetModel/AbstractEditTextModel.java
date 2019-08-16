package zm.gov.moh.common.submodule.form.model.widgetModel;

public abstract class AbstractEditTextModel extends AbstractLabelModel implements FormEditTextModel {

    protected String hint;
    protected String text;
    protected String regex;
    protected String errorMessage;


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

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getRegex() {
        return regex;
    }
}
