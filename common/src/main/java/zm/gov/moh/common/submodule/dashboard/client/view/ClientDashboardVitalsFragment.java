package zm.gov.moh.common.submodule.dashboard.client.view;


import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import zm.gov.moh.common.BR;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.FragmentClientDashboardVitalsBinding;
import zm.gov.moh.common.ui.BaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientDashboardVitalsFragment extends Fragment {


    private BaseActivity context;

    public ClientDashboardVitalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity) getContext();
        // Inflate the layout for this fragment

        long clientId = (long) getArguments().get(BaseActivity.CLIENT_ID_KEY);

        FragmentClientDashboardVitalsBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.fragment_client_dashboard_vitals, container, false);
        View view = binding.getRoot();
        Button button = view.findViewById(R.id.client_dashbord_btn);


        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getHeightByPersonId(clientId).observe(this, obs -> binding.setHeight((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getWeightByPersonId(clientId).observe(this, obs -> binding.setWeight((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getTemperatureByPersonId(clientId).observe(this, obs -> binding.setTemperature((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getPulseByPersonId(clientId).observe(this, obs -> binding.setPulse((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getRespiratoryRateByPersonId(clientId).observe(this, obs -> binding.setRespiration((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getSysbolicBloodPressureByPersonId(clientId).observe(this, obs -> binding.setBloodPressureSysbolic((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getDiabolicBloodPressureByPersonId(clientId).observe(this, obs -> binding.setBloodPressureDiabolic((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getBloodOxygenByPersonId(clientId).observe(this, obs -> binding.setBloodOxygen((obs == null )? 0 : obs.value_numeric));

        return view;
    }
}


