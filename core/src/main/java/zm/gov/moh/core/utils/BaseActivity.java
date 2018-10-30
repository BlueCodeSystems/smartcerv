package zm.gov.moh.core.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


    protected final String START_SUBMODULE_KEY = "start_submodule";
    protected final String CALLING_SUBMODULE_KEY = "calling_submodule";

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
}
