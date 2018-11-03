package zm.gov.moh.cervicalcancer;

import java.util.ArrayList;
import java.util.List;

import zm.gov.moh.cervicalcancer.submodule.cervicalcancer.view.CervicalCancerActivity;
import zm.gov.moh.cervicalcancer.submodule.enrollment.view.CervicalCancerEnrollmentActivity;
import zm.gov.moh.common.submodule.registration.view.RegistrationActivity;
import zm.gov.moh.core.model.submodule.BasicSubmodule;
import zm.gov.moh.core.model.submodule.BasicSubmoduleGroup;
import zm.gov.moh.core.model.submodule.SubmoduleGroup;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Submodule;

public class ApplicationContext extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //loadFirstPointOfCareSubmodule(new Submodule("Cervical Cancer",0, CervicalCancerActivity.class));

    }
}
