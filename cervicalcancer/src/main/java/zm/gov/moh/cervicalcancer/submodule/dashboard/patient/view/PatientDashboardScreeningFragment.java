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
public class PatientDashboardScreeningFragment extends Fragment {


    private BaseActivity context;
    TableLayout tableLayout;

    public PatientDashboardScreeningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity)getContext();
        View view = inflater.inflate(R.layout.fragment_patient_dashoard_screening, container, false);
        //context = (PatientDashboardActivity) getContext();
        //context.getClientId();
        // Inflate the layout for this fragment

        //FragmentClientDashboardVitalsBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.fragment_client_dashboard_vitals, container, false);
       // View view = binding.getRoot();

        tableLayout = view.findViewById(R.id.visit_type_table);

        populateScreeningObservations("03/06/17", false, true, false, true);
        populateScreeningObservations("24/10/18", true, false, false,  false);
        populateScreeningObservations("13/05/19", false, false, true,true);
        populateScreeningObservations("01/02/20", false, true, false, false);
        populateScreeningObservations("13/05/19", false, false, true, false);
        populateScreeningObservations("01/02/20", false, true, false,true);
        populateScreeningObservations("13/05/19", false, false, true, true);
        populateScreeningObservations("01/02/21", false, true, false,false);
        return view;
    }

    public void populateScreeningObservations(String date, boolean viaDone, boolean viaNegative, boolean viaPositive, boolean suspectedCancer){

        TableRow tableRow = new TableRow(context);
        tableRow.setBackground(getResources().getDrawable(R.drawable.border_bottom));

        tableRow.addView(dateCellView(context,date));
        tableRow.addView(crossMarkCellView(context, viaDone ));
        tableRow.addView(crossMarkCellView(context, viaNegative));
        tableRow.addView(crossMarkCellView(context, viaPositive));
        tableRow.addView(crossMarkCellView(context, suspectedCancer));


        tableLayout.addView(tableRow);
    }
}


