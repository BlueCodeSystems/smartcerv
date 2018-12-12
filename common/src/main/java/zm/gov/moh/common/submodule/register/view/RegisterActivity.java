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
import zm.gov.moh.common.submodule.register.adapter.ClientListAdapter;
import zm.gov.moh.common.submodule.register.model.SearchTermObserver;
import zm.gov.moh.common.submodule.register.viewmodel.RegisterViewModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Submodule;

public class RegisterActivity extends BaseActivity {

    public static final String START_SUBMODULE_WITH_RESULT_KEY = "START_SUBMODULE_WITH_RESULT_KEY";

    private RegisterViewModel registerViewModel;

    private Submodule defaultSubmodule;
    private Bundle bundle;
    ClientListAdapter clientListAdapter;
    private List<Client> allClients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        bundle = getIntent().getExtras();
        ToolBarEventHandler toolBarEventHandler = getToolbarHandler();
        toolBarEventHandler.setTitle("Client Register");


        defaultSubmodule = ((BaseApplication)this.getApplication()).getSubmodule(BaseApplication.CoreSubmodules.CLIENT_DASHOARD);

        AndroidThreeTen.init(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);


        if(bundle != null){

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
        }

        RecyclerView clientRecyclerView = findViewById(R.id.client_list);

        clientRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        clientListAdapter = new ClientListAdapter(this);

        clientRecyclerView.setAdapter(clientListAdapter);


        //registerViewModel.getAllClients().observe(this, clientListAdapter::setClientList);

        registerViewModel.getRepository().getDatabase().clientDao().findAllClients().observe(this, clients -> {
            clientListAdapter.setClientList(clients);
            this.allClients = clients;
        });

        SearchTermObserver searchTermObserver = new SearchTermObserver(this::searchCallback);

        binding.setToolbarhandler(toolBarEventHandler);
        binding.setSearch(searchTermObserver);
    }

    public void searchCallback(String term){

        if(!(term.equals("") || term == null))
            registerViewModel.getRepository().getDatabase().clientFtsDao().findClientByTerm(term).observe(this, ids -> {
                registerViewModel.getRepository().getDatabase().clientDao().findById(ids).observe(this, clientListAdapter::setClientList);
            });
        else
            clientListAdapter.setClientList(allClients);
    }
}