package com.codeapes.checklist.web.viewhelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ChecklistException;

/**
 * ViewHelperUtility is a utility class with static methods that make it more
 * convenient to convert between ViewHelper objects and domain class instances.
 * 
 * @author Joe Kuryla
 */
public final class ViewHelperUtility {

    private static final AppLogger logger = new AppLogger(ViewHelperUtility.class);
    
    private ViewHelperUtility() {
        super();
    }

    public static List<? extends ViewHelper> convertList(List<?> originalList, Class<?> viewHelperClass) {
        final List<ViewHelper> newList = new ArrayList<ViewHelper>();
        if (originalList != null && viewHelperClass != null) {
            for (Object object : originalList) {
                final ViewHelper viewHelper = createAndPopulateViewHelper(object, viewHelperClass);
                addToListIfNotNull(viewHelper, newList);
            }
        }
        return newList;
    }

    private static ViewHelper createAndPopulateViewHelper(Object inputObject, Class<?> viewHelperClass) {
        ViewHelper viewHelper = null;
        try {
            final Constructor<?> constructor = viewHelperClass.getConstructor(Class.class);
            viewHelper = (ViewHelper) constructor.newInstance(inputObject.getClass());
            viewHelper.populate(inputObject);
        } catch (NoSuchMethodException nsme) {
            logger.error(nsme.getMessage());
            throw new ChecklistException(nsme.getMessage(), nsme);
        } catch (InvocationTargetException ite) {
            logger.error(ite.getMessage());
            throw new ChecklistException(ite.getMessage(), ite);
        } catch (IllegalAccessException iae) {
            logger.error(iae.getMessage());
            throw new ChecklistException(iae.getMessage(), iae);
        } catch (InstantiationException ie) {
            logger.error(ie.getMessage());
            throw new ChecklistException(ie.getMessage(), ie);
        }
        return viewHelper;
    }

    private static void addToListIfNotNull(ViewHelper viewHelper, List<ViewHelper> list) {
        if (viewHelper != null) {
            list.add(viewHelper);
        }
    }
}
