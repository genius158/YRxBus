package com.yan.rxbus;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yan on 2016/9/18.
 */
public class RxBus {

    private final Subject<Object, Object> BUS;

    private static RxBus rxBus;

    private final Map<Class<?>, Object> mStickyEventMap;

    private ConcurrentMap<Object, CompositeSWithSubS>
            subSConcurrentHashMap = new ConcurrentHashMap<>();

    private RxBus() {
        BUS = new SerializedSubject<>(PublishSubject.create());
        mStickyEventMap = new HashMap<>();
    }

    public static synchronized RxBus getInstance() {
        if (rxBus == null)
            synchronized (RxBus.class) {
                if (rxBus == null) rxBus = new RxBus();
            }
        return rxBus;
    }

    public synchronized void post(Object o) {
        BUS.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return BUS.ofType(eventType);
    }

    /**
     * register
     *
     * @param object
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
        if (mStickyEventMap.size() > 0) {
            subscriberMethods.subscriberSticky(mStickyEventMap);
        }
    }

    /**
     * unRegister
     *
     * @param object
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
     * 发送一个新Sticky事件
     */
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
    }

}
