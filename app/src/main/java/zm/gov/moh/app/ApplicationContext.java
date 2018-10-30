package zm.gov.moh.app;

import zm.gov.moh.app.submodule.first.point.of.care.view.FirstPointOfCareActivity;
import zm.gov.moh.app.submodule.first.point.of.contact.view.FirstPointOfContactActivity;
import zm.gov.moh.common.submodule.login.view.LoginActivity;
import zm.gov.moh.common.submodule.registration.view.RegistrationActivity;
import zm.gov.moh.common.submodule.register.view.RegisterActivity;
import zm.gov.moh.common.submodule.vitals.view.VitalsActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Submodule;

public class ApplicationContext extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //Load submodules
        loadSubmodule(CoreSubmodules.FIND_PATIENT, new Submodule("Find Patient",0,RegisterActivity.class));
        loadSubmodule(CoreSubmodules.REGISTRATION, new Submodule("Register Patient",0,RegistrationActivity.class));
        loadSubmodule(CoreSubmodules.FIRST_POINT_OF_CARE, new Submodule("First Point Of Care",0, FirstPointOfCareActivity.class));
        loadSubmodule(CoreSubmodules.FIRST_POINT_OF_CONTACT, new Submodule("First Point Of Contact",0,FirstPointOfContactActivity.class));
        loadSubmodule(CoreSubmodules.LOGIN, new Submodule("Login",0,LoginActivity.class));
        loadSubmodule(CoreSubmodules.VITALS, new Submodule("Vitals",0,VitalsActivity.class));
    }
}

