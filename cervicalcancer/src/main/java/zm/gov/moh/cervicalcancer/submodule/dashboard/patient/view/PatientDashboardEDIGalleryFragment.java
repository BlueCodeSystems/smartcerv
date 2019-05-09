package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.RequestOptions;
import com.google.common.collect.LinkedHashMultimap;

import org.threeten.bp.Instant;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.common.ui.BaseActivity;

public class PatientDashboardEDIGalleryFragment extends Fragment {



    private BaseActivity context;
    RecyclerView recyclerView;
    private TextView visitDate;
    private View rootView;


    public PatientDashboardEDIGalleryFragment() {
        // Required empty public constructor


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (BaseActivity) getContext();
        rootView = inflater.inflate(R.layout.fragment_patient_dashoard_edi, container, false);
        //context = (PatientDashboardActivity) getContext();
        //context.getClientId();
        // Inflate the layout for this fragment

        //FragmentClientDashboardVitalsBinding binding = DataBindingUtil.inflate(context.getLayoutInflater(), R.layout.fragment_client_dashboard_vitals, container, false);
        // View view = binding.getRoot();

        visitDate = rootView.findViewById(R.id.visit_date);

        ((PatientDashboardViewModel) context.getViewModel()).getEDIDataEmitter().observe(context, this::populateEDIRole);
        return rootView;
        }

        private void populateEDIRole(LinkedHashMultimap<Long, String> ediData) {
            for (long visitEpochSeconds : ediData.keySet()) {

                Instant dateTime = Instant.ofEpochSecond(visitEpochSeconds);
                visitDate.setText(dateTime.toString());
                ArrayList<String> uris = new ArrayList<>(ediData.get(visitEpochSeconds));
                ImageDataAdapter adapter = new ImageDataAdapter(context,uris);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);

                recyclerView = rootView.findViewById(R.id.recyclerview1);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(adapter);


            }
        }

    public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.ViewHolder> {
        private ArrayList<String> uris;
        private Context context;


        public ImageDataAdapter(Context context, ArrayList<String> imageUris) {
            this.context = context;
            this.uris = imageUris;
        }

        @Override
        public ImageDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout, parent, false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int viewType) {
            GridView gridView = viewHolder.img;
            //String fileName = "IMG_20190429_154929.jpg";
            //String completePath = Environment.getExternalStorageDirectory() + "/storage/emulated/0/DCIM/" + fileName;
            //File file = new File(completePath);
            //Uri imageUri = Uri.fromFile(file);

            //Glide.with(context).load(file).transform(new Transform()).into(imageView);
            //Uri.fromFile(new File(completePath));

            //Glide.with(context)
                    //.load(Uri.fromFile(new File(completePath)))
                    //.apply(new RequestOptions().override(100, 100))
                    //.into(imageView);

            //Glide.with(context)
                    //.load(fileName)
                    //.into(imageView);
            //String completePath = Environment.getExternalStorageDirectory() + "/storage/emulated/0/DCIM/Camera";
            //String uri = uris.get(i);
            //String path = Uri.decode(uri.substring(uri.lastIndexOf('/')));
            //File file = new File(completePath);
           // Uri imageUris = Uri.fromFile(file);


            //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "IMG_20190408_142022.JPG");

            //Glide
                    //.with(context)
                    //.load(uris.get(viewType))
                    //.into(gridView);

                    //.load(new File(completePath+path))


        }

        @Override
        public int getItemCount() {

            return uris.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            GridView img;

            public ViewHolder(View view) {
                super(view);
                img = view.findViewById(R.id.iv);
            }
        }

        /*private class Transform implements com.bumptech.glide.load.Transformation<Bitmap> {
            @NonNull
            @Override
            public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
                return null;
            }

            @Override
            public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

            }*/
        }
    }



