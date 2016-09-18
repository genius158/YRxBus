package com.yan.rxbus;

import java.util.Set;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by yan on 2016/9/18.
 */
public class CompositeSWithSubS {
    private CompositeSubscription compositeSubscription;
    private Object object;
    private Set<SubscriberEvent> subscriberEvents;

    public CompositeSubscription getCompositeSubscription() {
        return compositeSubscription;
    }

    public void setCompositeSubscription(CompositeSubscription compositeSubscription) {
        this.compositeSubscription = compositeSubscription;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Class<?> object) {
        this.object = object;
    }

    public Set<SubscriberEvent> getSubscriberEvents() {
        return subscriberEvents;
    }

    public void setSubscriberEvents(Set<SubscriberEvent> subscriberEvents) {
        this.subscriberEvents = subscriberEvents;
    }

    public CompositeSWithSubS(CompositeSubscription compositeSubscription, Object object, Set<SubscriberEvent> subscriberEvents) {
        this.compositeSubscription = compositeSubscription;
        this.object = object;
        this.subscriberEvents = subscriberEvents;
    }
}
