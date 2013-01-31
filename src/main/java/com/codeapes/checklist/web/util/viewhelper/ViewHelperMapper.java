package com.codeapes.checklist.web.util.viewhelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ChecklistException;

@Service
public class ViewHelperMapper {

    private static final AppLogger logger = new AppLogger(ViewHelperMapper.class);
    private static final Object[] NO_PARAMETERS = null;

    private Map<String, ViewHelperMappingInfo> mappingInfoTable = new HashMap<String, ViewHelperMappingInfo>();

    public synchronized void addMapping(String mappingName, Class<?> inputClass,
        Class<? extends ViewHelper> viewHelperClass, String[] attributes) {
        ViewHelperMappingInfo mappingInfo = mappingInfoTable.get(mappingName);
        if (mappingInfo == null) {
            mappingInfo = new ViewHelperMappingInfo(mappingName, inputClass, viewHelperClass, attributes);
            mappingInfoTable.put(mappingName, mappingInfo);
        } else {
            logger.info("WARNING:  Could not create a ViewHelperMapping with the name %s.  "
                + "A mapping with this name already exists.", mappingName);
        }
    }

    public boolean mappingExists(String mappingName) {
        return mappingInfoTable.containsKey(mappingName);
    }

    public ViewHelper convertToViewMapper(String mappingName, Object inputObject) {
        ViewHelper viewHelper = null;
        final ViewHelperMappingInfo mappingInfo = mappingInfoTable.get(mappingName);
        if (mappingInfo != null) {
            viewHelper = createViewHelperInstance(mappingInfo);
            viewHelper = populateViewHelperAttributes(inputObject, viewHelper, mappingInfo);
        } else {
            final String errorMessage = String.format("ViewHelper Mapping with name %s not found.", mappingName);
            logger.error(errorMessage);
            throw new ChecklistException(errorMessage);
        }
        return viewHelper;
    }

    private ViewHelper populateViewHelperAttributes(Object inputObject, ViewHelper viewHelper,
        ViewHelperMappingInfo mappingInfo) {

        final Map<Method, Method> methodsToInvoke = mappingInfo.getMethodsToInvoke();
        final Set<Method> allGetMethods = methodsToInvoke.keySet();
        for (Method getMethod : allGetMethods) {
            try {
                final Method setMethod = methodsToInvoke.get(getMethod);
                final Object getResult = getMethod.invoke(inputObject, NO_PARAMETERS);
                final Object[] setMethodInputParameters = { getResult };
                setMethod.invoke(viewHelper, setMethodInputParameters);
            } catch (InvocationTargetException ite) {
                handleMethodCallException(mappingInfo.getMappingName(), ite);
            } catch (IllegalAccessException iae) {
                handleMethodCallException(mappingInfo.getMappingName(), iae);
            }
        }

        return viewHelper;
    }

    private void handleMethodCallException(String mappingName, Exception e) {
        logger.error("Unable to map field: %s.", mappingName);
        logger.error(e.getMessage());
        throw new ChecklistException(e.getMessage(), e);
    }

    private ViewHelper createViewHelperInstance(ViewHelperMappingInfo mappingInfo) {
        ViewHelper viewHelper = null;
        try {
            viewHelper = mappingInfo.getViewHelperClass().newInstance();
        } catch (InstantiationException ie) {
            logger.error(ie.getMessage());
            throw new ChecklistException(ie.getMessage(), ie);
        } catch (IllegalAccessException iae) {
            logger.error(iae.getMessage());
            throw new ChecklistException(iae.getMessage(), iae);
        }
        return viewHelper;
    }

}
