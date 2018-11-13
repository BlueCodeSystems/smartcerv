package zm.gov.moh.core.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import zm.gov.moh.core.model.submodule.Submodule;

public class BaseActivity extends AppCompatActivity {


    protected static final String START_SUBMODULE_KEY = "start_submodule";
    protected static final String CALLING_SUBMODULE_KEY = "calling_submodule";
    protected static final String FORM_FRAGMENT_KEY = "FORM_FRAGMENT_KEY";
    protected static final String CLIENT_ID_KEY = "CLIENT_ID_KEY";
    protected static final String JSON_FORM_KEY = "JSON_FORM_KEY";
    public static final String ACTION_KEY = "ACTION_KEY";
    public static final String FORM_DATA_KEY = "FORM_DATA_KEY";
    public static final String START_SUBMODULE_ON_FORM_RESULT_KEY = "START_SUBMODULE_ON_FORM_RESULT_KEY";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void startSubmodule(Submodule submodule, Bundle bundle){

        Intent intent = new Intent(this, submodule.getClassInstance());

        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    public void startSubmodule(Submodule submodule){

        this.startActivity( new Intent(this,submodule.getClassInstance()));
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }
}
