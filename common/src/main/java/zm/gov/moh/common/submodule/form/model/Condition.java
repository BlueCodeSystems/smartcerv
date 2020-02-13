package zm.gov.moh.common.submodule.form.model;

import java.io.Serializable;

public class Condition implements Serializable {

    private Object value;
    //Add expression object
    private Expression expression;
    private String dataType;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    //Setter for expression
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    //Getter for expression
    public Expression getExpression() {
        return expression;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }
}