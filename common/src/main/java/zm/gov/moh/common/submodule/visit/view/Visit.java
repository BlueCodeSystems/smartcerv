package zm.gov.moh.common.submodule.visit.view;

import androidx.appcompat.app.AlertDialog;
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
import zm.gov.moh.core.utils.Utils;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static zm.gov.moh.common.BR.preferences;

public class Visit extends BaseActivity {

    Button button;
    VisitViewModel viewModel;
    long visitTypeId;
    Bundle bundle;
    FormListAdapter formListAdapter;
    RecyclerView recyclerView;
    DatePicker datePicker;
    ImageView calenderPickerButton;
    Spinner spinner;
    ActivityVisitBinding binding;
    Long selectedVisitType;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_visit);

        button = findViewById(R.id.start_visit);

        spinner = this.findViewById(R.id.visit_type);

        calenderPickerButton = findViewById(R.id.date_picker);

        datePicker = Utils.dateDialog(this, calenderPickerButton,this::dateHandler).getDatePicker();
        datePicker.setMaxDate(System.currentTimeMillis());


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

        viewModel.getVisitStartDateObserver().observe(this, date -> binding.setVisitDate(date.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))));
    }

    public void initVisitList(List<VisitType> visitTypeList){

        if(visitTypeList.size() > 0) {

            LinkedList<String> visitTypes;
            final LinkedHashMap<String,Long> visitTypeIdList = new LinkedHashMap<>();

            for(VisitType visitType: visitTypeList)
                visitTypeIdList.put(visitType.getName(),visitType.getVisitTypeId());


            visitTypes = new LinkedList<>(visitTypeIdList.keySet());


            ArrayAdapter<String> visitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, visitTypes);

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

            viewModel.getVisitTypeObserver().observe(this, visitTypeId -> {

                int position = 0;
                for(Map.Entry<String, Long> entry :visitTypeIdList.entrySet()){

                   if(entry.getValue().equals(visitTypeId))
                        spinner.setSelection(position);

                    position++;
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
            binding.setVisitDateEnabled(true);
        }
        else if(visitState == VisitState.AMEND) {

            button.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.warning)));
            button.setText("End visit");
            formListAdapter.setClickable(true);
            binding.setVisitDateEnabled(false);

        }
        else if(visitState == VisitState.SESSION) {

            button.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.warning)));
            button.setText("End visit");
            formListAdapter.setClickable(true);
            recyclerView.setAdapter(formListAdapter);
            binding.setVisitDateEnabled(false);

            viewModel.createVisit();
            return;
        }
        else if(visitState == VisitState.END){
            viewModel.endVisit();
            finish();
            return;
        }



    }

    public void onBackPressed() {

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Do you want to return to the Patient Dashboard?")
                .setMessage("Click 'YES' to return to the Patient Dashboard OR click the 'Back Button' to close this dialog.")
                .setPositiveButton("YES", (DialogInterface dialogInterface, int i) -> {

                    this.finish();
                        }

                )
                .setNegativeButton("NO", (DialogInterface dialogInterface, int i) ->
                {
                })

                .show();

        {


        }



        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.green));

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


    public void dateHandler(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


        // set day of month , month and year value in the edit text
        this.viewModel.setVisitDate(LocalDate.of(year,monthOfYear + 1,dayOfMonth));

        return ;
    }


}
