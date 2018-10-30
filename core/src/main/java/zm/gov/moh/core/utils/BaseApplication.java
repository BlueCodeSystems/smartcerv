package zm.gov.moh.core.utils;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        public static final String FIND_PATIENT = "FIND_PATIENT";
        public static final String FIRST_POINT_OF_CARE = "FIRST_POINT_OF_CARE";
        public static final String FIRST_POINT_OF_CONTACT = "FIRST_POINT_OF_CONTACT";
        public static final String LOGIN = "LOGIN";
        public static final String VITALS = "VITALS";
    }
}

