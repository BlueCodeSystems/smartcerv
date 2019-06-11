package zm.gov.moh.common.view;

import androidx.appcompat.app.AppCompatActivity;
import zm.gov.moh.common.R;
import zm.gov.moh.common.ui.BaseActivity;

import android.os.Bundle;

public class CommonHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
