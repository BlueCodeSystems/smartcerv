package zm.gov.moh.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.io.Serializable;

import zm.gov.moh.common.submodule.login.view.LoginActivity;
import zm.gov.moh.core.utils.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.SerializedClassInstance;
import zm.gov.moh.core.utils.Submodule;

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