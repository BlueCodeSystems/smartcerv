package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.model.Criteria;
import zm.gov.moh.core.model.submodule.BasicModule;
import zm.gov.moh.core.model.submodule.BasicModuleGroup;
import zm.gov.moh.core.model.submodule.CriteriaModule;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.model.submodule.ModuleGroup;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model.FormGroup;


public class MdrFormListAdapter extends BaseExpandableListAdapter {

    private BaseActivity context;
    private List<FormGroup> mdrFormLists;
    private Bundle bundle;
    private Module formModule;

    public MdrFormListAdapter(Context context, List<FormGroup> mdrFormLists, Bundle bundle) {

        this.context = (BaseActivity) context;
        this.mdrFormLists = mdrFormLists;
        this.bundle = bundle;
        this.formModule = ((BaseApplication) ((BaseActivity) context).getApplication()).getModule(BaseApplication.CoreModule.FORM);
    }



    @Override
    public int getGroupCount() {
        return mdrFormLists.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        List<BasicModule> forms = mdrFormLists.get(groupPosition).getFormList();
        return forms.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mdrFormLists.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        List<BasicModule> forms = mdrFormLists.get(groupPosition).getFormList();

        return forms.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {

        FormGroup formGroup = (FormGroup) getGroup(groupPosition);

        if (view == null) {
            LayoutInflater inf = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.submodule_group_item, null);
        }

        TextView heading = view.findViewById(zm.gov.moh.common.R.id.submodule_group_item_title);
        heading.setText(formGroup.getTitle());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

        BasicModule forms = (BasicModule) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.mdr_submodule_group_child_item, null);
        }

        TextView sequence = view.findViewById(R.id.mdr_submodule_group_child_item_title);
        sequence.setText(forms.getName());

        view.setOnClickListener(view1 ->{
            context.startModule(forms, bundle);
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
