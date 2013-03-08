package com.codeapes.checklist.web.viewhelper.util;

import java.util.ArrayList;
import java.util.List;

import com.codeapes.checklist.util.AppLogger;
import com.codeapes.checklist.util.ChecklistException;
import com.codeapes.checklist.web.viewhelper.ViewHelper;

public final class ViewHelperUtility {

    private static final AppLogger logger = new AppLogger(ViewHelperUtility.class);

    private ViewHelperUtility() {
        super();
    }

    public static List<? extends ViewHelper> convertList(List<?> originalList,
        Class<? extends ViewHelper> viewHelperClass) {

        final List<ViewHelper> newList = new ArrayList<ViewHelper>();
        if (originalList != null && viewHelperClass != null) {
            for (Object object : originalList) {
                final ViewHelper viewHelper = createAndPopulateViewHelper(object, viewHelperClass);
                addToListIfNotNull(viewHelper, newList);
            }
        }
        return newList;
    }

    public static ViewHelper createAndPopulateViewHelper(Object inputObject,
        Class<? extends ViewHelper> viewHelperClass) {
        ViewHelper viewHelper = null;
        try {
            viewHelper = (ViewHelper) viewHelperClass.newInstance();
            viewHelper.populate(inputObject);
        } catch (InstantiationException ie) {
            logger.error(ie.getMessage());
            throw new ChecklistException(ie.getMessage(), ie);
        } catch (IllegalAccessException iae) {
            logger.error(iae.getMessage());
            throw new ChecklistException(iae.getMessage(), iae);
        }
        return viewHelper;
    }

    private static void addToListIfNotNull(ViewHelper viewHelper, List<ViewHelper> list) {
        if (viewHelper != null) {
            list.add(viewHelper);
        }
    }
}
