package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedHashMultimap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import io.reactivex.Observable;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model.ObsListItem;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model.VisitEncounterItem;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model.VisitListItem;
import zm.gov.moh.drugresistanttb.R;
import zm.gov.moh.common.model.VisitMetadata;
import zm.gov.moh.core.model.VisitState;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

public class VisitExpandableListAdapter extends BaseExpandableListAdapter {
    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

