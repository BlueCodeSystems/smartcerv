package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseLongArray;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.collection.LongSparseArray;
import androidx.databinding.DataBindingUtil;
import io.reactivex.Observable;
import zm.gov.moh.drugresistanttb.databinding.MdrObservationListItemBinding;
import zm.gov.moh.drugresistanttb.databinding.MdrVisitListItemBinding;
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

    private BaseActivity context;
    LinkedList<Long> filterConcepts;
    Map<Long, Long> substituteConcept;
    private LinkedList<LinkedHashMultimap<VisitListItem,VisitEncounterItem>> visitListItems ;
    private Bundle bundle;
    private Module formModule;

    public VisitExpandableListAdapter(Context context, LinkedList<LinkedHashMultimap<VisitListItem,VisitEncounterItem>> visitListItems) {

        this.context = (BaseActivity) context;
        this.visitListItems = visitListItems;
        this.bundle = ((BaseActivity) context).getIntent().getExtras();
        this.formModule = ((BaseApplication)((BaseActivity) context).getApplication()).getModule(BaseApplication.CoreModule.FORM);
    }

    public void setSubstituteConcept( Map<Long, Long>  substituteConcept) {
        this.substituteConcept = substituteConcept;
    }

    @Override
    public ObsListItem getChild(int groupPosition, int childPosition) {

        return getFilteredList(groupPosition).get(childPosition);
    }

    public ImmutableList<ObsListItem> getFilteredList(int groupPosition){

        Collection<VisitEncounterItem> visitEncounterItems = visitListItems.get(groupPosition).values();

        List<Long> finalFilteredConcepts = getFilterConcepts();

        LinkedList<ObsListItem> obsListItems = new LinkedList<>();

        for(VisitEncounterItem visitEncounterItem : visitEncounterItems)
            obsListItems.addAll(visitEncounterItem.getObsListItems());

        Iterator<ObsListItem> filtered = Observable.fromIterable(obsListItems)
                .filter(obsListItem -> getFilterConcepts().contains(obsListItem.getConceptId())).blockingIterable().iterator();


        List<Long> obsConceptId = Observable.fromIterable(obsListItems)
                .map(obs -> obs.getConceptId())
                .toList()
                .blockingGet();

        for(Map.Entry<Long,Long> entry: substituteConcept.entrySet())

            if(obsConceptId.contains(entry.getKey()) && obsConceptId.contains(entry.getValue())){


                final List<Long> filteredConcepts = Observable.fromIterable(finalFilteredConcepts)
                        .filter(conceptId -> (!conceptId.equals(entry.getKey())))
                        .toList().blockingGet();

                finalFilteredConcepts = filteredConcepts;

                filtered = Observable.fromIterable(obsListItems)
                        .filter(obsListItem -> filteredConcepts.contains(obsListItem.getConceptId())).blockingIterable().iterator();

            }

        return ImmutableList.copyOf(filtered);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {

        ObsListItem obsListItem = getChild(groupPosition, childPosition);

        MdrObservationListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.mdr_observation_list_item,parent,false);

        binding.setObsListItem(obsListItem);

        return binding.getRoot();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return getFilteredList(groupPosition).size();
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

        MdrVisitListItemBinding binding;


        if(view == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.mdr_visit_list_item,parent,false);
            view = binding.getRoot();
        }
        else {
            binding = (MdrVisitListItemBinding)view.getTag();
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
                        bundle.putSerializable(Key.VISIT_STATE, VisitState.AMEND);
                        ((BaseActivity)context).startModule(BaseApplication.CoreModule.VISIT, bundle);
                    }catch (Exception e){

                    }
                }
                return false;
            }
        };
    }
}
