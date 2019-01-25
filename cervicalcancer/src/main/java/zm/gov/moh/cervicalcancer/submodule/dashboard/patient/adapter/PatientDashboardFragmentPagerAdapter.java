package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardProvidersFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardReferralFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardScreeningFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardTreatmentFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitType2Fragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitTypeFragment;
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

        Fragment fragment;
        if (position == 0)
            fragment = new PatientDashboardVisitTypeFragment();
        else if (position == 1)
            fragment = new PatientDashboardVisitType2Fragment();
        else if (position == 2)
            fragment = new PatientDashboardScreeningFragment();
        else if (position == 3)
            fragment = new PatientDashboardReferralFragment();
        else if (position == 4)
            fragment = new PatientDashboardTreatmentFragment();
        else if (position == 5)
            fragment = new PatientDashboardProvidersFragment();
        else if (position == 6) {
            fragment = new PatientDashboardVisitFragment();
        }
        else if (position == 7)
            fragment = new ClientDashboardVitalsFragment();
         else
            return new PatientDashboardVisitTypeFragment();

        fragment.setArguments(mContext.getIntent().getExtras());
        return fragment;
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 8;
    }

    // This determines the title for each tab
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
            case 6:
                return "Visit";
            case 7:
                return "Vitals";
            default:
                return null;
        }
    }

}