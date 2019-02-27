package zm.gov.moh.core.model.submodule;

import java.util.List;

import zm.gov.moh.core.model.Criteria;

public class CriteriaModuleGroup extends AbstractModuleGroup {

    CriteriaModuleGroup(String string, Class classInstance, Criteria criteria, List<Module> module){
        super(string,classInstance,criteria, module);
        this.modules = module;
    }
}

/*
package zm.gov.moh.core.model.submodule;

import android.graphics.Bitmap;

import java.util.List;

import zm.gov.moh.core.model.Criteria;

public abstract class AbstractModuleGroup extends AbstractIconCriteriaModule implements ModuleGroup {

    protected List<Module> modules;

    AbstractModuleGroup(String string, Class classInstance, Bitmap icon, Criteria criteria,List<Module> submodule){
        super(string,classInstance,icon,criteria);
        this.modules = submodule;
    }

    AbstractModuleGroup(String string, Class classInstance, Bitmap icon,List<Module> submodule){
        super(string,classInstance,icon);
        this.modules = submodule;
    }

    AbstractModuleGroup(String string, Class classInstance,List<Module> submodule){
        super(string,classInstance);
        this.modules = submodule;
    }

    @Override
    public List<Module> getModules() {
        return modules;
    }
}

 */