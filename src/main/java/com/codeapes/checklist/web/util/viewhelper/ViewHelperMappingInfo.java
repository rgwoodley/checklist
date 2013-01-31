package com.codeapes.checklist.web.util.viewhelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.codeapes.checklist.util.AppLogger;

public class ViewHelperMappingInfo {

    private static final String PREFIX_GET = "get";
    private static final String PREFIX_IS = "is";
    private static final String PREFIX_SET = "set";
    private static final Class<?>[] NO_PARAMETERS = null;

    private static final AppLogger logger = new AppLogger(ViewHelperMappingInfo.class);

    private String mappingName;
    private Class<?> inputClass;
    private Class<? extends ViewHelper> viewHelperClass;
    private Map<Method, Method> methodsToInvoke = new HashMap<Method, Method>();

    protected ViewHelperMappingInfo(String mappingName, Class<?> inputClass,
        Class<? extends ViewHelper> viewHelperClass, String[] attributes) {
        super();
        logger.debug("Creating mapping named %s for input type %s which will output type %s", mappingName,
            inputClass.getName(), viewHelperClass.getName());
        this.mappingName = mappingName;
        this.viewHelperClass = viewHelperClass;
        this.inputClass = inputClass;
        captureMethods(attributes);
        logMappingInfoCreation();
    }

    private void logMappingInfoCreation() {
        if (methodsToInvoke != null) {
            logger.info("View Helper Mapping created for %s with name of %s", viewHelperClass.getName(), mappingName);
        } else {
            logger.info("WARNING: View Helper Mapping for %s with name of %s does not contain any valid attributes.",
                viewHelperClass.getName(), mappingName);
        }
    }

    private synchronized void captureMethods(String[] attributes) {
        methodsToInvoke.clear();
        for (String attribute : attributes) {
            logger.debug("mapping attribute: %s", attribute);
            final Method getMethod = findGetMethod(inputClass, attribute);
            Method setMethod = null;
            if (getMethod != null) {
                final Class<?>[] parameters = { getMethod.getReturnType() };
                setMethod = findSetMethod(viewHelperClass, attribute, parameters);
                if (setMethod != null) {
                    methodsToInvoke.put(getMethod, setMethod);
                } else {
                    logger.info("WARNING!  ViewHelperMapping attribute %s does not exist in the ViewHelper type of %s",
                        attribute, viewHelperClass.getName());
                }
            } else {
                logger.info("WARNING!  ViewHelperMapping attribute %s does not exist in the input type of %s",
                    attribute, inputClass.getName());
            }
        }
    }

    private Method findGetMethod(Class<?> clazz, String attribute) {

        Method getMethod = findMethod(clazz, generateMethodName(attribute, PREFIX_GET), NO_PARAMETERS);
        if (getMethod == null) {
            getMethod = findMethod(clazz, generateMethodName(attribute, PREFIX_IS), NO_PARAMETERS);
        }
        return getMethod;
    }

    private Method findSetMethod(Class<? extends ViewHelper> clazz, String attribute, Class<?>[] parameters) {        
        return findMethod(clazz, generateMethodName(attribute, PREFIX_SET), parameters);
    }

    private Method findMethod(Class<?> clazz, String methodName, Class<?>[] parameters) {
        Method method = null;
        try {
            method = clazz.getMethod(methodName, parameters);
        } catch (NoSuchMethodException nsme) {
            logger.debug("Cannot find method with name of %s in type %s:", methodName, clazz.getName());
        }
        return method;
    }

    private String generateMethodName(String attribute, String prefix) {
        final StringBuilder methodName = new StringBuilder(prefix);
        methodName.append(attribute.substring(0, 1).toUpperCase());
        methodName.append(attribute.substring(1));
        return methodName.toString();
    }

    public Class<?> getInputClass() {
        return inputClass;
    }

    public void setInputClass(Class<?> inputClass) {
        this.inputClass = inputClass;
    }

    public Class<? extends ViewHelper> getViewHelperClass() {
        return viewHelperClass;
    }

    public void setViewHelperClass(Class<? extends ViewHelper> viewHelperClass) {
        this.viewHelperClass = viewHelperClass;
    }

    public Map<Method, Method> getMethodsToInvoke() {
        return methodsToInvoke;
    }

    public void setMethodsToInvoke(Map<Method, Method> methodsToInvoke) {
        this.methodsToInvoke = methodsToInvoke;
    }

    public String getMappingName() {
        return mappingName;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }

}
