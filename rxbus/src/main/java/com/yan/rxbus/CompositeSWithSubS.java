package com.yan.rxbus;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
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

    public void subscriberSticky(Map<Class<?>, Object> objectMap) {
        for (Map.Entry<Class<?>, Object> classObjectEntry : objectMap.entrySet()) {
            for (SubscriberEvent subscriberEvent : subscriberEvents) {
                if (classObjectEntry.getKey() == subscriberEvent.getParameter()) {
                    try {
                        subscriberEvent.handleEvent(classObjectEntry.getValue());
                        objectMap.remove(classObjectEntry.getKey());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
