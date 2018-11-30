package zm.gov.moh.common.submodule.dashboard.client.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.dashboard.client.adapter.CareServicesExpandableListAdapter;
import zm.gov.moh.core.model.submodule.SubmoduleGroup;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Submodule;

public class ClientDashboardCareServicesFragment extends Fragment {

    private BaseActivity context;

    public ClientDashboardCareServicesFragment() {
        // Required empty public constructor
    }

    public static ClientDashboardCareServicesFragment newInstance() {
        ClientDashboardCareServicesFragment fragment = new ClientDashboardCareServicesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.context = (BaseActivity) this.getContext();
        View view = inflater.inflate(R.layout.fragment_client_dashboard_care_services, container, false);

        CareServicesExpandableListAdapter careServicesExpandableListAdapter = new CareServicesExpandableListAdapter(context, getCareServices());

        ExpandableListView clientServiceList = view.findViewById(R.id.care_service_list);

        clientServiceList.setAdapter(careServicesExpandableListAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void init(){

    }

    public List<SubmoduleGroup> getCareServices(){

        Submodule registeration = ((BaseApplication)context.getApplication()).getSubmodule(BaseApplication.CoreSubmodules.REGISTRATION);
        List<Submodule> submodules = new ArrayList<>();
        submodules.add(registeration);

        SubmoduleGroup submoduleGroup1 = (SubmoduleGroup) ((BaseApplication)context.getApplication()).getSubmodule(BaseApplication.CareSubmodules.CERVICAL_CANCER);


        List<SubmoduleGroup> submoduleGroups = new ArrayList<>();

        submoduleGroups.add(submoduleGroup1);

        return submoduleGroups;
    }
}
