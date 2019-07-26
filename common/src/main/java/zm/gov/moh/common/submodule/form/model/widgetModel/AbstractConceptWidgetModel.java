package zm.gov.moh.common.submodule.form.model.widgetModel;

public abstract class AbstractConceptWidgetModel extends OpenmrsEntity implements ConceptWidgetModel,LabelModel,FormEditTextModel,WidgetModel{

    @Deprecated
    private long conceptId;
    private String dataType;
    private String label;
    private int textSize;
    private String hint;
    private String text;
    private String style;

    @Override
    public long getConceptId() {
        return conceptId;
    }

    @Override
    public void setConceptId(long id) {
        this.conceptId = id;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    @Override
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
    public int getTextSize() {
        return this.textSize;
    }

    @Override
    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String getHint() {
        return hint;
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
    public String getStyle() {
        return style;
    }

    @Override
    public void setStyle(String style) {
        this.style = style;
    }
}
