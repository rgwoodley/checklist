package com.codeapes.checklist.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements ApplicationContextAware {
    private static SpringApplicationContext instance;
    private ApplicationContext context;

    public SpringApplicationContext() {
        synchronized (SpringApplicationContext.class) {
            if (instance == null) {
                instance = this;
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }

    public ApplicationContext getAppContext() {
        return context;
    }

    public static SpringApplicationContext getInstance() {
        return instance;
    }
}
