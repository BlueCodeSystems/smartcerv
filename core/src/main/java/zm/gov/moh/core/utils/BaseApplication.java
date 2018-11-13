package zm.gov.moh.core.utils;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import zm.gov.moh.core.model.submodule.Submodule;

public class BaseApplication extends Application {


    protected Map<String, Submodule> submodules;
    protected ArrayList<Submodule> firstPointOfCareSubmodules;

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

    public void loadSubmodule(String name, Submodule submodule){

        submodules.put(name , submodule);
    }

    public void loadFirstPointOfCareSubmodule(Submodule submodule){

        firstPointOfCareSubmodules.add(submodule);
    }

    public class CoreSubmodules{

        public static final String REGISTRATION = "REGISTRATION";
        public static final String REGISTER = "REGISTER";
        public static final String FIRST_POINT_OF_CARE = "FIRST_POINT_OF_CARE";
        public static final String FIRST_POINT_OF_CONTACT = "FIRST_POINT_OF_CONTACT";
        public static final String LOGIN = "LOGIN";
        public static final String VITALS = "VITALS";
        public static final String CLIENT_DASHOARD = "CLIENT_DASHOARD";
        public static final String FORM = "FORM";
    }

    public class CareSubmodules{

        public static final String CERVICAL_CANCER = "CERVICAL_CANCER";
    }
}

