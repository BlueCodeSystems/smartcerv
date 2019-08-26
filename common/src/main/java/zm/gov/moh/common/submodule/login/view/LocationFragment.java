package zm.gov.moh.common.submodule.login.view;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import zm.gov.moh.common.R;
import zm.gov.moh.common.databinding.FragmentLoginCredentialsBinding;
import zm.gov.moh.common.databinding.FragmentLoginLocationBinding;
import zm.gov.moh.common.submodule.login.adapter.LocationArrayAdapter;
import zm.gov.moh.common.submodule.login.viewmodel.LoginViewModel;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.domain.Location;

public class LocationFragment extends Fragment  implements AdapterView.OnItemSelectedListener {
    private LocationArrayAdapter locationArrayAdapter;
    private AppCompatSpinner locationSpinner;
    LoginViewModel viewModel;
    private List<Location> locations;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = (LoginViewModel)((LoginActivity) getActivity()).getViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentLoginLocationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_location,container,false);
        binding.setViewmodel(viewModel);

        locations=new ArrayList<>();
        locations=Arrays.asList(viewModel.getProviderLocations());
        locationSpinner = binding.getRoot().findViewById(R.id.locations);

        locationArrayAdapter = new LocationArrayAdapter(getContext(),locations);
        locationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationArrayAdapter);
        locationSpinner.setOnItemSelectedListener(this);

        setLastLocation();

        return binding.getRoot();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

         viewModel.getViewBindings()
                 .setSelectedLocationId(locationArrayAdapter.getItem(i).getLocationId());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }

   public  void setLastLocation()
   {



       long lastLocationId=viewModel.getRepository().getDefaultSharePrefrences().getLong(Key.LAST_LOCATION,0);

      if(lastLocationId != 0) {
          for (int i = 0; i < locationSpinner.getCount(); i++) {
              if (locationArrayAdapter.getItem(i).getLocationId() == lastLocationId) {
                  locationSpinner.setSelection(i);
              }
          }
      }else
      {
          return;
      }



   }



}
