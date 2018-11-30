package zm.gov.moh.common.submodule.register.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.ActivityRegisterBinding;
import zm.gov.moh.common.submodule.register.adapter.ClientListAdapter;
import zm.gov.moh.common.submodule.register.viewmodel.RegisterViewModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Submodule;

public class RegisterActivity extends BaseActivity {

    public static final String START_SUBMODULE_WITH_RESULT_KEY = "START_SUBMODULE_WITH_RESULT_KEY";

    private RegisterViewModel registerViewModel;

    private Submodule defaultSubmodule;
    private Bundle bundle;


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

        final ClientListAdapter clientListAdapter = new ClientListAdapter(this);

        clientRecyclerView.setAdapter(clientListAdapter);


        //registerViewModel.getAllClients().observe(this, clientListAdapter::setClientList);

        registerViewModel.getAllClients().observe(this, clients -> {
            clientListAdapter.setClientList(clients);
        });
        binding.setToolbarhandler(toolBarEventHandler);
    }
}