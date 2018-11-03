package zm.gov.moh.cervicalcancer.submodule.enrollment.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.enrollment.viewmodel.CervicalCancerEnrollmentViewModel;
import zm.gov.moh.common.submodule.dashboard.client.viewmodel.ClientDashboardViewModel;
import zm.gov.moh.core.utils.BaseActivity;

public class CervicalCancerEnrollmentActivity extends BaseActivity {

    private CervicalCancerEnrollmentViewModel mCervicalCancerEnrollmentViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cervical_cancer_enrollment);

        mCervicalCancerEnrollmentViewModel = ViewModelProviders.of(this).get(CervicalCancerEnrollmentViewModel.class);

        //mCervicalCancerEnrollmentViewModel.getRepository().getClientById(34).observe(this, );
    }
}
