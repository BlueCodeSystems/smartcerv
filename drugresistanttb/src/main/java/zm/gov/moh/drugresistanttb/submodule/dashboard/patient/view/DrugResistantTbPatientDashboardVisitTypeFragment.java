package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import zm.gov.moh.drugresistanttb.OpenmrsConfig;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;

import static zm.gov.moh.drugresistanttb.submodule.dashboard.patient.utils.Utils.dateCellView;
import static zm.gov.moh.drugresistanttb.submodule.dashboard.patient.utils.Utils.renderCheckMarkIconView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrugResistantTbPatientDashboardVisitTypeFragment extends Fragment {


    private BaseActivity context;
    TableLayout tableLayout;



    public DrugResistantTbPatientDashboardVisitTypeFragment() {
        // Required empty public constructor



    }

    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity)getContext();
        View view = inflater.inflate(R.layout.mdr_fragment_register_visit_type, container, false);
        context = (DrugResistantTbPatientDashboardActivity) getContext();
        //context.getClientId();
        // Inflate the layout for this fragment

        //FragmentClientDashboardVitalsBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.fragment_client_dashboard_vitals, container, false);
       //View view = binding.getRoot();
        bundle = getArguments();
        long patientId = bundle.getLong(Key.PERSON_ID);

        tableLayout = view.findViewById(R.id.visit_type_table);


        context.getViewModel()
                .getRepository()
                .getDatabase()
                .visitDao()
                .getByPatientIdVisitTypeId(patientId,2L,3L,4L,5L,6L,7L)
                .observe(context,this::recordCompletedVisits);

        return view;
    }

    public void tabulateVisitRow(String date, Map<Long,Boolean> visitCompleted){

        TableRow tableRow = new TableRow(context);
        tableRow.setBackground(context.getResources().getDrawable(R.drawable.border_bottom));

        tableRow.addView(dateCellView(context,date));
        tableRow.addView(renderCheckMarkIconView(context, visitCompleted.get(OpenmrsConfig.VISIT_TYPE_ID_MDR_BACTERIAL_EXAM)));
        tableRow.addView(renderCheckMarkIconView(context,  visitCompleted.get(OpenmrsConfig.VISIT_TYPE_ID_MDR_MONTHLY_REVIEW_FORM)));
        tableRow.addView(renderCheckMarkIconView(context, visitCompleted.get(OpenmrsConfig.VISIT_TYPE_ID_MDR_BASELINE_FOLLOW_UP_ASSESSMENT)));

        tableLayout.addView(tableRow);
    }

    public void recordCompletedVisits(List<VisitEntity> visitList){

        HashMap<Long, Boolean> visitCompleted;
        visitCompleted = new HashMap<>();

        Iterator<VisitEntity> visitIterator = visitList.iterator();

            while (visitIterator.hasNext()) {

                visitCompleted.put(OpenmrsConfig.VISIT_TYPE_ID_MDR_BACTERIAL_EXAM, false);
                visitCompleted.put(OpenmrsConfig.VISIT_TYPE_ID_MDR_MONTHLY_REVIEW_FORM, false);
                visitCompleted.put(OpenmrsConfig.VISIT_TYPE_ID_MDR_BASELINE_FOLLOW_UP_ASSESSMENT, false);



                VisitEntity visit = visitIterator.next();
                String date = visit.getDateStarted().format(DateTimeFormatter.ISO_LOCAL_DATE);
                long visitTypeId = visit.getVisitTypeId();

                visitCompleted.put(visitTypeId,true);

                tabulateVisitRow(date,visitCompleted);
            }
    }


}


