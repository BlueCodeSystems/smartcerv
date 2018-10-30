package zm.gov.moh.common.submodule.register.view;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.register.adapter.ClientListAdapter;
import zm.gov.moh.common.submodule.register.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        AndroidThreeTen.init(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        RecyclerView clientRecyclerView = findViewById(R.id.client_list);
        clientRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final ClientListAdapter clientListAdapter = new ClientListAdapter(this);
        clientRecyclerView.setAdapter(clientListAdapter);

        registerViewModel.getAllClients().observe(this, clients -> clientListAdapter.setClientList(clients));
    }
}
