package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import zm.gov.moh.common.R;
import zm.gov.moh.common.model.JsonForm;
import zm.gov.moh.common.model.FormJsonGroup;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.utils.BaseApplication;

public class FormJsonGroupExpandableListAdapter extends BaseExpandableListAdapter {

    private BaseActivity context;
    private List<FormJsonGroup> formJsonGroups;
    private Bundle bundle;
    private Module formModule;

    public FormJsonGroupExpandableListAdapter(Context context, List<FormJsonGroup> formJsonGroups) {

        this.context = (BaseActivity) context;
        this.formJsonGroups = formJsonGroups;
        this.bundle = ((BaseActivity) context).getIntent().getExtras();
        this.formModule = ((BaseApplication)((BaseActivity) context).getApplication()).getModule(BaseApplication.CoreModule.FORM);
    }

    @Override
    public JsonForm getChild(int groupPosition, int childPosition) {

        List<JsonForm> formJsons = formJsonGroups.get(groupPosition).getFormJsons();

        return formJsons.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        JsonForm formJson = getChild(groupPosition, childPosition);

        if (view == null) {

            LayoutInflater infalInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.submodule_group_child_item, null);
        }

        TextView sequence = view.findViewById(R.id.submodule_group_child_item_title);
        sequence.setText(formJson.getName());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        List<JsonForm> formJsons = formJsonGroups.get(groupPosition).getFormJsons();
        return formJsons.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return formJsonGroups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return formJsonGroups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {

        FormJsonGroup formJsonGroup = (FormJsonGroup) getGroup(groupPosition);

        if (view == null) {
            LayoutInflater inf = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.submodule_group_item, null);
        }

        TextView heading = view.findViewById(R.id.submodule_group_item_title);
        heading.setText(formJsonGroup.getName());

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
