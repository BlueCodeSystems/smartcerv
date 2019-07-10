package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter;

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
import zm.gov.moh.cervicalcancer.databinding.ObservationListItemBinding;
import zm.gov.moh.cervicalcancer.databinding.VisitListItemBinding;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.ObsListItem;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.VisitEncounterItem;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.VisitListItem;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.common.model.VisitMetadata;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Utils;

public class VisitExpandableListAdapter extends BaseExpandableListAdapter {

    private BaseActivity context;
    LinkedList<Long> filterConcepts;
    private LinkedList<LinkedHashMultimap<VisitListItem,VisitEncounterItem>> visitListItems ;
    private Bundle bundle;
    private Module formModule;

    public VisitExpandableListAdapter(Context context, LinkedList<LinkedHashMultimap<VisitListItem,VisitEncounterItem>> visitListItems) {

        this.context = (BaseActivity) context;
        this.visitListItems = visitListItems;
        this.bundle = ((BaseActivity) context).getIntent().getExtras();
        this.formModule = ((BaseApplication)((BaseActivity) context).getApplication()).getModule(BaseApplication.CoreModule.FORM);
    }

    @Override
    public ObsListItem getChild(int groupPosition, int childPosition) {

        Collection<VisitEncounterItem> visitEncounterItems = visitListItems.get(groupPosition).values();

        LinkedList<ObsListItem> obsListItems = new LinkedList<>();

        for(VisitEncounterItem visitEncounterItem : visitEncounterItems)
            obsListItems.addAll(visitEncounterItem.getObsListItems());

        Iterator<ObsListItem> filtered = Observable.fromIterable(obsListItems)
                .filter(obsListItem -> getFilterConcepts().contains(obsListItem.getConceptId())).blockingIterable().iterator();

        return ImmutableList.copyOf(filtered).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

        ObsListItem obsListItem = getChild(groupPosition, childPosition);

        ObservationListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.observation_list_item,parent,false);

        binding.setObsListItem(obsListItem);

        return binding.getRoot();
    }

    @Override
    public int getChildrenCount(int groupPosition) {


        Collection<VisitEncounterItem> visitEncounterItems = visitListItems.get(groupPosition).values();

        LinkedList<ObsListItem> obsListItems = new LinkedList<>();

        for(VisitEncounterItem visitEncounterItem : visitEncounterItems)
            obsListItems.addAll(visitEncounterItem.getObsListItems());

        Iterator<ObsListItem> filtered = Observable.fromIterable(obsListItems)
                .filter(obsListItem -> getFilterConcepts().contains(obsListItem.getConceptId())).blockingIterable().iterator();

       return ImmutableList.copyOf(filtered).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return visitListItems.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return visitListItems.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {

        VisitListItem visitListItem = ((LinkedHashMultimap<VisitListItem,VisitEncounterItem>)
                                      getGroup(groupPosition)).keys().iterator().next();

        VisitListItemBinding binding;


        if(view == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.visit_list_item,parent,false);
            view = binding.getRoot();
        }
        else {
            binding = (VisitListItemBinding)view.getTag();
        }
        binding.setVisitListItem(visitListItem);
        view.setTag(binding);

        ImageView overFlowMenu = view.findViewById(R.id.visit_overflow_icon);

        PopupMenu popupMenu = new PopupMenu(context,overFlowMenu);

        popupMenu.inflate(R.menu.visit_menu);

        popupMenu.setOnMenuItemClickListener(this.assignListener(visitListItem.getId()));

        overFlowMenu.setOnClickListener(view1 -> {popupMenu.show();});

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

    public void addFilterConceptId(List<Long> conceptIds){

        for(long conceptId:conceptIds)
            getFilterConcepts().add(conceptId);
    }

    public LinkedList<Long> getFilterConcepts() {

        if(filterConcepts == null)
            filterConcepts = new LinkedList<>();

        return filterConcepts;
    }

    public PopupMenu.OnMenuItemClickListener assignListener (final long visitId){


        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.visit_edit){

                    try {
                        VisitMetadata visitMetadata = new VisitMetadata(context, Utils.getStringFromInputStream(context.getAssets().open("visits/via.json")));

                        bundle.putSerializable(Key.VISIT_METADATA, visitMetadata);
                        bundle.putLong(Key.VISIT_ID, visitId);
                        ((BaseActivity)context).startModule(BaseApplication.CoreModule.VISIT, bundle);
                    }catch (Exception e){

                    }
                }
                return false;
            }
        };
    }

}
