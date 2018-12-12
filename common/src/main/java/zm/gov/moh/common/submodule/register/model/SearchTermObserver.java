package zm.gov.moh.common.submodule.register.model;

import androidx.core.util.Consumer;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import zm.gov.moh.common.BR;

public class SearchTermObserver extends BaseObservable {

    private CharSequence term = "";
    private Consumer<String> callback;

    @Bindable
    public CharSequence getTerm() {
        return term;
    }

    public void setTerm(final CharSequence term) {

        if(this.term != term){

            this.term = term;
            notifyPropertyChanged(BR.term);
            callback.accept(term.toString());
        }
    }

    public SearchTermObserver(Consumer<String> callback){
        this.callback = callback;
    }
}