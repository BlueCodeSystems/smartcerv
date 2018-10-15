package zm.gov.moh.smartcerv.component.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zm.gov.moh.smartcerv.R;
import zm.gov.moh.smartcerv.component.login.view.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        final Context activity = this;



        //Toolbar toolbar = findViewById(R.id.smartcerv_toolbar);
        //setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow();
        }



    }
}
