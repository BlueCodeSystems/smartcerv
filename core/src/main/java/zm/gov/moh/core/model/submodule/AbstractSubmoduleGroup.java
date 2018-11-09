package zm.gov.moh.core.model.submodule;

import android.graphics.Bitmap;

import java.util.List;

import zm.gov.moh.core.model.Criteria;

public abstract class AbstractSubmoduleGroup extends AbstractIconCriteriaSubmodule implements SubmoduleGroup {

    protected List<Submodule> submodules;

    AbstractSubmoduleGroup(String string, Class classInstance, Bitmap icon, Criteria criteria,List<Submodule> submodule){
        super(string,classInstance,icon,criteria);
        this.submodules = submodule;
    }

    AbstractSubmoduleGroup(String string, Class classInstance, Bitmap icon,List<Submodule> submodule){
        super(string,classInstance,icon);
        this.submodules = submodule;
    }

    AbstractSubmoduleGroup(String string, Class classInstance, Criteria criteria, List<Submodule> submodule){
        super(string,classInstance,criteria);
        this.submodules = submodule;
    }

    AbstractSubmoduleGroup(String string, Class classInstance,List<Submodule> submodule){
        super(string,classInstance);
        this.submodules = submodule;
    }

    @Override
    public List<Submodule> getSubmodules() {
        return submodules;
    }
}
