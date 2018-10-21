package zm.gov.moh.core.utils;

import java.io.Serializable;

public  class ClassHolder implements Serializable {

     public static final String KEY = "ClassHolderInstance";

     private Class classInstance;
     public ClassHolder(Class classInstance){
         this.classInstance = classInstance;
     }

     public Class getClassInstance() {
         return classInstance;
     }
}
