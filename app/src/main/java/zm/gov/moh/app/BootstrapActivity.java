package zm.gov.moh.app;

import android.os.Bundle;


import zm.gov.moh.core.utils.BaseActivity;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

public class BootstrapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);


        ApplicationContext applicationContext = (ApplicationContext)getApplicationContext();

        Submodule firstPointOfContactSubmodule = applicationContext.getSubmodule(ApplicationContext.CoreSubmodules.FIRST_POINT_OF_CONTACT);

        Submodule loginSubmodule = applicationContext.getSubmodule(ApplicationContext.CoreSubmodules.LOGIN);

        Submodule formSubmodule = applicationContext.getSubmodule(ApplicationContext.CoreSubmodules.FORM);

        Bundle bundle = new Bundle();

        bundle.putSerializable(START_SUBMODULE_KEY, firstPointOfContactSubmodule);

        //startSubmodule(loginSubmodule, bundle);


        try{
            String json = Utils.getStringFromInputStream(this.getAssets().open("forms/cervical_cancer_enrollment.json"));
           bundle.putString(BaseActivity.JSON_FORM_KEY,json);
        }catch (Exception ex){

        }

        startSubmodule(formSubmodule, bundle);

        finish();
    }
}