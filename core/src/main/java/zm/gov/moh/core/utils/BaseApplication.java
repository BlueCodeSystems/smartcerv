package zm.gov.moh.core.utils;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.model.submodule.SubmoduleGroup;

public class BaseApplication extends Application {


    protected Map<String, Submodule> submodules;
    protected ArrayList<SubmoduleGroup> firstPointOfCareSubmodules;

    @Override
    public void onCreate() {
        super.onCreate();

        //Load submodules
        submodules = new HashMap<>();
        firstPointOfCareSubmodules = new ArrayList<>();
    }

    public Submodule getSubmodule(String submodule){

        return submodules.get(submodule);
    }

    public void registerModule(String name, Submodule submodule){

        submodules.put(name , submodule);
    }

    public void loadFirstPointOfCareSubmodule(SubmoduleGroup submodule){

        firstPointOfCareSubmodules.add(submodule);
    }

    public class CoreModule {

        public static final String REGISTRATION = "REGISTRATION";
        public static final String REGISTER = "REGISTER";
        public static final String FIRST_POINT_OF_CARE = "FIRST_POINT_OF_CARE";
        public static final String FIRST_POINT_OF_CONTACT = "FIRST_POINT_OF_CONTACT";
        public static final String LOGIN = "LOGIN";
        public static final String VITALS = "VITALS";
        public static final String CLIENT_DASHOARD = "CLIENT_DASHOARD";
        public static final String FORM = "FORM";
    }

    public class Module {

        public static final String CERVICAL_CANCER = "CERVICAL_CANCER";
    }

    public List<SubmoduleGroup> getCareServices(){

        return firstPointOfCareSubmodules;
    }
}

