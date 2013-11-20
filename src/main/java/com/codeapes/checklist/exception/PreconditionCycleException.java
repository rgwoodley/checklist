package com.codeapes.checklist.exception;

public class PreconditionCycleException extends RuntimeException {

    private static final long serialVersionUID = -7853512654380256580L;

    public PreconditionCycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreconditionCycleException(Throwable cause, String formatter, Object... args) {
        super(String.format(formatter, args), cause);
    }

    public PreconditionCycleException(String formatter, Object... args) {
        super(String.format(formatter, args));
    }

    public PreconditionCycleException(String message) {
        super(message);
    }

}
