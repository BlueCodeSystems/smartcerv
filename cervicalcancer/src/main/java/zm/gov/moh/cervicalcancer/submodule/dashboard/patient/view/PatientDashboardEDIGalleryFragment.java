package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.common.collect.LinkedHashMultimap;

import org.threeten.bp.Instant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.IRecyclerViewClickListener;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.common.submodule.form.utils.MediaStorageUtil;
import zm.gov.moh.common.submodule.form.widget.FormCameraButtonWidget;
import zm.gov.moh.common.ui.BaseActivity;
import zm.gov.moh.core.model.Key;

public class PatientDashboardEDIGalleryFragment extends Fragment {



    private BaseActivity context;
    RecyclerView recyclerView;
    private TextView visitDate;
    //private TextView visitType;
    private View rootView;
    private FormCameraButtonWidget mContext;
    private SharedPreferences.Editor mBundle;
    private Uri uri;
    private String type;


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
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setType("image, JPG");
                    intent.putExtra(Intent.EXTRA_PACKAGE_NAME, "some data");
                    /*PackageManager packageManager = mContext.getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                    boolean isIntentSafe = activities.size() > 0;
                    if (isIntentSafe)*/
                    startActivity(Intent.createChooser(intent, "Open with"));
                    /*PackageManager pm  = ediPrint.getPackageManager();
                    Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                    Intent editIntent = new Intent(Intent.ACTION_EDIT);
                    viewIntent.setType("image, jpeg");
                    editIntent.setType("image, jpeg");
                    Intent openInChooser = Intent.createChooser(viewIntent, "Open in...");
                    startActivity(openInChooser);
                    Spannable forViewing = new SpannableString("(for editing)");
                    forViewing.setSpan(new ForegroundColorSpan(Color.CYAN), 0, forViewing.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    List<ResolveInfo> resInfo = pm.queryIntentActivities(viewIntent, 0);
                    Intent[] extraIntents = new Intent[resInfo.size()];
                    for (int i = 0; i < resInfo.size(); i++) {
                        // Extract the label, append it, and repackage it in a LabeledIntent
                        ResolveInfo ri = resInfo.get(i);
                        String packageName = ri.activityInfo.packageName;
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                        intent.setAction(Intent.ACTION_VIEW);
                        viewIntent.setType("image, jpeg");
                        //intent.setDataAndType(uri, type);
                        CharSequence label = TextUtils.concat(ri.loadLabel(pm), forViewing);
                        extraIntents[i] = new LabeledIntent(intent, packageName, label, ri.icon);
                    }*/

                    //openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
                    //startActivity(openInChooser);

                    //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { viewIntent });
                    //
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    //if (url.toString().contains(".jpg") ||
                            //url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                        // JPG file
                        //intent.setDataAndType(uri, "image/jpeg");
                    //intent.setDataAndType(Uri.parse("file://" + "/sdcard/test.jpg"), "image/*");

                    //mBundle.putString(Key.VIEW_TAG, (String)getTag());
                    //((AppCompatActivity)mContext).startActivity(galleryIntent);





                    //if(isImageFitToScreen) {
                        //isImageFitToScreen=false;
                        //imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        //imageView.setAdjustViewBounds(true);
                    //}else{
                        //isImageFitToScreen=true;
                        //imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                //}
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

    public PackageManager getPackageManager() {
        return mContext.getPackageManager();
    }
}
