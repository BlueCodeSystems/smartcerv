package zm.gov.moh.common.model;

import java.util.ArrayList;
import java.util.List;

public class FormJsonGroup {

    private String name;
    private List<FormJson> formJsons;

    public FormJsonGroup(final String name, final List<FormJson> formJsons){
        this.name = name;
        this.formJsons = formJsons;
    }

    public FormJsonGroup(final String name){
        this.name = name;
        this.formJsons = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<FormJson> getFormJsons() {
        return formJsons;
    }

    public void addForm(FormJson formJson){
        formJsons.add(formJson);
    }
}