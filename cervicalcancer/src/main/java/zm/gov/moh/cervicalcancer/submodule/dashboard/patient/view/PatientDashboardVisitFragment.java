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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter.FormJsonGroupExpandableListAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.VisitState;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.common.model.FormJson;
import zm.gov.moh.common.model.FormJsonGroup;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientDashboardVisitFragment extends Fragment implements View.OnClickListener {


    private BaseActivity context;
    TableLayout tableLayout;
    private Submodule formSubmodule;
    private Bundle bundle;
    private View rootView;
    private PatientDashboardViewModel viewModel;
    private VisitState mVisitState;
    private Button startButton;
    private HashMap<String,Object> formState;
    private ExpandableListView mFormGroupExpandableListView;
    private ImageView formInfoIcon;
    private TextView textView;

    public PatientDashboardVisitFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity)getContext();
        rootView = inflater.inflate(R.layout.fragment_patient_dashoard_visit, container, false);
        formSubmodule = ((BaseApplication)((BaseActivity) context).getApplication()).getSubmodule(BaseApplication.CoreSubmodules.FORM);
        viewModel = (PatientDashboardViewModel)context.getViewModel();

        this.bundle = getArguments();

        if(viewModel.getFormState() == null)
            viewModel.setFormState(new HashMap<>());

        formState = viewModel.getFormState();

        initFormState(formState);

        LinkedHashMap<String,Long> vistTypeIdMap = new LinkedHashMap<>();


        ArrayList<FormJsonGroup> formGroups = new ArrayList<>();

        FormJsonGroup viaFormGroup = new FormJsonGroup("VIA");
        FormJsonGroup leepFormGroup = new FormJsonGroup("LEEP");

        try {

            //initialize via forms
            FormJson reproductiveHealth = new FormJson("Reproductive Health History",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_reproductive_health.json")));

            FormJson hivStatus = new FormJson("HIV Status",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_hiv_status.json")));

            FormJson physicalExam = new FormJson("Physical Exam",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_physical_exam.json")));
            FormJson testResults = new FormJson("Test Results",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_test_results.json")));

            FormJson treatment = new FormJson("Referral",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/via_treatment.json")));
            FormJson referral = new FormJson("Treatment",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/via_referral.json")));
            FormJson prescriptions = new FormJson("Prescription(s)",
                    Utils.getStringFromInputStream(context.getAssets().open("forms/treatment_cryo_prescriptions.json")));

            //Add via forms to a form group
            viaFormGroup.addForm(reproductiveHealth);
            viaFormGroup.addForm(hivStatus);
            viaFormGroup.addForm(physicalExam);
            viaFormGroup.addForm(testResults);
            viaFormGroup.addForm(referral);
            viaFormGroup.addForm(treatment);
            viaFormGroup.addForm(prescriptions);
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

                    bundle.putSerializable(BaseActivity.JSON_FORM_KEY, adapter.getChild(groupPosition,childPosition));
                    bundle.putSerializable(Key.FORM_STATE, formState);

                    context.startSubmodule(formSubmodule, bundle);
                    return false;
                });


        //Add vist type map to their ids
        vistTypeIdMap.put("Inital Visit",1L);
        vistTypeIdMap.put("Delayed Cryotheraphy/Thermal Coagulation",2L);
        vistTypeIdMap.put("Post-Treatment Complication",3L);
        vistTypeIdMap.put("One-Year Follow Up",4L);
        vistTypeIdMap.put("Routing Screening",5L);
        vistTypeIdMap.put("Referral for Cryotherapy/Thermal Coagulation",6L);

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

                    formState.put(Key.VISIT_TYPE_ID, visitTypeId);
                }else if(visitTypeId != (long)formState.get(Key.VISIT_TYPE_ID)) {


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

            colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.light_green));
            text = "Start Visit";
        }
        else{

            colorStateList = ColorStateList.valueOf(getResources().getColor(R.color.warning));
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

    public void initFormState(HashMap<String,Object> formState){

        final long SESSION_LOCATION_ID = context.getViewModel().getRepository().getDefaultSharePrefrences()
                .getLong(context.getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);
        final String USER_UUID = context.getViewModel().getRepository().getDefaultSharePrefrences()
                .getString(context.getResources().getString(zm.gov.moh.core.R.string.logged_in_user_uuid_key), "null");
        final Long PERSON_ID = bundle.getLong(Key.PERSON_ID);


        formState.put(Key.LOCATION_ID, SESSION_LOCATION_ID);
        formState.put(Key.PERSON_ID, PERSON_ID);

        context.getViewModel()
                .getRepository()
                .getDatabase()
                .providerUserDao()
                .getAllByUserUuid(USER_UUID)
                .observe(context, providerUser -> {

                    formState.put(Key.PROVIDER_ID, providerUser.provider_id);
                    formState.put(Key.USER_ID, providerUser.user_id);
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