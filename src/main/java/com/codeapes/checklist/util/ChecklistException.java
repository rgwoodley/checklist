package com.codeapes.checklist.util;

public class ChecklistException extends RuntimeException {

    private static final long serialVersionUID = 2897773177082836197L;

    public ChecklistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChecklistException(Throwable cause, String formatter, Object... args) {
        super(String.format(formatter, args), cause);
    }

    public ChecklistException(String formatter, Object... args) {
        super(String.format(formatter, args));
    }

    public ChecklistException(String message) {
        super(message);
    }

}
