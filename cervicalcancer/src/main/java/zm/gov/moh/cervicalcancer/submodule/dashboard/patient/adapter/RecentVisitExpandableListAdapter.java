package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import zm.gov.moh.cervicalcancer.R;

public class RecentVisitExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> formListTitle;     // Form title
    private HashMap<String, List<String>>patientVistDetailForm; // details of each form when expanded

    LinearLayout tableLayout;
    Resources resources;

    public RecentVisitExpandableListAdapter(Context context, List<String> formListTitle,
                HashMap<String, List<String>> patientVistDetailForm) {
        this.context = context;
        this.formListTitle = formListTitle;
        this.patientVistDetailForm = patientVistDetailForm;
    }

    @Override
    public int getGroupCount() {
        return this.formListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.patientVistDetailForm.get(this.formListTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.formListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.patientVistDetailForm.get(this.formListTitle.get(groupPosition)).get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String formTitle = (String) getGroup(groupPosition);

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(zm.gov.moh.common.R.layout.submodule_form_group,
                    null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.submodule_form_title);

        listTitleTextView.setText(formTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
        View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.submodule_form_item, null);
        }


        tableLayout = convertView.findViewById(R.id.recent_visits_table);
        //add table row and textviews
        populateRecentVisitObservations(convertView, groupPosition, childPosition);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    void populateRecentVisitObservations(View view, int groupPosition, int childPosition){
        resources = context.getResources();
        final String formListContents = (String) getChild(groupPosition, childPosition);

        TableRow tableRow = new TableRow(context);
        TextView textView1 = new TextView(context);
        TextView textView2 = new TextView(context);

        textView1.setId(R.id.submodule_form_child_title);   // id set in resource file id.xml
        textView1.setText(formListContents);
        textView2.setText("TextView2 - Table 1");
        textView1.setGravity(Gravity.CENTER_HORIZONTAL);
        textView2.setGravity(Gravity.CENTER_HORIZONTAL);
        textView1.setBackground(resources.getDrawable(R.drawable.border_right));
        textView2.setBackground(resources.getDrawable(R.drawable.border_right));

        tableRow.setPadding(0, 0, 0, 0);
        tableRow.addView(textView1);
        tableRow.addView(textView2);
        tableLayout.removeAllViews();
        tableLayout.addView(tableRow);
    }
}
