package zm.gov.moh.common.submodule.form.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FormContext {

    private Set<String> visibleWidgetTags;

    private ArrayList<String> tags;

    public FormContext(){
        visibleWidgetTags = new HashSet<>();
        tags = new ArrayList<>();
    }

    public Set<String> getVisibleWidgetTags() {
        return visibleWidgetTags;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}
