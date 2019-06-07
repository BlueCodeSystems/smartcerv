package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.common.collect.LinkedHashMultimap;

import org.threeten.bp.Instant;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;


import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.cervicalcancer.BuildConfig;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.common.submodule.form.utils.MediaStorageUtil;
import zm.gov.moh.common.submodule.form.widget.FormCameraButtonWidget;
import zm.gov.moh.common.ui.BaseActivity;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

public class PatientDashboardEDIGalleryFragment<MainActivity> extends Fragment {


    private final Object PatientDashboardEDIGalleryFragment = this;
    private BaseActivity context;
    RecyclerView recyclerView;
    private TextView visitDate;
    //private TextView visitType;
    private View rootView;
    private FormCameraButtonWidget mContext;
    private SharedPreferences.Editor mBundle;
    private Uri uri;
    private String type;
    private File filename;
    private Locale mediaFile;
    private File mImsgeFileName;


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
        //visitType = rootView.findViewWById(R.id.visit_type);

        ((PatientDashboardViewModel) context.getViewModel()).getEDIDataEmitter().observe(context, this::populateEDIRole);
        return rootView;
    }



    private void populateEDIRole(LinkedHashMultimap<Long, String> ediData) {
        for (long visitEpochSeconds : ediData.keySet()) {

            Instant dateTime = Instant.ofEpochSecond(visitEpochSeconds);
            visitDate.setText(dateTime.toString());
            ArrayList<String> uris = new ArrayList<>(ediData.get(visitEpochSeconds));
            //visitType.setText(visitType.toString());

            ImageDataAdapter adapter = new ImageDataAdapter(context,uris);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);

            recyclerView = rootView.findViewById(R.id.recyclerview1);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);




        }
    }

    public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.ViewHolder> {
        private ArrayList<String> samples;
        private Context context;
        private FormCameraButtonWidget ediPrint;


        public ImageDataAdapter(Context context, ArrayList<String> imageUris) {
            this.context = context;
            this.samples = imageUris;
        }

        @Override
        public ImageDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout, parent, false);
            return new ViewHolder(view);

            




        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            AppCompatImageView imageView = viewHolder.img;
            String res = samples.get(i);
            File image = MediaStorageUtil.getPrivateAlbumStorageDir(context, MediaStorageUtil.EDI_DIRECTORY);


            /*imageView.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 try {
                                                     startActivity(new Intent(Intent.ACTION_VIEW,
                                                             Uri.parse(image.getCanonicalPath()+"/"+res+".png")));
                                                 } catch (IOException e) {
                                                     e.printStackTrace();
                                                 }

                                             }
                                         });**/

            imageView.setOnClickListener(new View.OnClickListener() {
                //private boolean isImageFitToScreen;

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivityForResult(Intent.createChooser(intent,
                            "Select Picture"), 1);
                    /*Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()+"")), "image/*");
                    context.startActivity(intent);*/
                }
            });


            try {


                Glide
                        .with(context)
                        .asBitmap()
                        .load(image.getCanonicalPath()+"/"+res+".png")

                        //.override(1600, 1300)
                        //.fitCenter()
                        .into(imageView);
            }catch (Exception e){
                Exception ex = e;


                
            }






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
            //String uri = samples.get(i);
            //String path = Uri.decode(uri.substring(uri.lastIndexOf('/')));
            //File file = new File(completePath);
            // Uri imageUris = Uri.fromFile(file);




            //.load(new File(completePath+path))


        }

        @Override
        public int getItemCount() {

            return samples.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatImageView img;
            ProgressBar progressBar;

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

    private PackageManager getPackageName() {
        return mContext.getPackageManager();
    }

    public PackageManager getPackageManager() {
        return mContext.getPackageManager();
    }
}
