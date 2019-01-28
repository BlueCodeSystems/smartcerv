package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitHistoryFragment;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitTypeFragment;
import zm.gov.moh.common.ui.BaseActivity;

public class PatientDashboardVisitViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

    private BaseActivity mContext;

    public PatientDashboardVisitViewPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = (BaseActivity) context;
    }

    @Override
    public Fragment getItem(int position) {
        // We want the visit fragment to have two tabs;

        Fragment fragment;
        if(position == 0)
            fragment = new PatientDashboardVisitFragment();
        else if(position == 1) {
            fragment = new PatientDashboardVisitHistoryFragment();
        }
        else
            fragment =  new PatientDashboardVisitFragment();

        fragment.setArguments(mContext.getIntent().getExtras());

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Visit";
            case 1:
                return "History Visit";
            default:
                return null;
        }
    }
}
