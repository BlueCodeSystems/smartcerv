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

import androidx.fragment.app.FragmentManager;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.model.Criteria;
import zm.gov.moh.core.model.submodule.BasicModuleGroup;
import zm.gov.moh.core.model.submodule.CriteriaModule;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.model.submodule.ModuleGroup;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.drugresistanttb.R;


public class MdrFormListAdapter extends BaseExpandableListAdapter {

    private BaseActivity context;
    private List<ModuleGroup> submoduleGroups;
    private Bundle bundle;
    private Module formModule;

    public MdrFormListAdapter(Context context, List<ModuleGroup> submoduleGroups, Bundle bundle) {

        this.context = (BaseActivity) context;
        this.submoduleGroups = submoduleGroups;
        this.bundle = bundle;
        this.formModule = ((BaseApplication) ((BaseActivity) context).getApplication()).getModule(BaseApplication.CoreModule.FORM);
    }



    @Override
    public int getGroupCount() {
        return submoduleGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        List<Module> modules = submoduleGroups.get(groupPosition).getModules();
        return modules.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return submoduleGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        List<Module> modules = submoduleGroups.get(groupPosition).getModules();

        return modules.get(childPosition);
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

        CriteriaModule submoduleGroup = (BasicModuleGroup) getGroup(groupPosition);

        Map<String,String> gender = new HashMap<>();

        gender.put("gender","Female");

        Criteria criteria = new Criteria(gender);

        submoduleGroup.setCriteria(criteria);

        if (view == null) {
            LayoutInflater inf = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.submodule_group_item, null);
        }

        TextView heading = view.findViewById(zm.gov.moh.common.R.id.submodule_group_item_title);
        heading.setText(submoduleGroup.getName());



        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

        Module module = (Module) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.mdr_submodule_group_child_item, null);
        }

        TextView sequence = view.findViewById(R.id.mdr_submodule_group_child_item_title);
        sequence.setText(module.getName());

        view.setOnClickListener(view1 ->{
            context.startModule(module, bundle);
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
