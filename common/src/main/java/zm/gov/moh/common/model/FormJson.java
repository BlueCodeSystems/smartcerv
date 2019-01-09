package zm.gov.moh.common.model;

import java.io.Serializable;

public class FormJson implements Serializable {

    private String name;
    private String json;

    public FormJson(final String name, final String json){
        this.name = name;
        this.json = json;
    }

    public String getName() {
        return name;
    }

    public String getJson() {
        return json;
    }
}