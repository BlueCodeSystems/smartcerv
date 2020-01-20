package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model;

import java.util.ArrayList;
import java.util.List;

import zm.gov.moh.core.model.submodule.BasicModule;

public class FormGroup {
    private String title;
    private List<BasicModule> formList = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<BasicModule> getFormList() {
        return formList;
    }

    public void setFormList(List<BasicModule> formList) {
        this.formList = formList;
    }
}
