package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter.MdrRecentsViewPagerFragmentAdapter;
import zm.gov.moh.common.base.BaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrugResistantTbPatientDashboardRecentsViewPagerFragment extends Fragment {

    private BaseActivity context;

    public DrugResistantTbPatientDashboardRecentsViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = (DrugResistantTbPatientDashboardActivity) getContext();
        View view = inflater.inflate(R.layout.mdr_fragment_patient_dashboard_vitals_view_pager,
                container, false);

        // Find the view pager that will allow the getUsers to swipe between fragments
        ViewPager viewPager = view.findViewById(R.id.viewpager);

        MdrRecentsViewPagerFragmentAdapter adapter =
                new MdrRecentsViewPagerFragmentAdapter(context, context.getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Inflate the layout for this fragment
        return view;
    }

}
