/*package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import zm.gov.moh.cervicalcancer.R;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.adapter.FullSizeAdapter;*/

/*public class PatientDashboardFullScreenImageFragment extends Activity {

    ViewPager viewPager;
    String[] images;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        if(savedInstanceState==null)
        {
            Intent i = getIntent();
            images = i.getStringArrayExtra("IMAGES");
            position = i.getIntExtra("POSITION",0);
        }
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        FullSizeAdapter fullSizeAdapter = new FullSizeAdapter(this,images);
        ViewPager.setAdapter(fullSizeAdapter);
        viewPager.setCurrentItem(position,true);
    }
}*/
