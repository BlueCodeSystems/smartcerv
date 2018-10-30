package zm.gov.moh.core.utils;

import java.io.Serializable;

public class Submodule implements Serializable {

    private String name;
    private int iconResource;
    private Class classInstance;

    public Submodule(String name, int iconResource, Class classInstance){

        this.name = name;
        this.iconResource = iconResource;
        this.classInstance = classInstance;
    }

    public String getName() {
        return name;
    }

    public int getIconResource() {
        return iconResource;
    }

    public Class getClassInstance() {
        return classInstance;
    }
}
