package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.common.ui.BaseActivity;
import static zm.gov.moh.cervicalcancer.submodule.dashboard.patient.utils.Utils.crossMarkCellView;
import static zm.gov.moh.cervicalcancer.submodule.dashboard.patient.utils.Utils.dateCellView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardVisitType2Fragment extends Fragment {


    private BaseActivity context;
    TableLayout tableLayout;

    public PatientDashboardVisitType2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity)getContext();
        View view = inflater.inflate(R.layout.fragment_register_visit_type_2, container, false);
        //context = (PatientDashboardActivity) getContext();
        //context.getClientId();
        // Inflate the layout for this fragment

        //FragmentClientDashboardVitalsBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.fragment_client_dashboard_vitals, container, false);
       // View view = binding.getRoot();

        tableLayout = view.findViewById(R.id.visit_type_table);

        populateVisitType("03/06/17", false, true, false);
        populateVisitType("24/10/18", true, false, false);
        populateVisitType("13/05/19", false, false, true);
        populateVisitType("01/02/20", false, true, false);
        populateVisitType("13/05/19", false, false, true);
        populateVisitType("01/02/20", false, true, false);
        populateVisitType("13/05/19", false, false, true);
        populateVisitType("01/02/21", false, true, false);
        return view;
    }

    public void populateVisitType(String date, boolean initialVia, boolean previousPostPonedCryo, boolean postTreatmentComplication){

        TableRow tableRow = new TableRow(context);
        tableRow.setBackground(getResources().getDrawable(R.drawable.border_bottom));

        tableRow.addView(dateCellView(context,date));
        tableRow.addView(crossMarkCellView(context, initialVia ));
        tableRow.addView(crossMarkCellView(context, previousPostPonedCryo));
        tableRow.addView(crossMarkCellView(context, postTreatmentComplication));

        tableLayout.addView(tableRow);
    }
}


