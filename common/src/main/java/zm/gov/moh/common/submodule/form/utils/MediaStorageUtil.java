package zm.gov.moh.common.submodule.form.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class MediaStorageUtil {

    public static String EDI_DIRECTORY = "EDI";

    public static File getPrivateAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("Cool", "Directory not created");
        }
        return file;
    }

}
