package com.codeapes.checklist.test.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class TestConfiguration {

    private static TestConfiguration instance;
    private ApplicationContext appContext;

    private TestConfiguration() {

        final String[] configLocations = { "spring-config/checklist-config-test.xml",
            "spring-config/checklist-props.xml", "spring-config/checklist-jdbc.xml",
            "spring-config/checklist-data.xml", "spring-config/checklist-service.xml",
            "spring-config/checklist-security.xml", "spring-config/checklist-webutil-test.xml", };

        appContext = new ClassPathXmlApplicationContext(configLocations);
    }

    public static synchronized TestConfiguration getInstance() {

        if (instance == null) {
            instance = new TestConfiguration();
        }

        return instance;
    }

    public ApplicationContext getApplicationContext() {
        return appContext;
    }

}
