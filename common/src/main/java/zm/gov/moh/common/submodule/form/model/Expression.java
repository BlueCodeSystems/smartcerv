package zm.gov.moh.common.submodule.form.model;

import com.squareup.moshi.Json;

import java.io.Serializable;

// Class was added to implement usage of comparison operators
public class Expression implements Serializable {

    @Json(name = "$lt")
    private String lessThan;
    String $lte;
    String $eq;
    String $ne;
    String $gt;
    String $gte;
    String[] $in;
    String[] $nin;

    public String getLessThan() {
        return lessThan;
    }

    public void setLessThan(String lessThan) {
        this.lessThan = lessThan;
    }
}