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
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.FormListItemBinding;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.common.model.VisitMetadata;
import zm.gov.moh.common.submodule.form.model.FormModel;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;

public class FormListAdapter extends RecyclerView.Adapter<FormListAdapter.FormViewHolder>{

    private List<FormModel> jsonFormList;
    private List<FormModel> jsonFormListTemp;
    private BaseActivity mContext;
    private Bundle mBundle;
    private boolean isClickable = false;


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

    public void setClickable(boolean clickable) {
        this.isClickable = clickable;
        notifyDataSetChanged();
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
            mBinding.getRoot().findViewById(R.id.form_name).setEnabled(isClickable);
        }

        @Override
        public void onClick(View view) {

            if(isClickable != true)
                return;

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
