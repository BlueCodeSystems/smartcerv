package zm.gov.moh.common.submodule.dashboard.client.viewmodel;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import zm.gov.moh.common.R;


public class EnlargeActivity extends AppCompatActivity {


    private ImageView picture;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarge);

        picture = (ImageView) findViewById(R.id.picture);
        Intent intent = getIntent();

        byte[] bytes = intent.getByteArrayExtra("EXTRA_IMG");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        picture.setImageBitmap(bmp);
    }


}
