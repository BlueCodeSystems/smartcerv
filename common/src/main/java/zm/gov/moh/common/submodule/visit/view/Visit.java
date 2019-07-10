package zm.gov.moh.common.submodule.visit.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.common.OpenmrsConfig;
import zm.gov.moh.common.R;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.common.model.VisitMetadata;
import zm.gov.moh.common.submodule.visit.adapter.FormListAdapter;
import zm.gov.moh.common.submodule.visit.viewmodel.VisitViewModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.repository.database.entity.domain.VisitType;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Visit extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);


        viewModel = ViewModelProviders.of(this).get(VisitViewModel.class);
        setViewModel(viewModel);


       /* //Passed visit types
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add(OpenmrsConfig.VISIT_TYPE_UUID_FACILITY_VISIT);

        //Passed visit forms
        LinkedList<JsonForm> linkedListForms = new LinkedList<>();

        try {
            JsonForm reproductiveHealth = new JsonForm("Reproductive Health History",
                    Utils.getStringFromInputStream(this.getAssets().open("forms/client_registration.json")));
            JsonForm reproductiveHealth2 = new JsonForm("HIV Status",
                    Utils.getStringFromInputStream(this.getAssets().open("forms/client_registration.json")));
            JsonForm reproductiveHealth3 = new JsonForm("Physical Exam",
                    Utils.getStringFromInputStream(this.getAssets().open("forms/client_registration.json")));
"via_hiv_status","via_physical_exam","via_test_results","via_treatment","via_treatment","via_prescriptions"
            linkedListForms.add(reproductiveHealth);
            linkedListForms.add(reproductiveHealth2);
            linkedListForms.add(reproductiveHealth3);
        }catch (Exception e){

        }*/


        String module = BaseApplication.CoreModule.FORM;
        Bundle bundle = getIntent().getExtras();
       // bundle.putSerializable(Key.VISIT_LIST,linkedList);
       // bundle.putSerializable(Key.VISIT_FORM_LIST,linkedListForms);
        bundle.putString(Key.MODULE,module);

        VisitMetadata visitMetadata =(VisitMetadata) bundle.getSerializable(Key.VISIT_METADATA);

        List<String> visitList =  visitMetadata.getVisitTypes();

        viewModel.getRepository()
                .getDatabase()
                .visitTypeDao()
                .getById(visitList)
                .observe(this, this::initVisitList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        FormListAdapter adapter = new FormListAdapter(this,bundle);
        RecyclerView recyclerView = findViewById(R.id.visit_forms);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    public void initVisitList(List<VisitType> visitTypeList){

        if(visitTypeList.size() > 0) {

            LinkedList<String> visitTypes;
            final LinkedHashMap<String,Long> visitTypeId = new LinkedHashMap<>();

            for(VisitType visitType: visitTypeList)
                visitTypeId.put(visitType.getName(),visitType.getVisitTypeId());


            visitTypes = new LinkedList<>(visitTypeId.keySet());


            ArrayAdapter<String> visitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, visitTypes);
            Spinner spinner = this.findViewById(R.id.visit_type);
            spinner.setAdapter(visitAdapter);

            //Spinner item click listener
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String visitSelected = spinner.getSelectedItem().toString();
                    int visitSelectedIndex = spinner.getSelectedItemPosition();
                    //long visitTypeId = visitTypeId.get(visitSelected);

                   /* if(viewModel.getVisitState().getState() == VisitState.ENDED) {

                        bundle.putLong(Key.VISIT_TYPE_ID, visitTypeId);
                    }else if(visitTypeId != (long) bundle.get(Key.VISIT_TYPE_ID)) {


                        createDialog("Visit session will end",
                                (DialogInterface dialogInterface, int j) -> viewModel.setVisitState(VisitState.ENDED), null).show();
                        adapterView.setSelection(visitSelectedIndex);
                    }*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

    }


    public void initForms(LinkedHashMap<String,String> visitList){


    }


}
