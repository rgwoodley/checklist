package com.codeapes.checklist.web.viewhelper.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ChecklistException;
import com.codeapes.checklist.web.viewhelper.ViewHelper;

public class AbstractViewHelper implements ViewHelper {

    private static final AppLogger logger = new AppLogger(AbstractViewHelper.class);

    private Class<?> classDerivedFrom;
    private Set<String> propertiesToExclude = new HashSet<String>();

    public void setClassDerivedFrom(Class<?> classDerivedFrom) {
        this.classDerivedFrom = classDerivedFrom;
    }

    public void setPropertiesToExclude(Set<String> propertiesToExclude) {
        this.propertiesToExclude = propertiesToExclude;
    }

    @Override
    public void populate(Object inputObject) {
        validateInputObject(inputObject);
        final List<PropertyDescriptor> descriptors = IntrospectionUtility.getPropertyDescriptors(this.getClass());
        for (PropertyDescriptor targetDescriptor : descriptors) {
            final String descriptorName = targetDescriptor.getName();
            final PropertyDescriptor sourceDescriptor = IntrospectionUtility.getPropertyDescriptor(descriptorName,
                classDerivedFrom);
            if (validDescriptors(sourceDescriptor, targetDescriptor)) {
                logger.debug("have two descriptors for property: %s", descriptorName);
                final Method getMethod = sourceDescriptor.getReadMethod();
                final Method setMethod = targetDescriptor.getWriteMethod();
                executeMethods(inputObject, getMethod, setMethod);
            }
        }
    }

    private void validateInputObject(Object inputObject) {
        if (classDerivedFrom == null) {
            throw new ChecklistException("classDerivedFrom is not set in this instance of %s", this.getClass()
                .getSimpleName());
        }
        if (inputObject == null) {
            throw new ChecklistException("inputObject passed to populate method of AbstractViewHelper is null.");
        }
        if (!classDerivedFrom.isInstance(inputObject)) {
            throw new ChecklistException("input object expected to be of type %s, but was of type %s.",
                classDerivedFrom.getSimpleName(), inputObject.getClass().getName());
        }
    }

    private boolean validDescriptors(PropertyDescriptor sourceDescriptor, PropertyDescriptor targetDescriptor) {
        boolean valid = true;
        final String descriptorName = targetDescriptor.getName();
        if (propertiesToExclude.contains(descriptorName) || "class".equals(descriptorName)) {
            valid = false;
        }
        if (sourceDescriptor == null) {
            valid = false;
        } else {
            final Class<?> sourceType = sourceDescriptor.getPropertyType();
            final Class<?> targetType = targetDescriptor.getPropertyType();
            if (!sourceType.equals(targetType)) {
                valid = false;
            }
        }
        return valid;
    }

    private void executeMethods(Object inputObject, Method getMethod, Method setMethod) {
        try {
            final Object value = getMethod.invoke(inputObject, (Object[]) null);
            final Object[] parameters = { value };
            setMethod.invoke(this, parameters);
        } catch (IllegalAccessException iae) {
            logger.error(iae.getMessage());
        } catch (InvocationTargetException ite) {
            logger.error(ite.getMessage());
        }
    }

}
