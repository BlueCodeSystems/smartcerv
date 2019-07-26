package zm.gov.moh.core.repository.api;

import android.content.SharedPreferences;

import io.reactivex.Maybe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.database.Database;


public interface Repository {

    Database getDatabase();

    SharedPreferences getDefaultSharePrefrences();

    RestApi getRestApi();
}
