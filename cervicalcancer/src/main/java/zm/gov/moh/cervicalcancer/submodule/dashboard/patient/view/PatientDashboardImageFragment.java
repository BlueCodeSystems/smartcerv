/*package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter.GalleryImageAdapter;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.IRecyclerViewClickListener;*/

/*public class PatientDashboardImageFragment extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_patient_image);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        Random random = new Random();

        final String[] images = new String[10];

        for(int i=0; i<images.length;i++)
            images[i] = "https://picsum.photo/600?image=" + random.nextInt(1000 + 1);

        final IRecyclerViewClickListener listener = (view, position)-> {
            //open the activity full screen

            Intent i = new Intent(getApplicationContext(),PatientDashboardFullScreenImageFragment.class);
            i.putExtra("IMAGES",images);
            i.putExtra("POSITION", position);
            startActivity(i);



        };

        GalleryImageAdapter galleryImageAdapter = new GalleryImageAdapter(this, images,listener);
        recyclerView.setAdapter(galleryImageAdapter);
    }

}
*/