package zm.gov.moh.core;

import android.os.Build;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.Extension;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
class RandomParametersExtension implements Extension {
    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean isCritical() {
        return false;
    }

    @Override
    public byte[] getValue() {
        return new byte[0];
    }

    @Override
    public void encode(OutputStream out) throws IOException {

    }
}
