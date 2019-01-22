package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardProvidersFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardReferralFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardScreeningFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardTreatmentFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitType2Fragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitTypeFragment;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardVitalsFragment;
import zm.gov.moh.common.ui.BaseActivity;

public class PatientDashboardRegisterViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

    private BaseActivity mContext;

    public PatientDashboardRegisterViewPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = (BaseActivity) context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new PatientDashboardVisitTypeFragment();
        else if (position == 1)
            return new PatientDashboardVisitType2Fragment();
        else if (position == 2)
            return new PatientDashboardScreeningFragment();
        else if (position == 3)
            return new PatientDashboardReferralFragment();
        else if (position == 4)
            return new PatientDashboardTreatmentFragment();
        else if (position == 5)
            return new PatientDashboardProvidersFragment();
        else
            return new PatientDashboardVisitTypeFragment();
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Visit Type 1|2";
            case 1:
                return "Visit Type 2|2";
            case 2:
                return "Screening";
            case 3:
                return "Referral";
            case 4:
                return "Treatment";
            case 5:
                return "Provider";
            default:
                return null;
        }
    }
}
