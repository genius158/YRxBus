package com.yan.rxbus;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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

    public final CompositeSubscription getCompositeSubscription() {
        return compositeSubscription;
    }

    public final void setCompositeSubscription(CompositeSubscription compositeSubscription) {
        this.compositeSubscription = compositeSubscription;
    }

    public final Object getObject() {
        return object;
    }

    public final void setObject(Class<?> object) {
        this.object = object;
    }

    public final Set<SubscriberEvent> getSubscriberEvents() {
        return subscriberEvents;
    }

    public final void setSubscriberEvents(Set<SubscriberEvent> subscriberEvents) {
        this.subscriberEvents = subscriberEvents;
    }

    public CompositeSWithSubS(CompositeSubscription compositeSubscription, Object object, Set<SubscriberEvent> subscriberEvents) {
        this.compositeSubscription = compositeSubscription;
        this.object = object;
        this.subscriberEvents = subscriberEvents;
    }

    public final void subscriberSticky(Map<Class<?>, Object> objectMap) {
        List<Class> classes = new ArrayList<>();
        for (Map.Entry<Class<?>, Object> classObjectEntry : objectMap.entrySet()) {
            for (SubscriberEvent subscriberEvent : subscriberEvents) {
                if (classObjectEntry.getKey() == subscriberEvent.getParameter()) {
                    try {
                        classes.add(classObjectEntry.getKey());
                        subscriberEvent.handleEvent(classObjectEntry.getValue());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        RxBus.getInstance().mStickyEventMapRemove(classes);
    }
}
