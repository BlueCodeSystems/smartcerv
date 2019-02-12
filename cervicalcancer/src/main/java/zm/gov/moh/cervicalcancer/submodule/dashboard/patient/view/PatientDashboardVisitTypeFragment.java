package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
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

import zm.gov.moh.cervicalcancer.ModuleConfig;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.Visit;

import static zm.gov.moh.cervicalcancer.submodule.dashboard.patient.utils.Utils.renderCheckMarkIconView;
import static zm.gov.moh.cervicalcancer.submodule.dashboard.patient.utils.Utils.dateCellView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardVisitTypeFragment extends Fragment {


    private BaseActivity context;
    TableLayout tableLayout;



    public PatientDashboardVisitTypeFragment() {
        // Required empty public constructor



    }

    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity)getContext();
        View view = inflater.inflate(R.layout.fragment_register_visit_type, container, false);
        //context = (PatientDashboardActivity) getContext();
        //context.getClientId();
        // Inflate the layout for this fragment

        //FragmentClientDashboardVitalsBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.fragment_client_dashboard_vitals, container, false);
       // View view = binding.getRoot();
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
        tableRow.addView(renderCheckMarkIconView(context, visitCompleted.get(ModuleConfig.VISIT_TYPE_ID_INTIAL_VIA)));
        tableRow.addView(renderCheckMarkIconView(context,  visitCompleted.get(ModuleConfig.VISIT_TYPE_ID_DELAYED_CRYOTHERAPHY_THERMAL_COAGULATION)));
        tableRow.addView(renderCheckMarkIconView(context, visitCompleted.get(ModuleConfig.VISIT_TYPE_ID_POST_TREATMENT_COMPILATION)));
        tableRow.addView(renderCheckMarkIconView(context, visitCompleted.get(ModuleConfig.VISIT_TYPE_ID_ONE_YEAR_FOLLOW_UP) ));
        tableRow.addView(renderCheckMarkIconView(context, visitCompleted.get(ModuleConfig.VISIT_TYPE_ID_ROUTINE_SCREENING)));
        tableRow.addView(renderCheckMarkIconView(context, visitCompleted.get(ModuleConfig.VISIT_TYPE_ID_REFERRAL_CRYOTHERAPHY_THERMAL_COAGULATION)));

        tableLayout.addView(tableRow);
    }

    public void recordCompletedVisits(List<Visit> visitList){

        HashMap<Long, Boolean> visitCompleted;
        visitCompleted = new HashMap<>();

        Iterator<Visit> visitIterator = visitList.iterator();

            while (visitIterator.hasNext()) {

                visitCompleted.put(ModuleConfig.VISIT_TYPE_ID_INTIAL_VIA, false);
                visitCompleted.put(ModuleConfig.VISIT_TYPE_ID_POST_TREATMENT_COMPILATION, false);
                visitCompleted.put(ModuleConfig.VISIT_TYPE_ID_DELAYED_CRYOTHERAPHY_THERMAL_COAGULATION, false);
                visitCompleted.put(ModuleConfig.VISIT_TYPE_ID_ONE_YEAR_FOLLOW_UP, false);
                visitCompleted.put(ModuleConfig.VISIT_TYPE_ID_REFERRAL_CRYOTHERAPHY_THERMAL_COAGULATION, false);
                visitCompleted.put(ModuleConfig.VISIT_TYPE_ID_ROUTINE_SCREENING, false);


                Visit visit = visitIterator.next();
                String date = visit.date_started.format(DateTimeFormatter.ISO_LOCAL_DATE);
                long visitTypeId = visit.getVisit_type_id();

                visitCompleted.put(visitTypeId,true);

                tabulateVisitRow(date,visitCompleted);
            }
    }


}


