/*package zm.gov.moh.common.submodule.dashboard.client.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import zm.gov.moh.common.R;
*/
/*
public class FilterActivity extends AppCompatActivity {
    private ArrayList<File> files = new ArrayList<File>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        files = (ArrayList<File>) getIntent().getSerializableExtra("DATA");
    }

    public void filterDate(View v) {
        Intent filterDate = new Intent(this, FilterDateActivity.class);
        filterDate.putExtra("DATA", files);
        startActivity(filterDate);
    }

    public void filterLocation(View v) {
        Intent filterLocation = new Intent(this, FilterLocationActivity.class);
        filterLocation.putExtra("DATA", files);
        startActivity(filterLocation);
    }

    public void filterText(View v) {
        Intent filterText = new Intent(this, FilterTextActivity.class);
        filterText.putExtra("DATA", files);
        startActivity(filterText);
    }
}
*/