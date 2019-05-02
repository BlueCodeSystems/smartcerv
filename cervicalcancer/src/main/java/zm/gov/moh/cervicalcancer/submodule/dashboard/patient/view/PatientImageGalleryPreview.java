/*package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import zm.gov.moh.cervicalcancer.R;*/


/*public class PatientImageGalleryPreview extends AppCompatActivity {

    ImageView GalleryPreviewImg;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.gallery_thumbnail);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        GalleryPreviewImg = findViewById(R.id.GalleryPreviewImg);
        Glide.with(PatientImageGalleryPreview.this)
                .load(new File(path)) // Uri of the picture
                .into(GalleryPreviewImg);
    }
}*/
