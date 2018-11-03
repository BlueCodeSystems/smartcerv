package zm.gov.moh.common.submodule.dashboard.client.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import zm.gov.moh.common.R;

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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_client_dashboard_vitals, container, false);

        Button button = view.findViewById(R.id.client_dashbord_btn);
        button.setOnClickListener(viewb -> {

            Bundle bundle = new Bundle();
            bundle.putLong(ClientDashboardActivity.CLIENT_ID_KEY,context.getClientId());
            context.startSubmodule(context.getVitals(),bundle);
        });
        return view;
    }
}
