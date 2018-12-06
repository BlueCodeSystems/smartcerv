package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardActivity;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardScreeningFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitTypeFragment;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardCareServicesFragment;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardVitalsFragment;
import zm.gov.moh.common.ui.BaseActivity;

public class PatientDashboardFragmentPagerAdapter extends FragmentPagerAdapter {

    private BaseActivity mContext;

    public PatientDashboardFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = (BaseActivity) context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new PatientDashboardVisitTypeFragment();
        }
        else if (position == 1)
            return new PatientDashboardScreeningFragment();
        else if (position == 2)
            return new PatientDashboardScreeningFragment();
        else if (position == 3)
            return new PatientDashboardScreeningFragment();
        else if (position == 4) {
            Fragment fragment = new ClientDashboardVitalsFragment();
            fragment.setArguments(mContext.getIntent().getExtras());
            return fragment;
        }
         else
            return new PatientDashboardVisitTypeFragment();

    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 5;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {

        // Generate title based on item position
        switch (position) {
            case 0:
                return "Visit Type";
            case 1:
                return "Screening";
            case 2:
                return "Referral";
            case 3:
                return "Treatment";
            case 4:
                return "Vitals";
            default:
                return null;
        }
    }

}