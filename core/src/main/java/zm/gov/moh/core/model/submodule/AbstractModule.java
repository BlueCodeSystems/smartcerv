package zm.gov.moh.core.model.submodule;

public abstract class AbstractModule implements Module {

    private  Class classInstance;
    private String name;

    public AbstractModule(String name, Class classInstance){
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
