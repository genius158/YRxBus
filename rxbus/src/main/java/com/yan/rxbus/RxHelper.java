package com.yan.rxbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by yan on 2016/9/21.
 */
public class RxHelper {
    protected final static Map<Class<?>, Object> mStickyEventMap;
    protected final static Subject<Object, Object> BUS;

    static {
        BUS = new SerializedSubject<>(PublishSubject.create());
        mStickyEventMap = new HashMap<>();
    }

    protected RxHelper() {
    }


    /**
     * the most important method
     * make relevance
     *
     * @param eventType eventType
     * @param <T>       T
     * @return Observable
     */
    protected static <T> Observable<T> toObservable(Class<T> eventType) {
        return BUS.ofType(eventType);
    }

    /**
     * Sticky dell
     *
     * @param event event
     */
    protected static synchronized void dellSticky(Object event) {
        if (!mStickyEventMap.isEmpty()) {
            List<Class> classes = new ArrayList<>();

            for (Map.Entry<Class<?>, Object> objectEntry : mStickyEventMap.entrySet())
                if (objectEntry.getKey() == event.getClass())
                    classes.add(event.getClass());

            mStickyEventMapRemove(classes);
        }
    }

    protected static void mStickyEventMapRemove(List<Class> classes) {
        for (Class aClass : classes) mStickyEventMap.remove(aClass);
    }
}
