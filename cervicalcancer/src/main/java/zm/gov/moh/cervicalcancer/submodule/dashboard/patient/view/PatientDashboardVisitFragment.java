package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.os.Bundle;

import androidx.arch.core.util.Function;
import androidx.core.util.Consumer;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.common.collect.LinkedHashMultimap;

import java.util.LinkedList;
import java.util.List;

import zm.gov.moh.cervicalcancer.OpenmrsConfig;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.databinding.FragmentPatientDashboardVisitBinding;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter.VisitExpandableListAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.VisitEncounterItem;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.VisitListItem;
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

    LinkedList<String> filterConceptIdUuid;
    ExpandableListView visitItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = (PatientDashboardActivity) getContext();
        viewModel = mContext.getViewModel();


        //Filter concept ids
        filterConceptIdUuid = new LinkedList<>();
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_VIA_INSPECTION_DONE);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_VIA_SCREENING_RESULT);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_REFERRAL_REASON);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_HEALTH_FACILITY_REFERRED);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_VIA_TREATMENT_PERFORMED);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_SCREENING_PROVIDER);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_TREATMENT_PROVIDER);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_HIV_STATUS);

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.fragment_patient_dashboard_visit, container, false);
        View rootView = binding.getRoot();

        visitItemList = rootView.findViewById(R.id.visit_list);
        Fragment visitSessionPage = new PatientDashboardVisitSessionFragment();
        visitSessionPage.setArguments(getArguments());
        binding.setVisitSessionPage(visitSessionPage);
        binding.setContext(mContext);

        viewModel.getVisitDataEmitter().observe(mContext,linkedHashMultimaps -> {

            VisitExpandableListAdapter adapter = new VisitExpandableListAdapter(mContext,linkedHashMultimaps);


            viewModel.getRepository().getDatabase().conceptDao()
                    .getConceptIdByUuid(filterConceptIdUuid)
                    .observe(this,populateList(visitItemList ,adapter)::accept);
        });





        return rootView;
    }

    public Consumer<List<Long>> populateList(ExpandableListView visitItemList, VisitExpandableListAdapter visitExpandableListAdapter){

       return (filterConceptId) -> {

           visitExpandableListAdapter.addFilterConceptId(filterConceptId);
           binding.setNumberOfVisits(visitExpandableListAdapter.getGroupCount());
           visitItemList.setAdapter(visitExpandableListAdapter);
       };
    }

}
