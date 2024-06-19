package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private final Map<Class<?>, Object> container = new HashMap<>();

    public void register(Class<?> type) {
        try {
            Object mainBean = type.getDeclaredConstructor().newInstance();
            container.put(type, mainBean);
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Object bean = container.get(field.getType());
                    if (bean == null) continue;
                    field.setAccessible(true);
                    field.set(mainBean, bean);
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getBean(Class<T> type) {
        Object bean = container.get(type);
        if (bean == null) throw new NullPointerException();
        return (T) bean;

    }
}
