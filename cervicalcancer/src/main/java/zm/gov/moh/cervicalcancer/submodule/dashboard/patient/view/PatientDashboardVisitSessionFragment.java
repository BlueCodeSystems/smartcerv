package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;


import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import zm.gov.moh.cervicalcancer.OpenmrsConfig;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter.FormJsonGroupExpandableListAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.VisitState;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.common.model.FormJsonGroup;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardVisitSessionFragment extends Fragment implements View.OnClickListener {


    private BaseActivity context;
    TableLayout tableLayout;
    private Module formModule;
    private Bundle bundle;
    private View rootView;
    private PatientDashboardViewModel viewModel;
    private VisitState mVisitState;
    private Button startButton;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ExpandableListView mFormGroupExpandableListView;
    private ImageView formInfoIcon;
    private TextView textView;
    private File image;

    public PatientDashboardVisitSessionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity)getContext();
        rootView = inflater.inflate(R.layout.fragment_patient_dashoard_visit_session, container, false);
        formModule = ((BaseApplication)((BaseActivity) context).getApplication()).getModule(BaseApplication.CoreModule.FORM);
        viewModel = (PatientDashboardViewModel)context.getViewModel();

        this.bundle = getArguments();

        viewModel.setBundle(bundle);

        if(viewModel.getBundle() == null)
            viewModel.setBundle(new Bundle());


        initFormState(bundle);

        LinkedHashMap<String,Long> vistTypeIdMap = new LinkedHashMap<>();


        ArrayList<FormJsonGroup> formGroups = new ArrayList<>();

        FormJsonGroup viaFormGroup = new FormJsonGroup("VIA");
        FormJsonGroup leepFormGroup = new FormJsonGroup("LEEP");

        try {

            //initialize via forms
            JsonForm reproductiveHealth = new JsonForm("Reproductive Health History",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_reproductive_health.json")));

            JsonForm hivStatus = new JsonForm("HIV Status",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_hiv_status.json")));

            JsonForm physicalExam = new JsonForm("Physical Exam",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_physical_exam.json")));
            JsonForm testResults = new JsonForm("Test Results",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_test_results.json")));

            JsonForm treatment = new JsonForm("Referral",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/via_referral.json")));
            JsonForm referral = new JsonForm("Treatment",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/via_treatment.json")));
            JsonForm prescriptions = new JsonForm("Prescription(s)",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_prescriptions.json")));

            JsonForm notes = new JsonForm("Notes & Recommendations",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/notes_recommendations.json")));
            JsonForm referral_slip = new JsonForm("Cervical Health Referral Slip",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/cervical_health_referral_slip.json")));

            // initialize LEEP forms
            JsonForm evaluation = new JsonForm("Evaluation",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/leep_evaluation.json")));
            JsonForm outcomes = new JsonForm("Outcomes",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/leep_outcomes.json")));
            JsonForm final_diagnosis = new JsonForm("Final Diagnosis & Plan",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/leep_final_diagnosis_plan.json")));

            //Add via and leep forms to a form group
            viaFormGroup.addForm(reproductiveHealth);
            viaFormGroup.addForm(hivStatus);
            viaFormGroup.addForm(physicalExam);
            viaFormGroup.addForm(testResults);
            viaFormGroup.addForm(referral);
            viaFormGroup.addForm(treatment);
            viaFormGroup.addForm(prescriptions);
            viaFormGroup.addForm(notes);
            //viaFormGroup.addForm(referral_slip);
            leepFormGroup.addForm(evaluation);
            leepFormGroup.addForm(outcomes);
            leepFormGroup.addForm(final_diagnosis);
        }catch (Exception e){

        }

        formGroups.add(viaFormGroup);
        formGroups.add(leepFormGroup);

        FormJsonGroupExpandableListAdapter adapter = new FormJsonGroupExpandableListAdapter(context,formGroups);
        mFormGroupExpandableListView = rootView.findViewById(R.id.visit_forms);
        mFormGroupExpandableListView.setAdapter(adapter);

        //Expandable click listener
        mFormGroupExpandableListView.setOnChildClickListener(
                (ExpandableListView parent, View v, int groupPosition, int childPosition, long id) -> {

                    bundle.putSerializable(BaseActivity.JSON_FORM, adapter.getChild(groupPosition,childPosition));

                    context.startModule(formModule, bundle);
                    return false;
                });


        //Add vist type map to their ids
        vistTypeIdMap.put("Inital Visit", OpenmrsConfig.VISIT_TYPE_ID_INTIAL_VIA);
        vistTypeIdMap.put("Delayed Cryotheraphy/Thermal Coagulation", OpenmrsConfig.VISIT_TYPE_ID_DELAYED_CRYOTHERAPHY_THERMAL_COAGULATION);
        vistTypeIdMap.put("Post-Treatment Complication", OpenmrsConfig.VISIT_TYPE_ID_POST_TREATMENT_COMPILATION);
        vistTypeIdMap.put("One-Year Follow Up", OpenmrsConfig.VISIT_TYPE_ID_ONE_YEAR_FOLLOW_UP);
        vistTypeIdMap.put("Routine Screening",OpenmrsConfig.VISIT_TYPE_ID_ROUTINE_SCREENING);
        vistTypeIdMap.put("Referral for Cryotherapy/Thermal Coagulation", OpenmrsConfig.VISIT_TYPE_ID_REFERRAL_CRYOTHERAPHY_THERMAL_COAGULATION);

        LinkedList<String> visitType = new LinkedList<>(vistTypeIdMap.keySet());
        ArrayAdapter<String> visitAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, visitType);

        Spinner spinner = rootView.findViewById(R.id.visit_type);
        spinner.setAdapter(visitAdapter);

        //Spinner item click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String visitSelected = spinner.getSelectedItem().toString();
                int visitSelectedIndex = spinner.getSelectedItemPosition();
                long visitTypeId = vistTypeIdMap.get(visitSelected);

                if(viewModel.getVisitState().getState() == VisitState.ENDED) {

                    bundle.putLong(Key.VISIT_TYPE_ID, visitTypeId);
                }else if(visitTypeId != (long) bundle.get(Key.VISIT_TYPE_ID)) {


                    createDialog("Visit session will end",
                            (DialogInterface dialogInterface, int j) -> viewModel.setVisitState(VisitState.ENDED), null).show();
                    adapterView.setSelection(visitSelectedIndex);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        init();
        return rootView;
    }

    public void init(){

        startButton = rootView.findViewById(R.id.start_visit);
        //imageButton1 = rootView.findViewById(R.id.load_imag);
        imageButton2 = rootView.findViewById(R.id.load_image);
        formInfoIcon = rootView.findViewById(R.id.form_info_ic);
        textView = rootView.findViewById(R.id.no_forms_placeholder);
        startButton.setOnClickListener(this);
        formInfoIcon.setOnClickListener(this);
        viewModel.getEmitVisitState().observe(context, this::onVisitStateChange);
        mVisitState = viewModel.getVisitState();
        viewModel.setVisitState(VisitState.ENDED);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id == R.id.start_visit){

            if(mVisitState.getState() != VisitState.STARTED)
                createDialog("Start Visit?",
                        (DialogInterface dialogInterface, int i)-> viewModel.setVisitState(VisitState.STARTED),
                        null).show();
            else
                createDialog("Stop Visit?",
                        (DialogInterface dialogInterface, int i)-> viewModel.setVisitState(VisitState.ENDED),
                        null).show();
        }else if(id == R.id.form_info_ic){

            Toast.makeText(context, "Some forms are only available after starting a visit session", Toast.LENGTH_LONG).show();
        }

    }

    public void updatedButtonStyle(Button button, int visitState){

        ColorStateList colorStateList;
        String text;

        if(visitState != VisitState.STARTED){

            colorStateList = ColorStateList.valueOf(context.getResources().getColor(R.color.light_green));
            text = "Start Visit";
        }
        else{

            colorStateList = ColorStateList.valueOf(context.getResources().getColor(R.color.warning));
            text = "End Visit";
        }

        button.setText(text);
        button.setBackgroundTintList(colorStateList);
    }

    public void onVisitStateChange(int visitState) {

        updatedButtonStyle(startButton, visitState);

        updateFormVisibility(visitState);

    }



    public void updateFormVisibility(int visitState){

        if(visitState == VisitState.STARTED) {

            formInfoIcon.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            mFormGroupExpandableListView.setVisibility(View.VISIBLE);
        }
        else {

            mFormGroupExpandableListView.setVisibility(View.GONE);
            formInfoIcon.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    public void initFormState(Bundle bundle){

        final long SESSION_LOCATION_ID = context.getViewModel().getRepository().getDefaultSharePrefrences()
                .getLong(Key.LOCATION_ID, 1);
        final String USER_UUID = context.getViewModel().getRepository().getDefaultSharePrefrences()
                .getString(Key.AUTHORIZED_USER_UUID, "null");
        bundle.putLong(Key.LOCATION_ID, SESSION_LOCATION_ID);


        context.getViewModel()
                .getRepository()
                .getDatabase()
                .providerUserDao()
                .getAllByUserUuid(USER_UUID)
                .observe(context, providerUser -> {

                    bundle.putLong(Key.PROVIDER_ID, providerUser.getProviderId());
                    bundle.putLong(Key.USER_ID, providerUser.getUserId());
                });
    }


    public AlertDialog createDialog(String text, DialogInterface.OnClickListener positiveCallback,  DialogInterface.OnClickListener negativeCallback){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        return  alertDialog.setMessage(text)
                .setPositiveButton("Confirm",positiveCallback)
                .setNegativeButton("Cancel",negativeCallback)
                .create();
    }
}