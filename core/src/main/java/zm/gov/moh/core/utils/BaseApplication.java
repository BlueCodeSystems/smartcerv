package zm.gov.moh.core.utils;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zm.gov.moh.core.model.submodule.ModuleGroup;

public class BaseApplication extends Application {

    protected String buildName;
    protected Map<String, zm.gov.moh.core.model.submodule.Module> submodules;
    protected ArrayList<ModuleGroup> firstPointOfCareSubmodules;

    @Override
    public void onCreate() {
        super.onCreate();

        //Load modules
        submodules = new HashMap<>();
        firstPointOfCareSubmodules = new ArrayList<>();
    }

    public String getBuildName() {
        return buildName;
    }

    public zm.gov.moh.core.model.submodule.Module getModule(String submodule){

        return submodules.get(submodule);
    }

    public void registerModule(String name, zm.gov.moh.core.model.submodule.Module module){

        submodules.put(name , module);
    }

    public void loadFirstPointOfCareSubmodule(ModuleGroup submodule){

        firstPointOfCareSubmodules.add(submodule);
    }

    public class CoreModule {

        public static final String REGISTRATION = "REGISTRATION";
        public static final String REGISTER = "REGISTER";
        public static final String FIRST_POINT_OF_CARE = "FIRST_POINT_OF_CARE";
        public static final String HOME = "HOME";
        public static final String LOGIN = "LOGIN";
        public static final String VITALS = "VITALS";
        public static final String CLIENT_DASHOARD = "CLIENT_DASHOARD";
        public static final String FORM = "FORM";
        public static final String VISIT = "VISIT";
        public static final String SETTINGS = "SETTINGS";
        public static final String BOOTSTRAP = "BOOTSTRAP";
    }

    public class Module {

        public static final String CERVICAL_CANCER = "CERVICAL_CANCER";
    }

    public List<ModuleGroup> getCareServices(){

        return firstPointOfCareSubmodules;
    }
}

