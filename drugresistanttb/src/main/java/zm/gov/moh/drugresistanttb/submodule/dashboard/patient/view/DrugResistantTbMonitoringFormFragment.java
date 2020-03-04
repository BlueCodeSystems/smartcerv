package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.drugresistanttb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrugResistantTbMonitoringFormFragment extends Fragment {

    private BaseActivity context;
    TableLayout tableLayout;
    Bundle bundle;

    public DrugResistantTbMonitoringFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = (DrugResistantTbPatientDashboardActivity)getContext();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drug_resistant_tb_monitoring_form, container, false);
        bundle = getArguments();
        tableLayout = view.findViewById(R.id.mdr_visit_table);

        long patientId = bundle.getLong(Key.PERSON_ID);
        context.getViewModel().getRepository()
                .getDatabase()
                .visitDao()
                .getByPatientIdVisitTypeId(patientId, 11L, 12L)
                .observe(context, this::gleanCompleteVisits);
        return view;
    }

    private void gleanCompleteVisits(List<VisitEntity> visitList){

        HashMap<Long, String> visitCompleted = new HashMap<>(); // Map <VistTypeId, VisitId>
        Iterator<VisitEntity> visitIterator = visitList.iterator();

        while (visitIterator.hasNext()) {
            VisitEntity currentVisit = visitIterator.next();
            if (currentVisit.getVisitTypeId() == 11L)
                visitCompleted.put(11L,currentVisit.getUuid());
        }
    }
}
