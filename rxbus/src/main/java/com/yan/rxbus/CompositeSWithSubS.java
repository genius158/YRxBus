package com.yan.rxbus;

import java.util.Set;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by yan on 2016/9/18.
 */
public class CompositeSWithSubS {
    private CompositeSubscription compositeSubscription;
    private Object aClass;
    private Set<SubscriberEvent> subscriberEvents;

    public CompositeSubscription getCompositeSubscription() {
        return compositeSubscription;
    }

    public void setCompositeSubscription(CompositeSubscription compositeSubscription) {
        this.compositeSubscription = compositeSubscription;
    }

    public Object getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }

    public Set<SubscriberEvent> getSubscriberEvents() {
        return subscriberEvents;
    }

    public void setSubscriberEvents(Set<SubscriberEvent> subscriberEvents) {
        this.subscriberEvents = subscriberEvents;
    }

    public CompositeSWithSubS(CompositeSubscription compositeSubscription,Object aClass, Set<SubscriberEvent> subscriberEvents) {
        this.compositeSubscription = compositeSubscription;
        this.aClass = aClass;
        this.subscriberEvents = subscriberEvents;
    }
}
