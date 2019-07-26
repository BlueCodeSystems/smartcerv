package zm.gov.moh.core.utils;

import java.io.Serializable;

public  class SerializedClassInstance implements Serializable {

     public static final String KEY = "ClassHolderInstance";

     private Class classInstance;
     public SerializedClassInstance(Class classInstance){
         this.classInstance = classInstance;
     }

     public Class getClassInstance() {
         return classInstance;
     }
}
