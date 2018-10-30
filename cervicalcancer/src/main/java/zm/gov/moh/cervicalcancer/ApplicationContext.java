package zm.gov.moh.cervicalcancer;

import zm.gov.moh.cervicalcancer.submodule.cervicalcancer.view.CervicalCancerActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Submodule;

public class ApplicationContext extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        //loadFirstPointOfCareSubmodule(new Submodule("Cervical Cancer",0, CervicalCancerActivity.class));
        loadSubmodule("Cervical", new Submodule("Cervical Cancer",0, CervicalCancerActivity.class));
    }
}
