package zm.gov.moh.app;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import zm.gov.moh.smartcerv.component.login.LoginActivity;

public class Modules extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        final Context activity = this;

        Button smartCerv =  findViewById(R.id.smartcerv);
        smartCerv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent launchSmarv = new Intent(activity, LoginActivity.class);
                activity.startActivity(launchSmarv);
            }
        });
    }

}
