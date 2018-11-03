package zm.gov.moh.core.model.submodule;

public abstract class AbstractSubmodule implements Submodule {

    private  Class classInstance;
    private String name;

    public AbstractSubmodule(String name, Class classInstance){
        this.name = name;
        this.classInstance = classInstance;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class getClassInstance() {
        return this.classInstance;
    }
}
