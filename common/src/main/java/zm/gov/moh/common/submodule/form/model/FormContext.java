package zm.gov.moh.common.submodule.form.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormContext {

    private Set<String> visibleWidgetTags;

    public FormContext(){
        visibleWidgetTags = new HashSet<>();
    }

    public Set<String> getVisibleWidgetTags() {
        return visibleWidgetTags;
    }
}
