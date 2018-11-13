package zm.gov.moh.common.submodule.form.model.widget;

public abstract class AbstractLabelModel extends AbstractWidgetModel implements LabelModel {

    protected String label;
    protected String text;
    protected int textSize;

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
    public int getTextSize() {
        return this.textSize;
    }
}