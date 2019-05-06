/*package zm.gov.moh.common.submodule.dashboard.client.viewmodel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import zm.gov.moh.common.R;
*/
/*
public class ResultActivity extends AppCompatActivity {

    private ImageView result;
    private String path;
    private Bitmap image;
    private ArrayList<File> results;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        results = (ArrayList<File>) getIntent().getSerializableExtra("pictures");

        if (results != null && results.size() != 0) {
            path = results.get(0).getAbsolutePath();
            image = BitmapFactory.decodeFile(path);

            result = (ImageView) findViewById(R.id.resultView);
            result.setImageBitmap(image);
        } else {
            Toast.makeText(this, "No picture can be found", Toast.LENGTH_SHORT).show();
        }
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

    //this is home method
    public void home(View v) {
        Intent intent = new Intent(this, AlbumMainActivity.class);
        startActivity(intent);
    }

    public void left(View v) {
        if (results != null && index > 0) {
            index--;
            path = results.get(index).getAbsolutePath();
            image = BitmapFactory.decodeFile(path);

            result = (ImageView) findViewById(R.id.resultView);
            result.setImageBitmap(image);
        } else {
            Toast.makeText(this, "No more pictures anymore", Toast.LENGTH_SHORT).show();
        }
    }

    public void right(View v) {
        if (results != null && index < results.size() - 1) {
            index++;
            path = results.get(index).getAbsolutePath();
            image = BitmapFactory.decodeFile(path);

            result = (ImageView) findViewById(R.id.resultView);
            result.setImageBitmap(image);
        } else {
            Toast.makeText(this, "No more pictures", Toast.LENGTH_SHORT).show();
        }
    }

}

*/