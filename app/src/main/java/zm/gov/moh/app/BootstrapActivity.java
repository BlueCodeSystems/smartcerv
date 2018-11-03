package zm.gov.moh.app;

import android.os.Bundle;


import zm.gov.moh.core.utils.BaseActivity;
import zm.gov.moh.core.model.submodule.Submodule;

public class BootstrapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);


        ApplicationContext applicationContext = (ApplicationContext)getApplicationContext();

        Submodule firstPointOfContactSubmodule = applicationContext.getSubmodule(ApplicationContext.CoreSubmodules.FIRST_POINT_OF_CONTACT);

        Submodule loginSubmodule = applicationContext.getSubmodule(ApplicationContext.CoreSubmodules.LOGIN);

        Bundle bundle = new Bundle();

        bundle.putSerializable(START_SUBMODULE_KEY, firstPointOfContactSubmodule);

        startSubmodule(loginSubmodule, bundle);

        finish();
    }
}