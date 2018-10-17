package zm.gov.moh.core.util;

import okhttp3.Credentials;

public class UtilImpl implements Util {

    @Override
    public String credentialsToBase64(CharSequence username, CharSequence password) {

       return Credentials.basic(username.toString(), password.toString());
    }

}
