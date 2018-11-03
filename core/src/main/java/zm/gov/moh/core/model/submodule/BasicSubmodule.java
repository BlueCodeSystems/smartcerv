package zm.gov.moh.core.model.submodule;

public class BasicSubmodule extends AbstractSubmodule {

    public BasicSubmodule(String name, Class classInstance){
        super(name,classInstance);
    }
}

/*
package zm.gov.moh.core.model.submodule;

import android.graphics.Bitmap;

import java.io.Serializable;

import zm.gov.moh.core.model.Criteria;

public class Submodule implements Serializable {

    private String name;
    private Bitmap icon;
    private Criteria criteria;
    private Class classInstance;

    public Submodule(String name, Class classInstance, Criteria criteria){

        this.name = name;
        this.classInstance = classInstance;
        this.criteria = criteria;
    }

    public Submodule(String name, Class classInstance){

        this.name = name;
        this.classInstance = classInstance;
    }

    public Submodule(String name, Class classInstance, Bitmap icon){

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
 public class SubmoduleGroup {

 private String name;
 List<Submodule> submodules;
 Criteria criteria;

 public SubmoduleGroup(String name, List<Submodule> submodules){

 this.name = name;
 this.submodules = submodules;
 }

 public SubmoduleGroup(String name, List<Submodule> submodules, Criteria criteria){
 this(name, submodules);
 this.criteria = criteria;
 }

 public String getName() {
 return name;
 }

 public List<Submodule> getSubmodules() {

 return submodules;
 }

 public Submodule getSubmodule(int index){

 return submodules.get(index);
 }

 public boolean evaluateByCriteria(Object sample) throws Exception{

 return criteria.evaluate(sample);
 }
 }

 */