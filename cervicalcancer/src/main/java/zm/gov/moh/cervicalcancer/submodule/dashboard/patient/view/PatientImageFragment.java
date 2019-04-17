package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.view;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import zm.gov.moh.cervicalcancer.R;

import static android.app.Activity.RESULT_OK;

public class PatientImageFragment extends Fragment {

    private ImageView img1, img2;
    private final int CODE_IMG_GALLERY = 1;
    private final int CODE_MULTIPLE_IMG_GALLERY = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        init();

        img1.setOnClickListener((View v) -> {
            loadImageMethod(v);
            startActivityForResult(Intent.createChooser(new Intent().setAction(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    "Digital Cervicography"), CODE_IMG_GALLERY);
        })

        ;

        img2.setOnClickListener((View v) -> {
                    loadImageMethod(v);
                    //MULTIPLE IMGS
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Digital Cervicography"),
                            CODE_MULTIPLE_IMG_GALLERY);
                }
        );
    }


    public void loadImageMethod(View v) {

    }



    private void init() {}

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_IMG_GALLERY && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            if(imageUri !=null){
                img1.setImageURI(imageUri);
            }
        } else if (resultCode == CODE_MULTIPLE_IMG_GALLERY && resultCode == RESULT_OK){

            ClipData clipData = data.getClipData();

            if(clipData!=null) {

                img1.setImageURI(clipData.getItemAt(0).getUri());
                img2.setImageURI(clipData.getItemAt(1).getUri());

                for (int i = 0; i < clipData.getItemCount(); i++) {

                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    Log.e("IMGS", uri.toString());
                }
            }

                }
            }

            }


