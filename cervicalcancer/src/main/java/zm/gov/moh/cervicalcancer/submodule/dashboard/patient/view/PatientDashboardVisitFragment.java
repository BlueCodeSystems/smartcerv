package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TableLayout;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter.FormJsonGroupExpandableListAdapter;
import zm.gov.moh.common.model.FormJson;
import zm.gov.moh.common.model.FormJsonGroup;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardVisitFragment extends Fragment {


    private BaseActivity context;
    TableLayout tableLayout;

     Submodule formSubmodule;
     Bundle bundle;

    public PatientDashboardVisitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity)getContext();
        View view = inflater.inflate(R.layout.fragment_patient_dashoard_visit, container, false);
        formSubmodule = ((BaseApplication)((BaseActivity) context).getApplication()).getSubmodule(BaseApplication.CoreSubmodules.FORM);

        bundle = getArguments();
        //context = (PatientDashboardActivity) getContext();
        //context.getClientId();
        // Inflate the layout for this fragment

        //FragmentClientDashboardVitalsBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.fragment_client_dashboard_vitals, container, false);
       // View view = binding.getRoot();

        FormJsonGroup formJsonGroup = new FormJsonGroup("Cryotherapy");

        try {

            //forms
            FormJson reproductiveHealth = new FormJson("Reproductive Health History",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_reproductive_health.json")));

            FormJson hivStatus = new FormJson("HIV Status",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_hiv_status.json")));

            FormJson physicalExam = new FormJson("Physical Exam",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_physical_exam.json")));
            FormJson testResults = new FormJson("Test Results",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_test_results.json")));
            FormJson prescriptions = new FormJson("Prescription(s)",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_prescriptions.json")));

            formJsonGroup.addForm(reproductiveHealth);
            formJsonGroup.addForm(hivStatus);
            formJsonGroup.addForm(physicalExam);
            formJsonGroup.addForm(testResults);
            formJsonGroup.addForm(prescriptions);
        }catch (Exception e){

        }


        ArrayList<FormJsonGroup> list = new ArrayList();
        list.add(formJsonGroup);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Inital Visit");
        arrayList.add("Delayed Cryotheraphy/Thermal Coagulation");
        arrayList.add("Post-Treatment Complication");
        arrayList.add("One-Year Follow Up");
        arrayList.add("Routing Screening");
        arrayList.add("Referral for Cryotherapy/Thermal Coagulation");
        ArrayAdapter<String> visitAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,arrayList);

        Spinner spinner = view.findViewById(R.id.visit_type);

        spinner.setAdapter(visitAdapter);

        FormJsonGroupExpandableListAdapter adapter = new FormJsonGroupExpandableListAdapter(context,list);
        ExpandableListView listView = view.findViewById(R.id.visit_forms);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(
                (ExpandableListView parent, View v, int groupPosition, int childPosition, long id) -> {

                    bundle = new Bundle();
                    bundle.putSerializable(BaseActivity.JSON_FORM_KEY, adapter.getChild(groupPosition,childPosition));
                    context.startSubmodule(formSubmodule, bundle);
                return false;
            });

        return view;
    }
}


