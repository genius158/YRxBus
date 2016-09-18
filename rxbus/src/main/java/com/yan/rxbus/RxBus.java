package com.yan.rxbus;

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

    private ConcurrentMap<Object, CompositeSWithSubS> subSConcurrentHashMap =
            new ConcurrentHashMap<>();

    private static RxBus rxBus;

    private RxBus() {
        BUS = new SerializedSubject<>(PublishSubject.create());
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

    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return BUS.ofType(eventType);
    }


    /**
     * ------------------------------------------------------------------------------------------
     * 注册
     *
     * @param object
     */

    public synchronized void register(Object object) {
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        CompositeSWithSubS subscriberMethods =
                AnalysisAnnotated.findAnnotatedSubscriberMethods(object, compositeSubscription);
        subSConcurrentHashMap.put(object, subscriberMethods);
    }

    public void unRegister(Object object) {
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        CompositeSWithSubS subscriberMethods = subSConcurrentHashMap.get(object);
        subscriberMethods.getCompositeSubscription().unsubscribe();
        subSConcurrentHashMap.remove(object);
    }

}
