package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;

import java.util.Collection;
import java.util.Map;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.common.base.BaseActivity;

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
        ((PatientDashboardViewModel) context.getViewModel()).getReferralDataEmitter().observe(context,this::populateRefferrals);
        return view;
    }

    public void populateRefferrals(Map<Long,Collection<Boolean>> refferalResults){

        if(tableLayout.getChildCount()> 0)
            tableLayout.removeAllViews();

        for (Map.Entry<Long,Collection<Boolean>> entry : refferalResults.entrySet()){

            Instant dateTime = Instant.ofEpochSecond(entry.getKey());

            TableRow tableRow = new TableRow(context);
            tableRow.setBackground(context.getResources().getDrawable(R.drawable.border_bottom));
            tableRow.addView(dateCellView(context,String.valueOf(dateTime.atZone(ZoneId.systemDefault()).format(org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE))));

            for(boolean v :entry.getValue())
                tableRow.addView(renderCheckMarkIconView(context, v));

            tableLayout.addView(tableRow);
        }
    }

    /*
    public void populateScreeningObservations(Map<Long,Collection<Boolean>> screeningResults){

        if(tableLayout.getChildCount()> 0)
            tableLayout.removeAllViews();


        for (Map.Entry<Long,Collection<Boolean>> b : screeningResults.entrySet()){

            Instant dateTime = Instant.ofEpochSecond(b.getKey());

            TableRow tableRow = new TableRow(context);
            tableRow.setBackground(context.getResources().getDrawable(R.drawable.border_bottom));
            tableRow.addView(dateCellView(context,String.valueOf(dateTime.atZone(ZoneId.systemDefault()).format(org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE))));
                for(boolean v :b.getValue())
                    tableRow.addView(renderCheckMarkIconView(context, v));

            tableLayout.addView(tableRow);
        }
    }
     */
}


