package zm.gov.moh.common.submodule.dashboard.client.view;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import zm.gov.moh.common.BR;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.FragmentClientDashboardVitalsBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientDashboardVitalsFragment extends Fragment {


    private ClientDashboardActivity context;

    public ClientDashboardVitalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (ClientDashboardActivity) getContext();
        context.getClientId();
        // Inflate the layout for this fragment

        FragmentClientDashboardVitalsBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.fragment_client_dashboard_vitals, container, false);
        View view = binding.getRoot();
        Button button = view.findViewById(R.id.client_dashbord_btn);


        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getHeightByPersonId(context.getClientId()).observe(this, obs -> binding.setHeight((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getWeightByPersonId(context.getClientId()).observe(this, obs -> binding.setWeight((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getTemperatureByPersonId(context.getClientId()).observe(this, obs -> binding.setTemperature((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getPulseByPersonId(context.getClientId()).observe(this, obs -> binding.setPulse((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getRespiratoryRateByPersonId(context.getClientId()).observe(this, obs -> binding.setRespiration((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getSysbolicBloodPressureByPersonId(context.getClientId()).observe(this, obs -> binding.setBloodPressureSysbolic((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getDiabolicBloodPressureByPersonId(context.getClientId()).observe(this, obs -> binding.setBloodPressureDiabolic((obs == null )? 0 : obs.value_numeric));

        context.getViewModel().getRepository().getDatabase().vitalsDao()
                .getBloodOxygenByPersonId(context.getClientId()).observe(this, obs -> binding.setBloodOxygen((obs == null )? 0 : obs.value_numeric));

        return view;
    }
}


