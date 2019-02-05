package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardProvidersFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardRecentsFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardReferralFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardScreeningFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardTreatmentFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitTypeFragment;
import zm.gov.moh.common.ui.BaseActivity;

public class PatientDashboardRecentsViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

    private BaseActivity mContext;

    public PatientDashboardRecentsViewPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = (BaseActivity) context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;
        if(position == 0)
            fragment = new PatientDashboardRecentsFragment();
        else
            fragment = new PatientDashboardRecentsFragment();
        /*
        if (position == 0)
            fragment = new PatientDashboardVisitTypeFragment();
        else if (position == 1)
            fragment =  new PatientDashboardScreeningFragment();
        else if (position == 2)
            fragment =  new PatientDashboardReferralFragment();
        else if (position == 3)
            fragment =  new PatientDashboardTreatmentFragment();
        else if (position == 4)
            fragment =  new PatientDashboardProvidersFragment();
        else
            fragment =  new PatientDashboardVisitTypeFragment();

        fragment.setArguments(mContext.getIntent().getExtras());
        */
        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        /*switch (position) {
            case 0:
                return "Visit Type";
            case 1:
                return "Screening";
            case 2:
                return "Referral";
            case 3:
                return "Treatment";
            case 4:
                return "Provider";
            default:
                return null;
        }*/
        switch(position) {
            case 0:
                return "Recent Visit";
            default:
                return null;
        }
    }
}
