package com.codeapes.checklist.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public final class IntrospectionUtility {

    private static final AppLogger logger = new AppLogger(IntrospectionUtility.class);
    private static Map<String, Map<String, PropertyDescriptor>> descriptorsByNameByType;
    private static Map<String, List<PropertyDescriptor>> descriptorsByType;

    static {
        descriptorsByNameByType = new ConcurrentHashMap<String, Map<String, PropertyDescriptor>>();
        descriptorsByType = new ConcurrentHashMap<String, List<PropertyDescriptor>>();
    }

    private IntrospectionUtility() {
        super();
    }

    public static List<PropertyDescriptor> getPropertyDescriptors(Class<?> clazz) {
        List<PropertyDescriptor> descriptors = descriptorsByType.get(clazz.getName());
        if (descriptors == null) {
            descriptors = new Vector<PropertyDescriptor>();
            try {
                final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
                final PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor descriptor : properties) {
                    descriptors.add(descriptor);
                }
                descriptorsByType.put(clazz.getName(), descriptors);
            } catch (IntrospectionException ie) {
                throw new ChecklistException(ie.getMessage(), ie);
            }
        }
        return descriptors;
    }

    public static PropertyDescriptor getPropertyDescriptor(String descriptorName, Class<?> clazz) {
        Map<String, PropertyDescriptor> descriptorMap = descriptorsByNameByType.get(clazz.getName());
        if (descriptorMap == null) {
            descriptorMap = new ConcurrentHashMap<String, PropertyDescriptor>();
            try {
                final BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
                final PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor descriptor : properties) {
                    descriptorMap.put(descriptor.getName(), descriptor);
                }
                descriptorsByNameByType.put(clazz.getName(), descriptorMap);
            } catch (IntrospectionException ie) {
                throw new ChecklistException(ie.getMessage(), ie);
            }
        }
        return descriptorMap.get(descriptorName);
    }

    public static Object executeGetMethod(Object object, Method getMethod) {
        Object value = null;
        if (object != null) {
            try {
                value = getMethod.invoke(object, (Object[]) null);
            } catch (IllegalAccessException iae) {
                logger.error(iae.getMessage());
            } catch (InvocationTargetException ite) {
                logger.error(ite.getMessage());
            }
        }
        return value;
    }

    public static void executeSetMethod(Object object, Method setMethod, Object value) {
        try {
            final Object[] parameters = { value };
            setMethod.invoke(object, parameters);
        } catch (IllegalAccessException iae) {
            logger.error(iae.getMessage());
        } catch (InvocationTargetException ite) {
            logger.error(ite.getMessage());
        }
    }

}
