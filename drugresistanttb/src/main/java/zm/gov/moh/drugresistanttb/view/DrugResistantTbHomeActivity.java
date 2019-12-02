package zm.gov.moh.drugresistanttb.view;

import androidx.fragment.app.FragmentManager;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.drugresistanttb.R;

import android.os.Bundle;

public class DrugResistantTbHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_resistant_tb_home);

        DrugResistantTbHomeFragment mdrHomeFragment = new DrugResistantTbHomeFragment();
        mdrHomeFragment.setArguments(getIntent().getExtras());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.mdrFrameLayout, mdrHomeFragment).commit();

    }
}
