package com.codeapes.checklist.web.form.util;

import java.beans.PropertyDescriptor;
import java.util.List;

import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ChecklistException;
import com.codeapes.checklist.util.IntrospectionUtility;

public final class FormUtility {

    private static final AppLogger logger = new AppLogger(FormUtility.class); // NOSONAR

    private FormUtility() {
        super();
    }

    public static Object copyStateToNewInstance(Object form, Class<?> targetType) {
        validateNewInstanceInput(form, targetType);
        Object targetObject = null;
        try {
            targetObject = targetType.newInstance();
        } catch (InstantiationException ie) {
            logger.error(ie.getMessage());
            throw new ChecklistException(ie.getMessage(), ie);
        } catch (IllegalAccessException iae) {
            logger.error(iae.getMessage());
            throw new ChecklistException(iae.getMessage(), iae);
        }
        targetObject = copyProperties(form, targetObject);
        return targetObject;
    }

    public static Object copyStateToTargetInstance(Object form, Object targetObject) {
        validateCopyInput(form, targetObject);
        return copyProperties(form, targetObject);
    }

    private static void validateNewInstanceInput(Object form, Class<?> targetType) {
        if (form == null) {
            throw new ChecklistException("Form passed to copyStateToNewInstance method is null.");
        } else if (targetType == null) {
            throw new ChecklistException("TargetType passed to copyStateToNewInstance method is null.");
        }
    }

    private static void validateCopyInput(Object form, Object targetObject) {
        if (form == null) {
            throw new ChecklistException("Form passed to copyStateToTargetInstance method is null.");
        } else if (targetObject == null) {
            throw new ChecklistException("TargetType passed to copyStateToTargetInstance method is null.");
        }
    }

    private static Object copyProperties(Object form, Object targetObject) {
        final List<PropertyDescriptor> descriptors = IntrospectionUtility.getPropertyDescriptors(form.getClass());
        for (PropertyDescriptor formDescriptor : descriptors) {
            if ("class".equals(formDescriptor.getName())) {
                continue;
            }
            processFormDescriptor(targetObject, form, formDescriptor);
        }
        return targetObject;
    }

    private static void processFormDescriptor(Object targetObject, Object form, PropertyDescriptor formDescriptor) {
        final String targetDescriptorName = formDescriptor.getName();
        logger.debug("Processing form descriptor %s", targetDescriptorName);
        final PropertyDescriptor targetDescriptor = IntrospectionUtility.getPropertyDescriptor(targetDescriptorName,
            targetObject.getClass());
        if (targetDescriptor != null) {
            final Object value = IntrospectionUtility.executeGetMethod(form, formDescriptor.getReadMethod());
            logger.debug("  value from form is %s", value);
            if (value != null) {
                IntrospectionUtility.executeSetMethod(targetObject, targetDescriptor.getWriteMethod(), value);
            }
        }
    }

}
