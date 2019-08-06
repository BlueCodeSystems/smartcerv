package zm.gov.moh.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import com.jakewharton.threetenabp.AndroidThreeTen;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.service.ServiceManager;
import zm.gov.moh.core.utils.BaseApplication;

public class BootstrapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);
        AndroidThreeTen.init(this);

        ApplicationContext applicationContext = (ApplicationContext)getApplicationContext();

        Module firstPointOfContactModule = applicationContext.getModule(BaseApplication.CoreModule.HOME);

        Bundle bundle = new Bundle();

        bundle.putSerializable(START_SUBMODULE_KEY, firstPointOfContactModule);

        startModule(BaseApplication.CoreModule.LOGIN, bundle);

       ServiceManager.getInstance(this)
                .setService(ServiceManager.Service.PULL_PATIENT_ID_REMOTE)
                .startOnComplete(ServiceManager.Service.PULL_PATIENT_ID_REMOTE, ServiceManager.Service.PULL_META_DATA_REMOTE)
                .start();

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE},1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CHANGE_NETWORK_STATE},1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CHANGE_NETWORK_STATE},1);


        finish();
    }
}