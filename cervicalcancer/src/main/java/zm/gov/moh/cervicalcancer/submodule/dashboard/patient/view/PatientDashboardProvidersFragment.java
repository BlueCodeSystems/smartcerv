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

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.common.ui.BaseActivity;

import static zm.gov.moh.cervicalcancer.submodule.dashboard.patient.utils.Utils.dateCellView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardProvidersFragment extends Fragment {


    private BaseActivity context;
    TableLayout tableLayout;

    public PatientDashboardProvidersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity)getContext();
        View view = inflater.inflate(R.layout.fragment_patient_dashoard_provider, container, false);
        //context = (PatientDashboardActivity) getContext();
        //context.getClientId();
        // Inflate the layout for this fragment

        //FragmentClientDashboardVitalsBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.fragment_client_dashboard_vitals, container, false);
       // View view = binding.getRoot();

        tableLayout = view.findViewById(R.id.visit_type_table);

        populateProviderInitials("03/06/17", "AT", "AT");
        populateProviderInitials("24/10/18", "CB", "BS");
        populateProviderInitials("13/05/19", "FM", "BK");
        populateProviderInitials("01/02/20", "CS", "ZK");
        populateProviderInitials("13/05/19", "JB", "WN");
        populateProviderInitials("01/02/20", "KL", "MN");
        populateProviderInitials("13/05/19", "Gl", "GL");
        populateProviderInitials("01/02/21", "NT", "CK");
        return view;
    }

    public void populateProviderInitials(String date, String screening, String treatment){

        TableRow tableRow = new TableRow(context);
        tableRow.setBackground(context.getResources().getDrawable(R.drawable.border_bottom));

        tableRow.addView(dateCellView(context,date));
        tableRow.addView(providerInitialsCellView(context, screening ));
        tableRow.addView(providerInitialsCellView(context, treatment));

        tableLayout.addView(tableRow);
    }

    public static AppCompatTextView providerInitialsCellView(Context context, String intials){

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(zm.gov.moh.core.utils.Utils.dpToPx(context,0), TableRow.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        AppCompatTextView intialsTv = new AppCompatTextView(context);
        intialsTv.setText(intials);
        intialsTv.setGravity(Gravity.CENTER);
        intialsTv.setTypeface(null,Typeface.BOLD);
        intialsTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        intialsTv.setTextColor(context.getResources().getColor(R.color.colorAccent));
        intialsTv.setLayoutParams(layoutParams);
        intialsTv.setBackground(context.getResources().getDrawable(R.drawable.border_right));

        return intialsTv;
    }
}


