package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardVitalsFragment;
import zm.gov.moh.common.base.BaseActivity;

/*public class RecentsViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

    private BaseActivity mContext;

    public RecentsViewPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = (BaseActivity) context;
    }*/


    /*@Override
    public Fragment getItem(int position) {

        Fragment fragment;

        if (position == 0)
             fragment = new PatientDashboardVisitFragment();
        else if (position == 1)
                fragment = new ClientDashboardVitalsFragment();
        else
            fragment = new PatientDashboardVisitTypeFragment();

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
                return "Vitals";

            default:
                return null;
        }
    }
}
*/