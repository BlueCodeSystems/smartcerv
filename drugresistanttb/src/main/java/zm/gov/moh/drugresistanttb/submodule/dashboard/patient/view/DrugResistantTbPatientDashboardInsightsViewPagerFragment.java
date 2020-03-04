package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter.MdrInsightsViewPagerFragment;

public class DrugResistantTbPatientDashboardInsightsViewPagerFragment extends Fragment {

    private BaseActivity context;

    public DrugResistantTbPatientDashboardInsightsViewPagerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (DrugResistantTbPatientDashboardActivity) getContext();
        View view = inflater.inflate(R.layout.mdr_fragment_patient_dashboard_vitals_view_pager, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewpager);

        MdrInsightsViewPagerFragment adapter = new MdrInsightsViewPagerFragment(context, context.getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
