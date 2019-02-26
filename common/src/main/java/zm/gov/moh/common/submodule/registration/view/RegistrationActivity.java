package zm.gov.moh.common.submodule.registration.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;
import zm.gov.moh.common.BR;
import zm.gov.moh.common.R;
import zm.gov.moh.common.R2;
import zm.gov.moh.common.databinding.RegistrationActivityBinding;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardActivity;
import zm.gov.moh.common.submodule.registration.viewmodel.RegistrationViewModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;


public class RegistrationActivity extends BaseActivity {

    Resources resources;
    @BindView(R2.id.date_of_birth)
     Button date;

    @BindView(R2.id.first_name) EditText firstName;
    @BindView(R2.id.last_name) EditText lastName;
    @BindView(R2.id.province) EditText province;
    @BindView(R2.id.district) EditText district;
    @BindView(R2.id.address1) EditText address1;
    private Bundle bundle;
    private Module clientDashBoardModule;


    private RegistrationViewModel registrationViewModel;

    Map<Integer, String[]> validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = new Bundle();

        AndroidThreeTen.init(this);

        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);

        //Data binding
        RegistrationActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.registration_activity);
        binding.setRegformdata(registrationViewModel.getRegistrationFormData());
        binding.setVariable(BR.regviewmodel, registrationViewModel);
        ButterKnife.bind(this);

        resources = this.getResources();

        Utils.dateDialog(this, date, (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {

            // set day of month , month and year value in the edit text
            String dob = year + "-" + ((monthOfYear + 1 < 10)? "0"+(monthOfYear + 1 ):(monthOfYear + 1 ))  + "-" + ((dayOfMonth < 10)? "0"+dayOfMonth:dayOfMonth);
            registrationViewModel.onDateOfBirthChanged(dob);

            if(date.getError() != null)
                date.setError(null);
        });

        clientDashBoardModule = ((BaseApplication)this.getApplication()).getSubmodule(BaseApplication.CoreModule.CLIENT_DASHOARD);

        init();
    }

    public void init(){

        String[] validationCondition = {resources.getString(zm.gov.moh.core.R.string.regex_alphabet_atleast3),
                resources.getString(zm.gov.moh.core.R.string.atleast_3_characters_long)};

        String[] aphanumeric = {resources.getString(zm.gov.moh.core.R.string.regex_alphanumeric_words),resources.getString(zm.gov.moh.core.R.string.only_aphanumeric_characters)};



        validation = new HashMap<>();
        validation.put(firstName.getId(),validationCondition);
        validation.put(lastName.getId(), validationCondition);
        validation.put(address1.getId(), aphanumeric);
        validation.put(district.getId(), validationCondition);
        validation.put(province.getId(), validationCondition);


        final Observer<Boolean> observer = validateAndSubmit -> {

            registrationViewModel.setFormValid(true);
            EditText editText;
            EditText earliestEditTextwithErr = null;

            if(validateAndSubmit)
                for (Map.Entry validation: validation.entrySet()){

                    editText = findViewById((int)validation.getKey());
                    editText.requestFocus();

                    if(editText.getError() != null)
                        earliestEditTextwithErr = editText;

                }

                if(registrationViewModel.getRegistrationFormData().getDateOfBirth() == null){

                    date.setError("select a date");
                    registrationViewModel.setFormValid(false);
                }

                if(earliestEditTextwithErr != null)
                    earliestEditTextwithErr.requestFocus();


                if(registrationViewModel.getFormValid()){

                    Toast.makeText(this,"New Client was registered",Toast.LENGTH_LONG).show();
                    registrationViewModel.submitForm();
                }
        };

        final Observer<Long> getClientDashBoardTransitionObserver = clientId -> {

            bundle.putLong(ClientDashboardActivity.PERSON_ID, clientId);
            startModule(clientDashBoardModule, bundle);
            finish();
        };

        registrationViewModel.getValidateAndSubmitFormObserver().observe(this, observer);
        registrationViewModel.getClientDashBoardTransition().observe(this, getClientDashBoardTransitionObserver);
    }

    //validate on focus lost
    @OnFocusChange({R2.id.first_name,R2.id.last_name,R2.id.address1,R2.id.district,R2.id.province})
    public void onFocusChange(View view){

        EditText editText = (EditText)view;
        int resId = editText.getId();

        String regex = validation.get(resId)[0];
        String errorMessage = validation.get(resId)[1];
        String text = editText.getText().toString();

        if(!text.matches(regex)) {

            editText.setError(errorMessage);
            registrationViewModel.setFormValid(false);
        }
    }
}