package zm.gov.moh.common.submodule.form.model;

import com.squareup.moshi.Json;

// Class was added to implement usage of comparison operators
public class Expression {

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