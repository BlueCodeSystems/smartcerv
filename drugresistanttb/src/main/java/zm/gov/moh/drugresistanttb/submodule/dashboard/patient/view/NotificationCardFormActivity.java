package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;

import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.model.VisitMetadata;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

import android.os.Bundle;

import java.io.IOException;

public class NotificationCardFormActivity extends BaseActivity {

    Bundle mbundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbundle = getIntent().getExtras();

        VisitMetadata visitMetadata = null;
        try {
            visitMetadata = new VisitMetadata(this, Utils.getStringFromInputStream(this.getAssets().open("visits/mdr.json")));
            mbundle.putSerializable(Key.VISIT_METADATA, visitMetadata);
            mbundle.putSerializable(Key.VISIT_STATE, zm.gov.moh.core.model.VisitState.NEW);
            this.startModule(BaseApplication.CoreModule.VISIT, mbundle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

