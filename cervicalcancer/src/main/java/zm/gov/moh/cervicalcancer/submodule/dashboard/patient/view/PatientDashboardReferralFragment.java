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

import static zm.gov.moh.cervicalcancer.submodule.dashboard.patient.utils.Utils.renderCheckMarkIconView;
import static zm.gov.moh.cervicalcancer.submodule.dashboard.patient.utils.Utils.dateCellView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardReferralFragment extends Fragment {


    private BaseActivity context;
    TableLayout tableLayout;

    public PatientDashboardReferralFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity)getContext();
        View view = inflater.inflate(R.layout.fragment_patient_dashoard_referral, container, false);
        //context = (PatientDashboardActivity) getContext();
        //context.getClientId();
        // Inflate the layout for this fragment

        //FragmentClientDashboardVitalsBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.fragment_client_dashboard_vitals, container, false);
       // View view = binding.getRoot();

        tableLayout = view.findViewById(R.id.visit_type_table);

        populateRefferrals("03/06/17", false, true);
        populateRefferrals("24/10/18", true, false);
        populateRefferrals("13/05/19", false, false);
        populateRefferrals("01/02/20", false, true);
        populateRefferrals("13/05/19", false, false);
        populateRefferrals("01/02/20", false, true);
        populateRefferrals("13/05/19", false, false);
        populateRefferrals("01/02/21", false, true);
        return view;
    }

    public void populateRefferrals(String date, boolean largeLesion, boolean suspectedCancer){

        TableRow tableRow = new TableRow(context);
        tableRow.setBackground(context.getResources().getDrawable(R.drawable.border_bottom));

        tableRow.addView(dateCellView(context,date));
        tableRow.addView(renderCheckMarkIconView(context, largeLesion ));
        tableRow.addView(renderCheckMarkIconView(context, suspectedCancer));

        tableLayout.addView(tableRow);
    }
}


