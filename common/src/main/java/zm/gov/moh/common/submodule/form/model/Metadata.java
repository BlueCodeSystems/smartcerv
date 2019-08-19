package zm.gov.moh.common.submodule.form.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Metadata implements Serializable {

    private Set<String> tags;

    public Set<String> getTags() {
        return tags;
    }
}