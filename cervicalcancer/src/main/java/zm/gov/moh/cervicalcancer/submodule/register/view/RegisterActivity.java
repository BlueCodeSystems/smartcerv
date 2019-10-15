package zm.gov.moh.cervicalcancer.submodule.register.view;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.List;

import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.databinding.ActivityCervicalCancerRegisterBinding;
import zm.gov.moh.cervicalcancer.submodule.register.adapter.ClientListAdapter;
import zm.gov.moh.cervicalcancer.submodule.register.viewmodel.RegisterViewModel;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.base.BaseEventHandler;
import zm.gov.moh.common.ui.BaseRegisterActivity;


public class RegisterActivity extends BaseRegisterActivity {

    RegisterViewModel registerViewModel;
    ClientListAdapter clientListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCervicalCancerRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_cervical_cancer_register);
        AndroidThreeTen.init(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        BaseEventHandler toolBarEventHandler = getToolbarHandler(this);
        setViewModel(registerViewModel);
        toolBarEventHandler.setTitle("Client Register");

        binding.setToolbarhandler(toolBarEventHandler);
        binding.setSearchTermObserver(searchTermObserver);

        RecyclerView clientRecyclerView = findViewById(R.id.client_list);

        clientRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        clientListAdapter = new ClientListAdapter(this);

        clientRecyclerView.setAdapter(clientListAdapter);
        getAllClient();
    }

    @Override
    public void matchedSearchId(List<Long> ids) {
        registerViewModel.getMatchedClients(ids).observe(this, clientListAdapter::setClientList);
    }

    @Override
    public void getAllClient() {
        registerViewModel.getAllClients().observe(this, clientListAdapter::setClientList);
        setViewModel(registerViewModel);
        addDrawer(this);
    }
}