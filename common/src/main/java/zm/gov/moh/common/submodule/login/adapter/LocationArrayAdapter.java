package zm.gov.moh.common.submodule.login.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import java.util.List;

import zm.gov.moh.core.repository.database.entity.domain.Location;

public class LocationArrayAdapter extends ArrayAdapter<Location> {

        private LayoutInflater mInflater;

        public LocationArrayAdapter(Context context, List<Location> locations){
            super(context, 0, locations);

            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            return createItemView(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            return createItemView(position, convertView, parent);
        }

        private View createItemView(int postion, View convertView, ViewGroup parent){

            final View view = mInflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

            CheckedTextView checkedTextView = (CheckedTextView) view.getRootView();

            Location location = super.getItem(postion);

            checkedTextView.setText(location.name);

            return view;
        }
    }