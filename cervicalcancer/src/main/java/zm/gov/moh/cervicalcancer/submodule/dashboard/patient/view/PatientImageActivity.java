/*package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.cervicalcancer.R;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view.SlideshowDialogFragment;*/

/*public class PatientImageActivity extends AppCompatActivity {

    private String TAG = PatientImageActivity.class.getSimpleName();
    private static final String endpoint = "http://www.delaroystudios.com/glide/json/gideimages.json";
    private ArrayList<Image> images;
    private AlertDialog aDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        aDialog = new AlertDialog(this);
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleyAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        fetchImages() {

            aDialog.setMessage("Downloading json...");
            aDialog.show();

            JSONArrayRequest req = new JsonArrayRequest(endpoint,
                    (response) {
                            Log.d(TAG, response.toString());
                            aDialog.hide();

                            images.clear();
                            for (int i = 0; i < response.length(); i++)
                            {
                                try{
                                    JSONObject object = response.getJSONObject(i);
                                    Image image = new Image();
                                    image.setName(object.getString("name"));

                                    JSONObject url = object.getJSONObject("url");
                                    image.setSmall(url.getString("small"));
                                    image.setMedium(url.getString("medium"));
                                    image.setLarge(url.getString("large"));
                                    image.setTimestamp(object.getString("timestamp"));

                                    images.add(image);
                                } catch (JSONException e) {
                                    Log.e(TAG, "Json parsing error:"+ e.getMessage());

                                }
                            }


    }


}*/