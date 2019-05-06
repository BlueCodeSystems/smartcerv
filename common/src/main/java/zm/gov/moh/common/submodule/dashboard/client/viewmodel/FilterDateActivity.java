/*package zm.gov.moh.common.submodule.dashboard.client.viewmodel;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import zm.gov.moh.common.R;
*/
/*public class FilterDateActivity extends AppCompatActivity {
    private ArrayList<File> files = new ArrayList<File>();
    private ArrayList<File> results = new ArrayList<File>();
    private int photoDateValue;
    private String filename, searchterm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_date);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        files = (ArrayList<File>) getIntent().getSerializableExtra("DATA");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home(view);
            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    public void greater(View v) {
        EditText date = (EditText) findViewById(R.id.date);
        results = new ArrayList<File>();
        if (files != null && date != null) {

            int dateValue = Integer.parseInt(date.getText().toString());

            for (int i = 0; i < files.size(); i++) {
                searchterm = files.get(i).getName();
                filename = searchterm.substring(6, 14);
                photoDateValue = Integer.parseInt(filename);
                if (files.get(i) != null && photoDateValue > dateValue) {
                    results.add(files.get(i));
                }
            }

            Intent in = new Intent(this, ResultActivity.class);
            in.putExtra("pictures", results);
            startActivity(in);
        } else {
            Toast.makeText(this, "Please enter search term", Toast.LENGTH_SHORT).show();
        }
    }

    public void less(View v) {
        EditText date = (EditText) findViewById(R.id.date);
        results = new ArrayList<File>();
        if (files != null && date != null) {

            int dateValue = Integer.parseInt(date.getText().toString());

            for (int i = 0; i < files.size(); i++) {
                filename = files.get(i).getName().substring(6, 14);

                photoDateValue = Integer.parseInt(filename);
                if (photoDateValue < dateValue) {
                    results.add(files.get(i));
                }
            }

            Intent in = new Intent(this, ResultActivity.class);
            in.putExtra("pictures", results);
            startActivity(in);
        } else {
            Toast.makeText(this, "Please enter search term", Toast.LENGTH_SHORT).show();
        }
    }

    public void home(View v) {
        Intent intent = new Intent(this, AlbumMainActivity.class);
        startActivity(intent);
    }

}*/

