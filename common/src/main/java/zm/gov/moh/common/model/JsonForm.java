package zm.gov.moh.common.model;

import java.io.Serializable;

public class JsonForm implements Serializable {

    private String name;
    private int status = 0;
    private String json;

    public JsonForm(final String name, final String json){
        this.name = name;
        this.json = json;
    }

    public JsonForm(final String name, final String json, int status){
        this.name = name;
        this.json = json;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getJson() {
        return json;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}