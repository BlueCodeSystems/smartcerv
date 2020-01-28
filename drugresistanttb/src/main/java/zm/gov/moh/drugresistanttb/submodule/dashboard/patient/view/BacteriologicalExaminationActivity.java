package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import java.io.IOException;

import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.common.model.VisitMetadata;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.VisitState;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.model.submodule.BasicModule;
import zm.gov.moh.drugresistanttb.R;

public class BacteriologicalExaminationActivity extends BaseActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_bacteriological_exam);
        BasicModule basicModule = new BasicModule("Bacteriological Exam",
                BacteriologicalExaminationActivity.class);
        try {
            JsonForm bacterialExam = new JsonForm("Bacteriological Exam",
                    Utils.getStringFromInputStream(this.getAssets().open("visits/mdr.json.json")));
            bundle.putSerializable(BaseActivity.JSON_FORM, bacterialExam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        startVisit(basicModule, bundle);
    }
     private void startVisit(BasicModule basicModule, Bundle bundle) {
        VisitMetadata visitMetadata = null;
        try {
            visitMetadata = new VisitMetadata(this,
                    Utils.getStringFromInputStream(this.getAssets().open("visits/mdr.json")));
            bundle.putSerializable(Key.VISIT_METADATA, visitMetadata);
            bundle.putSerializable(Key.VISIT_METADATA, VisitState.NEW);
            this.startModule(BaseApplication.CoreModule.VISIT, bundle);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}





