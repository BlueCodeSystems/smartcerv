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
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.common.ui.ToolBarEventHandler;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.utils.Utils;

public class RegisterActivity extends BaseActivity {

    public static final String START_SUBMODULE_WITH_RESULT_KEY = "START_SUBMODULE_WITH_RESULT_KEY";

    private RegisterViewModel registerViewModel;

    private Module defaultModule;
    private Bundle mBundle;
    ClientListAdapter clientListAdapter;
    private List<Client> allClients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        binding.setContext(this);
        mBundle = getIntent().getExtras();
        ToolBarEventHandler toolBarEventHandler = getToolbarHandler(this);
        toolBarEventHandler.setTitle("Client Register");


        defaultModule = ((BaseApplication)this.getApplication()).getModule(BaseApplication.CoreModule.CLIENT_DASHOARD);

        AndroidThreeTen.init(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);


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


        long locationId = registerViewModel.getRepository().getDefaultSharePrefrences().getLong(this.getResources().getString(zm.gov.moh.core.R.string.session_location_key),0L);

        registerViewModel.getRepository().getDatabase().clientDao().findClientsByLocation(locationId).observe(this, clients -> {
            clientListAdapter.setClientList(clients);
            this.allClients = clients;
        });

        SearchTermObserver searchTermObserver = new SearchTermObserver(this::searchCallback);

        binding.setToolbarhandler(toolBarEventHandler);
        binding.setSearch(searchTermObserver);
        binding.setContext(this);
    }

    public void searchCallback(String term){

        if(!(term.equals("") || term == null))
            registerViewModel.getRepository().getDatabase().clientFtsDao().findClientByTerm(term).observe(this, ids -> {
                registerViewModel.getRepository().getDatabase().clientDao().findById(ids).observe(this, clientListAdapter::setClientList);
            });
        else
            clientListAdapter.setClientList(allClients);
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
