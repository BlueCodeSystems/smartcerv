package zm.gov.moh.core.service;

import org.reactivestreams.Subscription;

public abstract class BasePresenter implements BasePresenterContract {
    private CompositeSubscription mSubscription;

    public BasePresenter() {
        mSubscription = new CompositeSubscription();
    }

    public void addSubscription(Subscription subscription) {
        if (mSubscription != null) {
            mSubscription.add(subscription);
        }
    }

    @Override
    public void unsubscribe() {
        if (mSubscription != null) {
            mSubscription.clear();
        }
    }

    private class CompositeSubscription {
        public void add(Subscription subscription) {
        }

        public void clear() {
        }
    }
}