package zm.gov.moh.cervicalcancer.submodule.register.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.register.adapter.ClientListAdapter;
import zm.gov.moh.cervicalcancer.submodule.register.viewmodel.RegisterViewModel;
import zm.gov.moh.core.utils.BaseActivity;

public class RegisterActivity extends BaseActivity {

    RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        AndroidThreeTen.init(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);


        /*if(bundle != null){

            try {
                getIntent().getExtras().getSerializable(START_SUBMODULE_WITH_RESULT_KEY);
            }catch (Exception e){
                getIntent().getExtras().putSerializable(START_SUBMODULE_WITH_RESULT_KEY, defaultSubmodule);
            }
        }
        else {

            bundle = new Bundle();
            bundle.putSerializable(START_SUBMODULE_WITH_RESULT_KEY, defaultSubmodule);
            getIntent().putExtras(bundle);
        }*/


        RecyclerView clientRecyclerView = findViewById(R.id.client_list);

        clientRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final ClientListAdapter clientListAdapter = new ClientListAdapter(this);

        clientRecyclerView.setAdapter(clientListAdapter);

        registerViewModel.getAllClients().observe(this, clientListAdapter::setClientList);
    }
}