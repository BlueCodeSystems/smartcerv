package zm.gov.moh.common.submodule.visit.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.ActivityVisitBinding;
import zm.gov.moh.common.model.VisitMetadata;
import zm.gov.moh.common.submodule.visit.adapter.FormListAdapter;
import zm.gov.moh.core.model.VisitState;
import zm.gov.moh.common.submodule.visit.viewmodel.VisitViewModel;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.VisitType;
import zm.gov.moh.core.utils.BaseApplication;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Visit extends BaseActivity {

    Button button;
    VisitViewModel viewModel;
    long visitTypeId;
    Bundle bundle;
    FormListAdapter formListAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);
        viewModel = ViewModelProviders.of(this).get(VisitViewModel.class);

        ActivityVisitBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_visit);

        button = findViewById(R.id.start_visit);

        viewModel = ViewModelProviders.of(this).get(VisitViewModel.class);
        setViewModel(viewModel);
        binding.setViewmodel(viewModel);
        viewModel.getViewState().observe(this,this::updateViewState);

        String module = BaseApplication.CoreModule.FORM;
        bundle = getIntent().getExtras();
        viewModel.setBundle(bundle);
       // bundle.putSerializable(Key.VISIT_LIST,linkedList);
       // bundle.putSerializable(Key.VISIT_FORM_LIST,linkedListForms);
        bundle.putString(Key.MODULE,module);
        initForms();
        updateViewState(bundle);
    }

    public void initVisitList(List<VisitType> visitTypeList){

        if(visitTypeList.size() > 0) {

            LinkedList<String> visitTypes;
            final LinkedHashMap<String,Long> visitTypeIdList = new LinkedHashMap<>();

            for(VisitType visitType: visitTypeList)
                visitTypeIdList.put(visitType.getName(),visitType.getVisitTypeId());


            visitTypes = new LinkedList<>(visitTypeIdList.keySet());


            ArrayAdapter<String> visitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, visitTypes);
            Spinner spinner = this.findViewById(R.id.visit_type);
            spinner.setAdapter(visitAdapter);

            //Spinner item click listener
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String visitSelected = spinner.getSelectedItem().toString();
                    visitTypeId = visitTypeIdList.get(visitSelected);
                    bundle.putLong(Key.VISIT_TYPE_ID, visitTypeId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

    }

    public void updateViewState(Bundle bundle){

        if(bundle == null)
            return;

        VisitState visitState = (VisitState)bundle.getSerializable(Key.VISIT_STATE);

        if(visitState == VisitState.NEW){

            button.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.light_green)));
            button.setText("Start visit");
        }
        else if(visitState == VisitState.AMEND) {

            button.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.warning)));
            button.setText("End visit");
            formListAdapter.setClickable(true);

        }
        else if(visitState == VisitState.SESSION) {

            button.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.warning)));
            button.setText("End visit");
            formListAdapter.setClickable(true);
            recyclerView.setAdapter(formListAdapter);

            viewModel.createVisit();
            return;
        }
        else if(visitState == VisitState.END){
            viewModel.endVisit();
            finish();
            return;
        }



    }


    public void initForms(){

        VisitMetadata visitMetadata =(VisitMetadata) bundle.getSerializable(Key.VISIT_METADATA);

        List<String> visitList =  visitMetadata.getVisitTypes();

        viewModel.getRepository()
                .getDatabase()
                .visitTypeDao()
                .getById(visitList)
                .observe(this, this::initVisitList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        formListAdapter = new FormListAdapter(this,bundle);
        recyclerView = findViewById(R.id.visit_forms);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(formListAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


}
