package zm.gov.moh.core.model.submodule;

import java.io.Serializable;

public interface Module extends Serializable {

    String getName();

    Class getClassInstance();
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

 public Module getSubmodule(int index){

 return modules.get(index);
 }

 public boolean evaluateByCriteria(Object sample) throws Exception{

 return criteria.evaluate(sample);
 }
 }

 */