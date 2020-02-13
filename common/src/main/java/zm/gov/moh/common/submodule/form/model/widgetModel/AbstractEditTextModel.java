package zm.gov.moh.common.submodule.form.model.widgetModel;

public abstract class AbstractEditTextModel extends AbstractLabelModel implements FormEditTextModel {

    protected String hint;
    protected String text;
    protected String regex;
    protected Boolean required;
    protected String dataType;
    protected String FutureDate;


    public String getFutureDate() {
        return FutureDate;
    }

    public void setFutureDate(String futureDate) {
        this.FutureDate = futureDate;
    }


    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

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


    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
