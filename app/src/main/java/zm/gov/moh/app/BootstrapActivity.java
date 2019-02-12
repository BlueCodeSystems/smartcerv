package zm.gov.moh.app;

import android.content.Intent;
import android.os.Bundle;


import com.jakewharton.threetenabp.AndroidThreeTen;


import zm.gov.moh.core.service.MetaDataSync;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.utils.BaseApplication;

public class BootstrapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);
        AndroidThreeTen.init(this);

        ApplicationContext applicationContext = (ApplicationContext)getApplicationContext();

        Submodule firstPointOfContactSubmodule = applicationContext.getSubmodule(BaseApplication.CoreModule.HOME);

        Submodule loginSubmodule = applicationContext.getSubmodule(BaseApplication.CoreModule.LOGIN);

        Submodule formSubmodule = applicationContext.getSubmodule(BaseApplication.CoreModule.FORM);

        Bundle bundle = new Bundle();

        bundle.putSerializable(START_SUBMODULE_KEY, firstPointOfContactSubmodule);

        startSubmodule(loginSubmodule, bundle);

        finish();

       Intent intent = new Intent(this, MetaDataSync.class);

       startService(intent);
    }
}