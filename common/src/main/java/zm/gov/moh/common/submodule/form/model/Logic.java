package zm.gov.moh.common.submodule.form.model;

import java.io.Serializable;
import java.util.List;

public class Logic implements Serializable {

    private Condition condition;
    private Action action;

    public Action getAction() {
        return action;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}