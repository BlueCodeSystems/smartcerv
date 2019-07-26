package zm.gov.moh.core.model.submodule;

public class BasicModule extends AbstractModule {

    public BasicModule(String name, Class classInstance){
        super(name,classInstance);
    }
}

/*
package zm.gov.moh.core.model.submodule;

import android.graphics.Bitmap;

import java.io.Serializable;

import zm.gov.moh.core.model.Criteria;

public class Module implements Serializable {

    private String name;
    private Bitmap icon;
    private Criteria criteria;
    private Class classInstance;

    public Module(String name, Class classInstance, Criteria criteria){

        this.name = name;
        this.classInstance = classInstance;
        this.criteria = criteria;
    }

    public Module(String name, Class classInstance){

        this.name = name;
        this.classInstance = classInstance;
    }

    public Module(String name, Class classInstance, Bitmap icon){

        this(name,classInstance);
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public Class getClassInstance() {
        return classInstance;
    }
}


/**
 public class ModuleGroup {

 private String name;
 List<Module> modules;
 Criteria criteria;

 public ModuleGroup(String name, List<Module> modules){

 this.name = name;
 this.modules = modules;
 }

 public ModuleGroup(String name, List<Module> modules, Criteria criteria){
 this(name, modules);
 this.criteria = criteria;
 }

 public String getName() {
 return name;
 }

 public List<Module> getModules() {

 return modules;
 }

 public Module getModule(int index){

 return modules.get(index);
 }

 public boolean evaluateByCriteria(Object sample) throws Exception{

 return criteria.evaluate(sample);
 }
 }

 */