package zm.gov.moh.common.submodule.dashboard.client.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.common.R;
import zm.gov.moh.common.submodule.form.model.widgetModel.IRecyclerViewClickListener;

import static zm.gov.moh.common.R2.id.position;

public class GalleryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Random random = new Random();

        final String[] images = new String[10];

        for (int i = 0; i < images.length; i++)
            images[i] = "https://picsum.photo/600?image=" + random.nextInt(1000 + 1);

        final IRecyclerViewClickListener listener = (view, position) -> {

            Intent i = new Intent(getApplicationContext(), FullScreenActivity.class);
            i.putExtra("IMAGES", images);
            i.putExtra("POSITION", position);
            startActivity(i);


        };

        GalleryImageAdapter galleryImageAdapter = new GalleryImageAdapter(this, images, listener);
        recyclerView.setAdapter(galleryImageAdapter);
    }
}



