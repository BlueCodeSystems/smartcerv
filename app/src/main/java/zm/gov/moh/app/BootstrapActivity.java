package zm.gov.moh.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import zm.gov.moh.app.submodule.first.point.of.contact.view.FirstPointOfContactActivity;
import zm.gov.moh.common.submodule.login.view.LoginActivity;
import zm.gov.moh.core.utils.SerializedClassInstance;

public class BootstrapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(SerializedClassInstance.KEY, new SerializedClassInstance(FirstPointOfContactActivity.class));
        this.startActivity(intent);
        finish();
    }
}