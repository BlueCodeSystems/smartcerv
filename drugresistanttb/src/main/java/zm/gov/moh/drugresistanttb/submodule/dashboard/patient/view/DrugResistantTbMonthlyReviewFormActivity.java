package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;

import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.model.VisitMetadata;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.drugresistanttb.R;

import android.os.Bundle;

import java.io.IOException;

public class DrugResistantTbMonthlyReviewFormActivity extends BaseActivity {

    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getIntent().getExtras();

        VisitMetadata visitMetadata = null;
        try {
            visitMetadata = new VisitMetadata(this, Utils.getStringFromInputStream(this.getAssets().open("visits/mdr.json")));
            mBundle.putSerializable(Key.VISIT_METADATA, visitMetadata);
            mBundle.putSerializable(Key.VISIT_STATE, zm.gov.moh.core.model.VisitState.NEW);
            mBundle.putSerializable(Key.THEME_STYLE, R.style.MDR);
            this.startModule(BaseApplication.CoreModule.VISIT, mBundle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

