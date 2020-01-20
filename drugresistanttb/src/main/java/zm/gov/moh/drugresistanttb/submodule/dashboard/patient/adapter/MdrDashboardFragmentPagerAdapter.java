package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.drugresistanttb.view.MyDrugResistantTbDialogFragment;

public class MdrDashboardFragmentPagerAdapter extends FragmentPagerAdapter {

    private BaseActivity mContext;

    public MdrDashboardFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = (BaseActivity) context;
    }



    // This will only determine the fragment for one tab, capisce?
    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        fragment = new MyDrugResistantTbDialogFragment();

        fragment.setArguments(mContext.getIntent().getExtras());

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    // This determines the title for the tab
    @Override
    public CharSequence getPageTitle(int position) {

        // Generate title
        switch (position) {
            case 0:
                return "Notification Card";
            case 1:
                return "Request For Bacteriological Examination For TB";
            case 2:
                return "Baseline and Follow Up Assessment Form";
            default:
                return "";
        }
    }
}




