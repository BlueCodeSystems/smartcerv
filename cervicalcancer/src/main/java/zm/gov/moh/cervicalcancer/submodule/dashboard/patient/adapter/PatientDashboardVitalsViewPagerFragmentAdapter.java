package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitTypeFragment;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardVitalsFragment;
import zm.gov.moh.common.ui.BaseActivity;

public class PatientDashboardVitalsViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

    private BaseActivity mContext;

    public PatientDashboardVitalsViewPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = (BaseActivity) context;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Fragment fragment = new ClientDashboardVitalsFragment();
            fragment.setArguments(mContext.getIntent().getExtras());
            return fragment;
        }else
            return new PatientDashboardVisitTypeFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Vitals";

            default:
                return null;
        }
    }
}
