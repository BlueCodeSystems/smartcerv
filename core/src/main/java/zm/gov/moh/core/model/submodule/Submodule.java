package zm.gov.moh.core.model.submodule;

import java.io.Serializable;

public interface Submodule extends Serializable {

    String getName();

    Class getClassInstance();
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