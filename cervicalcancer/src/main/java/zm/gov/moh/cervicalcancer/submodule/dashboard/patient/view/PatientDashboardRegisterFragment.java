package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zm.gov.moh.cervicalcancer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardRegisterFragment extends Fragment {


    public PatientDashboardRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_dashboard_register, container, false);
    }

}
