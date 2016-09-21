package com.yan.rxbus;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by yan on 2016/9/18.
 */
public final class RxBus extends RxHelper {
    private volatile static RxBus rxBus;

    private ConcurrentMap<Object, CompositeSWithSubS>
            subSConcurrentHashMap = new ConcurrentHashMap<>();

    public static RxBus getInstance() {
        if (rxBus == null)
            synchronized (RxBus.class) {
                if (rxBus == null) rxBus = new RxBus();
            }
        return rxBus;
    }

    /**
     * post event
     *
     * @param o event
     */
    public synchronized void post(Object o) {
        BUS.onNext(o);
    }


    /**
     * register
     *
     * @param object object
     */
    public void register(Object object) {
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        CompositeSWithSubS subscriberMethods =
                AnalysisAnnotated.findAnnotatedSubscriberMethods(object, compositeSubscription);
        subSConcurrentHashMap.put(object, subscriberMethods);

        // Sticky
        if (!mStickyEventMap.isEmpty()) {
            subscriberMethods.subscriberSticky(mStickyEventMap);
        }
    }

    /**
     * unRegister
     *
     * @param object object
     */
    public void unRegister(Object object) {
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        CompositeSWithSubS subscriberMethods = subSConcurrentHashMap.get(object);
        if (subscriberMethods != null)
            subscriberMethods.getCompositeSubscription().unsubscribe();
        subSConcurrentHashMap.remove(object);
    }


    /**
     * post a sticky event
     *
     * @param event event
     */
    public void postSticky(Object event) {
        mStickyEventMap.put(event.getClass(), event);
        post(event);
    }

}
