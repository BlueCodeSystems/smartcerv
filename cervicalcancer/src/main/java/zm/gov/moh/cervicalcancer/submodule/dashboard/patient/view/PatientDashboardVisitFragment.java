package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.os.Bundle;

import androidx.core.util.Consumer;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import zm.gov.moh.cervicalcancer.OpenmrsConfig;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.databinding.FragmentPatientDashboardVisitBinding;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter.VisitExpandableListAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardVisitFragment extends Fragment {


    PatientDashboardActivity mContext;
    PatientDashboardViewModel viewModel;
    FragmentPatientDashboardVisitBinding binding;
    public PatientDashboardVisitFragment() {
        // Required empty public constructor
    }

    ExpandableListView visitItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = (PatientDashboardActivity) getContext();
        viewModel = mContext.getViewModel();

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.fragment_patient_dashboard_visit, container, false);
        View rootView = binding.getRoot();

        visitItemList = rootView.findViewById(R.id.visit_list);
        binding.setContext(mContext);

        viewModel.getVisitDataEmitter().observe(mContext,linkedHashMultimaps -> {

            VisitExpandableListAdapter adapter = new VisitExpandableListAdapter(mContext,linkedHashMultimaps);

            viewModel.getFilterObsEmitter().observe(this, populateList(visitItemList ,adapter)::accept);
        });

        return rootView;
    }

    public Consumer<Map.Entry<List<Long>, Map<Long,Long>>> populateList(ExpandableListView visitItemList, VisitExpandableListAdapter visitExpandableListAdapter){

       return (Map.Entry<List<Long>, Map<Long,Long>> filter) -> {

           visitExpandableListAdapter.addFilterConceptId(filter.getKey());
           visitExpandableListAdapter.setSubstituteConcept(filter.getValue());
           binding.setNumberOfVisits(visitExpandableListAdapter.getGroupCount());
           visitItemList.setAdapter(visitExpandableListAdapter);
       };
    }

}
