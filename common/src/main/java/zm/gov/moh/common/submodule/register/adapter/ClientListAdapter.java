package zm.gov.moh.common.submodule.register.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zm.gov.moh.common.databinding.ClientDemographicsBinding;
import zm.gov.moh.common.submodule.dashboard.client.view.ClientDashboardActivity;
import zm.gov.moh.common.submodule.register.view.RegisterActivity;
import zm.gov.moh.core.repository.database.entity.derived.Client;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Submodule;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientViewHolder> {

    private LayoutInflater mInflater;
    private List<Client> clientList;
    private BaseActivity context;
    private Submodule clientDashboad;
    private Bundle bundle;

    class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ClientDemographicsBinding binding;

        private ClientViewHolder(ClientDemographicsBinding binding){
            super(binding.getRoot());

            this.binding = binding;

            binding.getRoot().setOnClickListener(this);
        }

        public void bind(Client client){
            binding.setClient(client);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {

            Client client = clientList.get(getAdapterPosition());
            long clientId = client.patient_id;
            bundle.putLong(ClientDashboardActivity.PERSON_ID, clientId);

            Submodule call = (Submodule) context.getIntent().getSerializableExtra(RegisterActivity.START_SUBMODULE_WITH_RESULT_KEY);
           context.startSubmodule(call, bundle);
        }

    }

    public ClientListAdapter(Context context){

        mInflater = LayoutInflater.from(context);
        this.context = (BaseActivity) context;
        BaseApplication applicationContext = (BaseApplication)((BaseActivity) context).getApplication();
        clientDashboad = applicationContext.getSubmodule(BaseApplication.CoreSubmodules.CLIENT_DASHOARD);
        bundle = new Bundle();
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        ClientDemographicsBinding clientDemographicsBinding =
                ClientDemographicsBinding.inflate(layoutInflater, parent, false);

        return new ClientViewHolder(clientDemographicsBinding);
    }

    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position) {

        if(clientList != null){

            Client client = clientList.get(position);
            holder.bind(client);
        }
    }

    public void setClientList(List<Client> clients){

        clientList = clients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(clientList != null)
            return clientList.size();
        return 0;
    }
}