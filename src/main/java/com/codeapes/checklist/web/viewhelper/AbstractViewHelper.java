package com.codeapes.checklist.web.viewhelper;

import java.beans.PropertyDescriptor;
import java.util.List;

import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ChecklistException;
import com.codeapes.checklist.util.IntrospectionUtility;
import com.codeapes.checklist.web.viewhelper.annotation.Mapped;

/**
 * AbstractViewHelper is the abstract base class for ViewHelper classes, and
 * provides a hopefully easy-to-use mechanism to populate ViewHelper instances
 * from domain objects.
 * 
 * <p>
 * To use these methods, you must use the constructor that takes a class
 * (sourceType) as a parameter. The sourceType is the domain class you want to
 * be able to populate from. Once the class has been set, the populate method
 * should work. The populate method will read the properties from the object of
 * the sourceType, and set corresponding properties on this ViewHelper as long
 * as those properties have the same name and type. The createTargetInstance
 * method will create an instance of the sourceType, and populate it with data
 * from this ViewHelper object.
 * </p>
 * 
 * @author Joe Kuryla
 */
public class AbstractViewHelper implements ViewHelper {

    private static final AppLogger logger = new AppLogger(AbstractViewHelper.class); // NOSONAR

    private Class<?> sourceType;
    private Object inputObject;

    public AbstractViewHelper(Class<?> sourceType) {
        this.sourceType = sourceType;
    }

    public AbstractViewHelper(Class<?> sourceType, Object inputObject) {
        this.sourceType = sourceType;
        this.inputObject = inputObject;
    }
    
    @Override
    public void populate(Object inputObject) {
        this.inputObject = inputObject;
        populate();
    }
    
    @Override
    public void populate() {
        copyProperties(inputObject);
    }

    private void copyProperties(Object inputObject) {
        validateInputObject(inputObject);
        final List<PropertyDescriptor> descriptors = IntrospectionUtility.getPropertyDescriptors(this.getClass());
        for (PropertyDescriptor viewHelperDescriptor : descriptors) {
            if (!"class".equals(viewHelperDescriptor.getName())) {
                processViewHelperDescriptor(inputObject, viewHelperDescriptor);
            }
        }
    }

    private void processViewHelperDescriptor(Object inputObject, PropertyDescriptor viewHelperDescriptor) {
        final Mapped mappingInfo = viewHelperDescriptor.getReadMethod().getAnnotation(Mapped.class);
        if (mappingInfo != null) {
            final String descriptorName = getOtherDescriptorName(viewHelperDescriptor, mappingInfo);
            logger.debug("  Mapping descriptor: %s", descriptorName);
            copyProperty(inputObject, descriptorName, this, viewHelperDescriptor);
        } else {
            logger.error(
                    "  Attempt to automatically map property, but the getter in the view helper is not annotated "
                    + "with '@Mapped'.  Property: %s", viewHelperDescriptor.getName());
        }
    }

    private String getOtherDescriptorName(PropertyDescriptor viewHelperDescriptor, Mapped mappingInfo) {
        String otherDescriptorName = mappingInfo.source();
        if (otherDescriptorName == null || "".equals(otherDescriptorName.trim())) {
            otherDescriptorName = viewHelperDescriptor.getName();
        }
        return otherDescriptorName;
    }

    private void copyProperty(Object sourceObject, String sourceDescriptorNames, Object targetObject,
            PropertyDescriptor targetDescriptor) {

        PropertyDescriptor otherDescriptor = null;
        final String[] propertyNames = sourceDescriptorNames.split("\\.");
        Class<?> descriptorType = sourceType;
        Object object = sourceObject;
        for (String propertyName : propertyNames) {
            logger.debug("  Property being processed: %s", propertyName);
            otherDescriptor = IntrospectionUtility.getPropertyDescriptor(propertyName, descriptorType);
            if (otherDescriptor != null) {
                descriptorType = otherDescriptor.getPropertyType();
                object = IntrospectionUtility.executeGetMethod(object, otherDescriptor.getReadMethod());
                logger.debug("  Property Value: %s", object);
            } else {
                logger.error("  Property of %s on type %s does not exist!", propertyName,
                        descriptorType.getSimpleName());
                return;
            }
        }
        IntrospectionUtility.executeSetMethod(targetObject, targetDescriptor.getWriteMethod(), object);
    }

    private void validateInputObject(Object inputObject) {
        if (inputObject == null) {
            throw new ChecklistException("inputObject passed to method of AbstractViewHelper is null. %s", this
                    .getClass().getSimpleName());
        } else if (sourceType == null) {
            throw new ChecklistException("sourceType in this ViewHelper is null. %s", this.getClass().getSimpleName());
        } else if (!sourceType.equals(inputObject.getClass())) {
            throw new ChecklistException("inputObject is of type %s, but expected it to be of type %s.", inputObject
                    .getClass().getSimpleName(), sourceType.getSimpleName());
        }
    }

}
