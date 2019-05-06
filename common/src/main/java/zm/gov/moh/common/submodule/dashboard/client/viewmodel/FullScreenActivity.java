package zm.gov.moh.common.submodule.dashboard.client.viewmodel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import zm.gov.moh.common.R;

public class FullScreenActivity extends AppCompatActivity
{
    ViewPager viewPager;
    String[] images;
    int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        if(savedInstanceState==null)
        {
            Intent i = getIntent();
            images = i.getStringArrayExtra("IMAGES");
            position = i.getIntExtra("POSITION",0);
        }


        viewPager = (ViewPager)findViewById(R.id.viewPager);

        FullSizeAdapter fullSizeAdapter = new FullSizeAdapter(this,images);
        viewPager.setAdapter(fullSizeAdapter);
        viewPager.setCurrentItem(position,true);

    }
}
