package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view.DrugResistantTbMonitoringFormFragment;

public class MdrInsightsViewPagerFragment extends FragmentStatePagerAdapter {
    private BaseActivity mContext;

    public MdrInsightsViewPagerFragment(Context context, FragmentManager fm) {
        super(fm);
        mContext = (BaseActivity) context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0)
            fragment = new DrugResistantTbMonitoringFormFragment();
        else
            fragment = new DrugResistantTbMonitoringFormFragment();

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
                return "Monitoring Form";
            default:
                return null;
        }
    }
}
