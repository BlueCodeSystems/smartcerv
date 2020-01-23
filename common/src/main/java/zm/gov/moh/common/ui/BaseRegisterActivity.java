package zm.gov.moh.common.ui;

import androidx.appcompat.app.AppCompatActivity;
import zm.gov.moh.common.R;
import zm.gov.moh.common.base.BaseActivity;
import zm.gov.moh.common.submodule.register.adapter.ClientListAdapter;
import zm.gov.moh.common.submodule.register.model.SearchTermObserver;
import zm.gov.moh.core.utils.BaseAndroidViewModel;

import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseRegisterActivity<T> extends BaseActivity {

    protected SearchTermObserver searchTermObserver;
    protected List<Long> emptyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_register);
        searchTermObserver = new SearchTermObserver(this::searchCallback);
        emptyList = new LinkedList<>();
    }

    public void searchCallback(String term){

        if(!(term.equals("") || term == null))
            viewModel.getRepository().getDatabase().clientFtsDao().findClientByTerm(term).observe(this, this::matchedSearchId);
        else
            getAllClient();

    }

    public SearchTermObserver getSearchTermObserver() {
        return searchTermObserver;
    }

    public abstract void matchedSearchId(List<Long> ids);

    public abstract void getAllClient();

    public abstract T getAdapter();
}
