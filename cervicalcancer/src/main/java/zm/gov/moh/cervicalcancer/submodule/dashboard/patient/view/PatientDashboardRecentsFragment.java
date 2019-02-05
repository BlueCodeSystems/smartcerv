package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter.RecentVisitExpandableListAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter.TestListDataEntry;
import zm.gov.moh.common.ui.BaseActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardRecentsFragment extends Fragment {

    private BaseActivity context;
    View rootView; // base view - the view ExpandableListView sits on

    private ExpandableListView mFormGroupExpandableListView;
    List<String> formListTitle;
    RecentVisitExpandableListAdapter adapter;
    HashMap<String, List<String>> patientVistDetailForm;

    public PatientDashboardRecentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = (BaseActivity) getContext();
        rootView = inflater.inflate(R.layout.fragment_patient_dashboard_recents, container, false);

        // screening form title ie. HIV status, Reproductive Health History, etc
        formListTitle = new ArrayList<>();
        // object to display data values collected on a form
        patientVistDetailForm = new HashMap<>();

        mFormGroupExpandableListView = (ExpandableListView) rootView.findViewById(R.id.recent_visits);
        patientVistDetailForm = TestListDataEntry.addDummyData();
        formListTitle = new ArrayList<>(patientVistDetailForm.keySet());
        adapter = new RecentVisitExpandableListAdapter(context, formListTitle, patientVistDetailForm);

        mFormGroupExpandableListView.setAdapter(adapter);

        // implement listeners here - after setting adapter.
        mFormGroupExpandableListView.setOnGroupExpandListener(
                new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {
                        Toast.makeText(context, formListTitle.
                                get(groupPosition) + " List Expanded.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        mFormGroupExpandableListView.setOnChildClickListener(
                new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Toast.makeText(context, formListTitle.
                                        get(groupPosition) + " -> " + patientVistDetailForm.get(
                                formListTitle.get(groupPosition)).get(childPosition),
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
        );

        return rootView;
    }

}
