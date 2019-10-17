package zm.gov.moh.common.submodule.register.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.ActivityRegisterBinding;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.common.submodule.register.adapter.ClientListAdapter;
import zm.gov.moh.common.submodule.register.model.SearchTermObserver;
import zm.gov.moh.common.submodule.register.viewmodel.RegisterViewModel;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.base.BaseEventHandler;
import zm.gov.moh.common.ui.BaseRegisterActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.utils.Utils;

public class RegisterActivity extends BaseRegisterActivity {

    public static final String START_SUBMODULE_WITH_RESULT_KEY = "START_SUBMODULE_WITH_RESULT_KEY";

    private RegisterViewModel registerViewModel;

    private Module defaultModule;
    private Bundle mBundle;
    ClientListAdapter clientListAdapter;
    private List<Client> allClients;
    protected long locationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        binding.setContext(this);
        mBundle = getIntent().getExtras();
        binding.setTitle("Client Register");


        defaultModule = ((BaseApplication)this.getApplication()).getModule(BaseApplication.CoreModule.CLIENT_DASHOARD);

        AndroidThreeTen.init(this);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        setViewModel(registerViewModel);


        if(mBundle != null){

            try {
                getIntent().getExtras().getSerializable(START_SUBMODULE_WITH_RESULT_KEY);
            }catch (Exception e){
                getIntent().getExtras().putSerializable(START_SUBMODULE_WITH_RESULT_KEY, defaultModule);
            }
        }
        else {

            mBundle = new Bundle();
            mBundle.putSerializable(START_SUBMODULE_WITH_RESULT_KEY, defaultModule);
            getIntent().putExtras(mBundle);
        }

        RecyclerView clientRecyclerView = findViewById(R.id.client_list);

        clientRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        clientListAdapter = new ClientListAdapter(this);

        clientRecyclerView.setAdapter(clientListAdapter);


        locationId = registerViewModel.getRepository().getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0L);

        binding.setSearchTermObserver(searchTermObserver);
        binding.setContext(this);

        setViewModel(registerViewModel);
        addDrawer(this);
        getAllClient();

    }

    @Override
    public void matchedSearchId(List<Long> ids) {
        registerViewModel.getRepository().getDatabase().clientDao().findById(ids).observe(this, clientListAdapter::setClientList);
    }

    @Override
    public void getAllClient() {

        registerViewModel.getRepository().getDatabase().clientDao().findClientsByLocation(locationId).observe(this, clients -> {
            clientListAdapter.setClientList(clients);
            this.allClients = clients;
        });
    }

    public void registerClient(){

        try {

            JsonForm clientRegistration = new JsonForm("Client Registration",
                    Utils.getStringFromInputStream(this.getAssets().open("forms/client_registration.json")));


            mBundle.putSerializable(Key.JSON_FORM, clientRegistration);
            startModule(BaseApplication.CoreModule.FORM, mBundle);

        }catch (Exception e){

        }
    }
}
