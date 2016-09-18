package com.yan.rxbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private static RxBus rxBus;
    private final Subject<Object, Object> BUS;

    private final Map<Class<?>, Object> mStickyEventMap;

    private ConcurrentMap<Object, CompositeSWithSubS>
            subSConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * Sticky消息处理
     *
     * @param event
     */
    public final synchronized void dellSticky(Object event) {
        if (!mStickyEventMap.isEmpty()) {
            List<Class> classes = new ArrayList<>();

            for (Map.Entry<Class<?>, Object> objectEntry : mStickyEventMap.entrySet())
                if (objectEntry.getKey() == event.getClass())
                    classes.add(event.getClass());

            mStickyEventMapRemove(classes);
        }
    }

    public final void mStickyEventMapRemove(List<Class> classes) {
        for (Class aClass : classes) mStickyEventMap.remove(aClass);
    }

    private  RxBus() {
        BUS = new SerializedSubject<>(PublishSubject.create());
        mStickyEventMap = new HashMap<>();
    }

    public   static RxBus getInstance() {
        if (rxBus == null)
            synchronized (RxBus.class) {
                if (rxBus == null) rxBus = new RxBus();
            }
        return rxBus;
    }

    /**
     * 事件分发
     *
     * @param o
     */
    public final synchronized void post(Object o) {
        BUS.onNext(o);
    }

    /**
     * 订阅事件
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public final <T> Observable<T> toObservable(Class<T> eventType) {
        return BUS.ofType(eventType);
    }

    /**
     * register
     *
     * @param object
     */
    public final void register(Object object) {
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
     * @param object
     */
    public final void unRegister(Object object) {
        if (object == null) {
            throw new NullPointerException("Object to register must not be null.");
        }
        CompositeSWithSubS subscriberMethods = subSConcurrentHashMap.get(object);
        if (subscriberMethods != null)
            subscriberMethods.getCompositeSubscription().unsubscribe();
        subSConcurrentHashMap.remove(object);
        mStickyEventMap.remove(object);
    }


    /**
     * 发送一个新Sticky事件
     */
    public final void postSticky(Object event) {
        mStickyEventMap.put(event.getClass(), event);
        post(event);
    }

}
