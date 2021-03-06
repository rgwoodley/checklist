package com.codeapes.checklist.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codeapes.checklist.util.AppLogger;
import com.googlecode.jmapper.JMapper;

public final class DTOUtil {
    
    private static final AppLogger logger = new AppLogger(DTOUtil.class); // NOSONAR
    private static final String RAW_TYPES = "rawtypes";
    private static final String UNCHECKED = "unchecked";
    
    @SuppressWarnings(RAW_TYPES)
    private static Map<Class[], JMapper> jMapperCache = new HashMap<Class[], JMapper>();

    private DTOUtil() { }
    
    @SuppressWarnings({ RAW_TYPES, UNCHECKED })
    public static Object convertObject(Object source, Class destinationClass) {
        logger.debug("Mapping object of class %s to class %s.", source.getClass().getSimpleName(),
                destinationClass.getSimpleName());
        final Class sourceClass = source.getClass();
        final Class[] types = { destinationClass, sourceClass };
        final JMapper mapper = getJMapper(types);
        return mapper.getDestination(source);
    }
    
    @SuppressWarnings({ RAW_TYPES, UNCHECKED })
    public static List convertList(List sourceList, Class destinationClass) {
        final List destinationList = new ArrayList();
        for (Object sourceObject : sourceList) {
            destinationList.add(convertObject(sourceObject, destinationClass));
        }
        return destinationList;
    }

    @SuppressWarnings({ RAW_TYPES, UNCHECKED })
    private static JMapper getJMapper(Class[] types) {
        JMapper mapper = jMapperCache.get(types);
        if (mapper == null) {
            mapper = new JMapper(types[0], types[1]);
            jMapperCache.put(types, mapper);
        }
        return mapper;
    }
}
