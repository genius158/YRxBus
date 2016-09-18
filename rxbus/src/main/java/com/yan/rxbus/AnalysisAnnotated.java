package com.yan.rxbus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import rx.subscriptions.CompositeSubscription;

/**
 * 注解解析类
 */
public final class AnalysisAnnotated {

    public static CompositeSWithSubS findAnnotatedSubscriberMethods(Object listenerClass, CompositeSubscription compositeSubscription) {
        Set<SubscriberEvent> producerMethods = new HashSet<>();
        return findAnnotatedMethods(listenerClass, producerMethods, compositeSubscription);
    }

    private static CompositeSWithSubS findAnnotatedMethods(Object listenerClass, Set<SubscriberEvent> subscriberMethods, CompositeSubscription compositeSubscription) {
        for (Method method : listenerClass.getClass().getDeclaredMethods()) {
            if (method.isBridge()) {
                continue;
            }
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation but requires "
                            + parameterTypes.length + " arguments.  Methods must require a single argument.");
                }

                Class<?> parameterClazz = parameterTypes[0];

                if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                    throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation on " + parameterClazz
                            + " but is not 'public'.");
                }

                Subscribe annotation = method.getAnnotation(Subscribe.class);
                EventThread thread = annotation.thread();

                SubscriberEvent subscriberEvent = new SubscriberEvent(listenerClass, method, thread);
                if (!subscriberMethods.contains(subscriberEvent)) {
                    subscriberMethods.add(subscriberEvent);
                    compositeSubscription.add(subscriberEvent.getSubscription());
                }
            }
        }
        return new CompositeSWithSubS(compositeSubscription, listenerClass, subscriberMethods);
    }

}
