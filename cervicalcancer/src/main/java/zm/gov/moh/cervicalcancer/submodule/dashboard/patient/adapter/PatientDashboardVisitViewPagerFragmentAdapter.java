package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.PatientDashboardVisitSessionFragment;
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
            fragment = new PatientDashboardVisitSessionFragment();
        else
            fragment =  new PatientDashboardVisitSessionFragment();

        fragment.setArguments(mContext.getIntent().getExtras());

        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Visit";
            default:
                return null;
        }
    }
}
