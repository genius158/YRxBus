package com.yan.rxbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by yan on 2016/9/18.
 */
public class SubscriberEvent {

    private final Object target;

    private final Method method;

    private final EventThread thread;

    private Subscription subscription;

    public SubscriberEvent(Object target, Method method, EventThread thread) {
        if (target == null) {
            throw new NullPointerException("SubscriberEvent target cannot be null.");
        }
        if (method == null) {
            throw new NullPointerException("SubscriberEvent method cannot be null.");
        }
        if (thread == null) {
            throw new NullPointerException("SubscriberEvent thread cannot be null.");
        }
        this.target = target;
        this.method = method;
        this.thread = thread;
        this.method.setAccessible(true);
        initObservable(this.method.getParameterTypes()[0]);
    }

    public final Class getParameter() {
        return this.method.getParameterTypes()[0];
    }

    /**
     * initObservable
     *
     * @param aClass aClass
     */
    private final void initObservable(Class aClass) {
        subscription = RxBus.getInstance().
                toObservable(aClass)
                .observeOn(EventThread.getScheduler(thread))
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        try {
                            handleEvent(event);
                            RxBus.getInstance().dellSticky(event);
                        } catch (InvocationTargetException e) {
                            throwRuntimeException("Could not dispatch event: " + event.getClass() + " to subscriber " + SubscriberEvent.this, e);
                        }
                    }
                });
    }

    public final Subscription getSubscription() {
        return subscription;
    }


    /**
     * handleEvent
     *
     * @param event event
     * @throws InvocationTargetException
     */
    public final void handleEvent(Object event) throws InvocationTargetException {
        try {
            method.invoke(target, event);

        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            }
            throw e;
        }
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final SubscriberEvent other = (SubscriberEvent) obj;
        return method.equals(other.method) && target == other.target;
    }

    public final void throwRuntimeException(String msg, InvocationTargetException e) {
        throwRuntimeException(msg, e.getCause());
    }

    public final void throwRuntimeException(String msg, Throwable e) {
        Throwable cause = e.getCause();
        if (cause != null) {
            throw new RuntimeException(msg + ": " + cause.getMessage(), cause);
        } else {
            throw new RuntimeException(msg + ": " + e.getMessage(), e);
        }
    }
}
