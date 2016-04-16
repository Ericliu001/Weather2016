package com.example.ericliu.weather2016.framework.mvp;

import org.greenrobot.eventbus.EventBusException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ericliu on 16/04/2016.
 */
public enum ViewUpdateDispatcher {
    INSTANCE;

    private static final int MODIFIERS_IGNORE = Modifier.ABSTRACT | Modifier.STATIC;

    private final Map<Class<?>, List<Method>> methodsByDisplayViewClass = new HashMap<Class<?>, List<Method>>();


    public void register(DisplayView displayView) {
        Class<?> displayViewClazz = displayView.getClass();

        if (methodsByDisplayViewClass.containsKey(displayViewClazz)) {
            // do not go throught the process for the same class more than once
            return;
        }

        for (Method method : displayViewClazz.getDeclaredMethods()) {
            DisplayElement displayElement = method.getAnnotation(DisplayElement.class);
            if (displayElement != null) {
                int modifiers = method.getModifiers();
                if ((modifiers & Modifier.PUBLIC) != 0 && (modifiers & MODIFIERS_IGNORE) == 0) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 1 || parameterTypes.length == 0) {

                        addMethod(displayViewClazz, method);


                    } else if (method.isAnnotationPresent(DisplayElement.class)) {
                        throw new EventBusException("@DisplayElement method " + method.getName() +
                                "must have exactly 1 parameter but has " + parameterTypes.length);
                    }


                } else if (method.isAnnotationPresent(DisplayElement.class)) {
                    String methodName = method.getDeclaringClass().getName() + "." + method.getName();
                    throw new EventBusException(methodName +
                            " is a illegal @DisplayElement method: must be public, non-static, and non-abstract");
                }


            }
        }

    }

    private void addMethod(Class<?> displayViewClazz, Method method) {
        if (!methodsByDisplayViewClass.containsKey(displayViewClazz)) {
            methodsByDisplayViewClass.put(displayViewClazz, new ArrayList<Method>());
        }

        List<Method> methodList = methodsByDisplayViewClass.get(displayViewClazz);
        if (!methodList.contains(method)) {
            methodList.add(method);
        }

    }


    public void refreshDisplayElement(DisplayView displayView, int id, Object element) {

        Class<?> displayViewClazz = displayView.getClass();
        if (!methodsByDisplayViewClass.containsKey(displayViewClazz)) {
            throw new NoClassDefFoundError("This DisplayView has never been registered.");
        }

        List<Method> methodList = methodsByDisplayViewClass.get(displayViewClazz);
        for (Method method :
                methodList) {
            DisplayElement elementAnnotation = method.getAnnotation(DisplayElement.class);
            if (elementAnnotation.id() != id) {
                continue;

            }

            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length == 1) {
                Class<?> elementType = parameterTypes[0];

                if (!element.getClass().equals(elementType)) {
                    throw new IllegalArgumentException("the parameter type is not the same as the registered method parameter " + elementType.getName());
                }
            }
            try {
                if (parameterTypes.length == 1) {
                    Class<?> elementType = parameterTypes[0];

                    if (!element.getClass().equals(elementType)) {
                        throw new IllegalArgumentException("the parameter type is not the same as the registered method parameter " + elementType.getName());
                    }
                    method.invoke(displayView, element);
                } else {
                    method.invoke(displayView);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            if (elementAnnotation.id() == id) {
                return;
            }

        }

        throw new NoSuchMethodError("this refreshDisplayEnum is not handled in " + displayViewClazz.getName());

    }
}
