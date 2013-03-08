package com.codeapes.checklist.web.viewhelper.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import com.codeapes.checklist.util.ChecklistException;

public final class IntrospectionUtility {
        
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

}
