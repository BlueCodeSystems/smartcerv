package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.Gravity;
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

import static zm.gov.moh.cervicalcancer.submodule.dashboard.patient.utils.Utils.dateCellView;
import static zm.gov.moh.cervicalcancer.submodule.dashboard.patient.utils.Utils.providerInitialsCellView;
import static zm.gov.moh.cervicalcancer.submodule.dashboard.patient.utils.Utils.renderCheckMarkIconView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardProviderFragment extends Fragment {


    private BaseActivity context;
    TableLayout tableLayout;

    public PatientDashboardProviderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity)getContext();
        View view = inflater.inflate(R.layout.fragment_patient_dashoard_provider, container, false);

        tableLayout = view.findViewById(R.id.visit_type_table);


        ((PatientDashboardViewModel) context.getViewModel()).getProviderDataEmitter().observe(context,this::populateProviderRole);
        return view;
    }


    public void populateProviderRole(Map<Long, Collection<Map.Entry<String, String>>> screeningResults){

        if(tableLayout.getChildCount()> 0)
            tableLayout.removeAllViews();

        for (Map.Entry<Long, Collection<Map.Entry<String, String>>> b : screeningResults.entrySet()){

            Instant dateTime = Instant.ofEpochSecond(b.getKey());

            TableRow tableRow = new TableRow(context);
            tableRow.setBackground(context.getResources().getDrawable(R.drawable.border_bottom));
            tableRow.addView(dateCellView(context,String.valueOf(dateTime.atZone(ZoneId.systemDefault()).format(org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE))));
            for(Map.Entry<String, String> v :b.getValue())
                tableRow.addView(providerInitialsCellView(context, v));

            tableLayout.addView(tableRow);
        }
    }

}


