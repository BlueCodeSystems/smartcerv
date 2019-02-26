package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter.RecentsViewPagerFragmentAdapter;
import zm.gov.moh.common.ui.BaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardRecentsViewPagerFragment extends Fragment {

    private BaseActivity context;

    public PatientDashboardRecentsViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = (PatientDashboardActivity) getContext();
        View view = inflater.inflate(R.layout.fragment_patient_dashboard_vitals_view_pager,
                container, false);

        // Find the view pager that will allow the getUsers to swipe between fragments
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        RecentsViewPagerFragmentAdapter adapter =
                new RecentsViewPagerFragmentAdapter(context, context.getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Inflate the layout for this fragment
        return view;
    }

}
