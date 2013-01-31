package com.codeapes.checklist.util;

import org.apache.log4j.Logger;

public class AppLogger {

    private final Logger logger;

    public AppLogger(Class<?> clazz) {

        this.logger = Logger.getLogger(clazz);
    }

    public void debug(String formatter, Object... args) {

        if (logger.isDebugEnabled()) {
            logger.debug(String.format(formatter, args));
        }
    }

    public void debug(String message) {

        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    public void info(String formatter, Object... args) {

        if (logger.isInfoEnabled()) {
            logger.info(String.format(formatter, args));
        }
    }

    public void info(String message) {

        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    public void error(String formatter, Object... args) {
        logger.error(String.format(formatter, args));
    }

    public void error(String message) {
        logger.error(message);
    }

    public void fatal(String formatter, Object... args) {
        logger.fatal(String.format(formatter, args));
    }

    public void fatal(String message) {
        logger.fatal(message);
    }
}
