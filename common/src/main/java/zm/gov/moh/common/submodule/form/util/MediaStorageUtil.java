package zm.gov.moh.common.submodule.form.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class MediaStorageUtil {

    public static File getPrivateAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.v("CameraDir for Cervical","Directory not created");
        }
        return file;
    }
}
