package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.Map;

import androidx.core.util.Consumer;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.databinding.MdrFragmentPatientDashboardVisitBinding;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter.VisitExpandableListAdapter;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.viewmodel.DrugResistantTbPatientDashboardViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrugResistantTbPatientDashboardVisitFragment extends Fragment {


    DrugResistantTbPatientDashboardActivity mContext;
    DrugResistantTbPatientDashboardViewModel viewModel;
    MdrFragmentPatientDashboardVisitBinding binding;

    public DrugResistantTbPatientDashboardVisitFragment() {
        // Required empty public constructor
    }


    ExpandableListView visitItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = (DrugResistantTbPatientDashboardActivity) getContext();
        viewModel = mContext.getViewModel();

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.mdr_fragment_patient_dashboard_visit, container, false);
        View rootView = binding.getRoot();

        visitItemList = rootView.findViewById(R.id.mdr_visit_list);
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
