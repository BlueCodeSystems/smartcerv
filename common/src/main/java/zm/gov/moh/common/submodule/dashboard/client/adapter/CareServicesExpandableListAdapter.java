package zm.gov.moh.common.submodule.dashboard.client.adapter;

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

import zm.gov.moh.common.R;
import zm.gov.moh.core.model.Criteria;
import zm.gov.moh.core.model.submodule.BasicSubmoduleGroup;
import zm.gov.moh.core.model.submodule.CriteriaSubmodule;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.model.submodule.SubmoduleGroup;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.utils.BaseApplication;

public class CareServicesExpandableListAdapter extends BaseExpandableListAdapter {

    private BaseActivity context;
    private List<SubmoduleGroup> submoduleGroups;
    private Bundle bundle;
    private Submodule formSubmodule;

    public CareServicesExpandableListAdapter(Context context, List<SubmoduleGroup> submoduleGroups) {

        this.context = (BaseActivity) context;
        this.submoduleGroups = submoduleGroups;
        this.bundle = ((BaseActivity) context).getIntent().getExtras();
        this.formSubmodule = ((BaseApplication)((BaseActivity) context).getApplication()).getSubmodule(BaseApplication.CoreSubmodules.FORM);
    }

    @Override
    public Submodule getChild(int groupPosition, int childPosition) {

        List<Submodule> submodules = submoduleGroups.get(groupPosition).getSubmodules();

        return submodules.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

        Submodule submodule = (Submodule) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.submodule_group_child_item, null);
        }

        TextView sequence = (TextView) view.findViewById(R.id.submodule_group_child_item_title);
        sequence.setText(submodule.getName());

        view.setOnClickListener(view1 -> context.startSubmodule(formSubmodule,bundle));

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        List<Submodule> submodules = submoduleGroups.get(groupPosition).getSubmodules();
        return submodules.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return submoduleGroups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return submoduleGroups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {

        CriteriaSubmodule submoduleGroup = (BasicSubmoduleGroup) getGroup(groupPosition);

        Map<String,String> gender = new HashMap<>();

        gender.put("gender","Female");

        Criteria criteria = new Criteria(gender);

        submoduleGroup.setCriteria(criteria);

        if (view == null) {
            LayoutInflater inf = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.submodule_group_item, null);
        }

        TextView heading = (TextView) view.findViewById(R.id.submodule_group_item_title);
        heading.setText(submoduleGroup.getName());



        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
