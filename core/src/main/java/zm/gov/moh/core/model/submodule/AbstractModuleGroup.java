package zm.gov.moh.core.model.submodule;

import android.graphics.Bitmap;

import java.util.List;

import zm.gov.moh.core.model.Criteria;

public abstract class AbstractModuleGroup extends AbstractIconCriteriaModule implements ModuleGroup {

    protected List<Module> modules;

    AbstractModuleGroup(String string, Class classInstance, Bitmap icon, Criteria criteria, List<Module> module){
        super(string,classInstance,icon,criteria);
        this.modules = module;
    }

    AbstractModuleGroup(String string, Class classInstance, Bitmap icon, List<Module> module){
        super(string,classInstance,icon);
        this.modules = module;
    }

    AbstractModuleGroup(String string, Class classInstance, Criteria criteria, List<Module> module){
        super(string,classInstance,criteria);
        this.modules = module;
    }

    AbstractModuleGroup(String string, Class classInstance, List<Module> module){
        super(string,classInstance);
        this.modules = module;
    }

    @Override
    public List<Module> getModules() {
        return modules;
    }

    @Override
    public Module getSubmodule(String name){

        for (Module module : modules)
            if(module.getName().equals(name))
                return module;

        return null;
    }
}
