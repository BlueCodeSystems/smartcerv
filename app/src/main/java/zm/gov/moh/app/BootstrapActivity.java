package zm.gov.moh.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.jakewharton.threetenabp.AndroidThreeTen;


import zm.gov.moh.core.service.MetaDataSync;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.utils.BaseApplication;

public class BootstrapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);
        AndroidThreeTen.init(this);

        ApplicationContext applicationContext = (ApplicationContext)getApplicationContext();

        Module firstPointOfContactModule = applicationContext.getModule(BaseApplication.CoreModule.HOME);

        Module loginModule = applicationContext.getModule(BaseApplication.CoreModule.LOGIN);

        Module formModule = applicationContext.getModule(BaseApplication.CoreModule.FORM);

        Bundle bundle = new Bundle();

        bundle.putSerializable(START_SUBMODULE_KEY, firstPointOfContactModule);

        startModule(loginModule, bundle);

        finish();

       Intent intent = new Intent(this, MetaDataSync.class);

       startService(intent);
    }

    @Override
    public void init() {

    }

    @Override
    public void onClick(View view) {

    }
}