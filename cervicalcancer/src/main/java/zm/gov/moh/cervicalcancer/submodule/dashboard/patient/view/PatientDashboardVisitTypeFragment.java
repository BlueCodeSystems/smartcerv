package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.common.databinding.FragmentClientDashboardVitalsBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardVisitTypeFragment extends Fragment {


    private PatientDashboardActivity context;

    public PatientDashboardVisitTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_visit_type, container, false);
        //context = (PatientDashboardActivity) getContext();
        //context.getClientId();
        // Inflate the layout for this fragment

        //FragmentClientDashboardVitalsBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.fragment_client_dashboard_vitals, container, false);
       // View view = binding.getRoot();


        return view;
    }
}


