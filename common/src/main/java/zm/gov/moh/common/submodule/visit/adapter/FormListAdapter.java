package zm.gov.moh.common.submodule.visit.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.common.databinding.FormListItemBinding;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.common.model.VisitMetadata;
import zm.gov.moh.common.submodule.form.model.FormModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;

public class FormListAdapter extends RecyclerView.Adapter<FormListAdapter.FormViewHolder>{

    private List<FormModel> jsonFormList;
    private BaseActivity mContext;
    private Bundle mBundle;


    public FormListAdapter(@NonNull BaseActivity context, @NonNull Bundle bundle){
       mContext = context;
       mBundle = bundle;


        VisitMetadata visitMetadata = (VisitMetadata)mBundle.getSerializable(Key.VISIT_METADATA);
       jsonFormList = visitMetadata.getFormModels();
    }

    public void setJsonFormList(LinkedList<FormModel> jsonFormList) {
        this.jsonFormList = jsonFormList;
    }

    @Override
    public int getItemCount() {
        return jsonFormList.size();
    }

    @Override
    public void onBindViewHolder(FormViewHolder holder, int position) {

        if(jsonFormList != null){

            FormModel client = jsonFormList.get(position);
            holder.bind(client);
        }
    }

    @NonNull
    @Override
    public FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        FormListItemBinding formListItemBinding = FormListItemBinding.inflate(layoutInflater, parent, false);

        return new FormViewHolder(formListItemBinding);
    }

    class FormViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private FormListItemBinding mBinding;

        private FormViewHolder(FormListItemBinding binding){
            super(binding.getRoot());

            mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            FormModel formModel = jsonFormList.get(getAdapterPosition());
            mBundle.putSerializable(Key.FORM_MODEL, formModel);
            String module = mBundle.getString(Key.MODULE);
            mContext.startModule(module, mBundle);
        }

        public void bind(FormModel formModel){
            mBinding.setForm(formModel);
            mBinding.executePendingBindings();
        }

    }

}


/*public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientViewHolder> {

    private LayoutInflater mInflater;
    private List<Client> clientList;
    private BaseActivity context;
    private Module clientDashboad;
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
            long clientId = client.getPatientId();
            bundle.putLong(ClientDashboardActivity.PERSON_ID, clientId);

            Module call = (Module) context.getIntent().getSerializableExtra(RegisterActivity.START_SUBMODULE_WITH_RESULT_KEY);
            context.startModule(call, bundle);
        }

    }

    public ClientListAdapter(Context context){

        mInflater = LayoutInflater.from(context);
        this.context = (BaseActivity) context;
        BaseApplication applicationContext = (BaseApplication)((BaseActivity) context).getApplication();
        clientDashboad = applicationContext.getModule(BaseApplication.CoreModule.CLIENT_DASHOARD);
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
}*/
