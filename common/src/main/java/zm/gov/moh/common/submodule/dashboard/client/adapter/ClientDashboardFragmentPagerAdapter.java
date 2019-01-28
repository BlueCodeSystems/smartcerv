package zm.gov.moh.common.submodule.dashboard.client.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardCareServicesFragment;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardVitalsFragment;
import zm.gov.moh.common.ui.BaseActivity;

public class ClientDashboardFragmentPagerAdapter extends FragmentPagerAdapter {

    private BaseActivity mContext;

    public ClientDashboardFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = (BaseActivity) context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {

        Fragment fragment;
        if (position == 0) {

            fragment = new ClientDashboardVitalsFragment();
        }
        else if (position == 1)
            fragment = new ClientDashboardCareServicesFragment();
         else
            fragment = new ClientDashboardVitalsFragment();

        fragment.setArguments(mContext.getIntent().getExtras());

        return fragment;
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {

        // Generate title based on item position
        switch (position) {
            case 0:
                return "Vitals";
            case 1:
                return "Care Services";
            default:
                return null;
        }
    }

}