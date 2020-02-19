package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Key;
import com.google.common.collect.LinkedHashMultimap;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;
import java.io.File;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel.PatientDashboardViewModel;
import zm.gov.moh.common.submodule.form.utils.MediaStorageUtil;
import zm.gov.moh.common.submodule.form.widget.FormCameraButtonWidget;
import zm.gov.moh.common.base.BaseActivity;

public class PatientDashboardEDIGalleryFragment<MainActivity> extends Fragment {


    private final Object PatientDashboardEDIGalleryFragment = this;
    private BaseActivity context;
    RecyclerView recyclerView;
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


        ((PatientDashboardViewModel) context.getViewModel()).getEDIDataEmitter().observe(context, this::populateEDIRole);
        return rootView;
    }



    private void populateEDIRole(Map<String,LinkedHashMultimap<Long, String>> ediData) {

        ImageDataAdapter adapter = new ImageDataAdapter(context,ediData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);

        if(ediData.size() > 0) {
            recyclerView = rootView.findViewById(R.id.recyclerview1);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
        }

    }
}

class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.ViewHolder> {
    private  LinkedList<Map.Entry<String,LinkedHashMultimap<Long, String>>> ediVisitDataList;
    private Context context;
    private FormCameraButtonWidget ediPrint;
    private MenuItem item;
    private Object v;


    public ImageDataAdapter(Context context, Map<String,LinkedHashMultimap<Long, String>> ediVisitData) {
        ediVisitDataList = new LinkedList<>();
        this.context = context;


        for(Map.Entry<String,LinkedHashMultimap<Long, String>> ediData : ediVisitData.entrySet())
            ediVisitDataList.push(ediData);

    }

    @Override
    public ImageDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AppCompatImageView imageView = viewHolder.img,  imageView2 = viewHolder.img2;
        TextView caption = viewHolder.caption;
        Map.Entry<String,LinkedHashMultimap<Long, String>> data = ediVisitDataList.get(i);

        if(data != null && data.getValue().size() > 0) {

            File image = MediaStorageUtil.getPrivateAlbumStorageDir(context, MediaStorageUtil.EDI_DIRECTORY);
            long dateTimeEpoch = data.getValue().keySet().iterator().next();

            Iterator<String> images = data.getValue().get(dateTimeEpoch).iterator();

            Instant dateTime = Instant.ofEpochSecond(dateTimeEpoch);
            LocalDateTime visitDateTime = LocalDateTime.ofInstant(dateTime, ZoneOffset.UTC);
            String visitDateTimeFormatted = visitDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));

            caption.setText(data.getKey() + " on " + visitDateTimeFormatted);

            imageView.setOnClickListener(new View.OnClickListener() {

                private WebView touch;
                private Dialog currentAnimator;
                private boolean isImageFitToScreen;
                private File file;

                @Override
                public void onClick(View v) {

                    File ediSamples = MediaStorageUtil.getPrivateAlbumStorageDir(context, MediaStorageUtil.EDI_DIRECTORY);

                    File pic = new File(ediSamples.getAbsolutePath()+"/"+"test.png");
                    boolean there = pic.exists();

                    ImageView imageView1 = viewHolder.img3;



                    Glide.with(context)
                            .asBitmap()
                            .override(600, 600)
                            .load(pic)
                            .into(imageView2);

                   /* Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(pic), "image/*");
                    context.startActivity(intent);*/

                 /*   MediaScannerConnection.scanFile(context,
                            new String[] { pic.getAbsolutePath() }, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("onScanCompleted", uri.getPath());

                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setDataAndType(Uri.parse(uri.getPath()), "image/*");
                                    context.startActivity(intent);
                                }
                            });*/
                }
            });

            try {
                if (images.hasNext()) {
                    RequestBuilder builder = Glide
                            .with(context)
                            .asBitmap();
                    signature(new StringSignature(String.valueOf(System.currentTimeMillis())));
                    builder.load(image.getCanonicalPath() + "/" + images.next() + ".png")
                            .into(imageView);


                    if (images.hasNext()) {
                        builder.load(image.getCanonicalPath() + "/" + images.next() + ".png")
                                .into(imageView2);
                    }
                }
            } catch (Exception e) {
                Exception ex = e;
            }
        }

    }

    private void signature(StringSignature stringSignature) {
    }

    private void skipMemoryCache(boolean b) {
    }


    @Override
    public int getItemCount() {

        return ediVisitDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView img,img2,img3;
        TextView caption;

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.iv);
            img2 = view.findViewById(R.id.img2);

            img3 = view.findViewById(R.id.image_test);
            caption = view.findViewById(R.id.caption);



        }
    }

    private class StringSignature implements Key {
        public StringSignature(String s) {
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }
}