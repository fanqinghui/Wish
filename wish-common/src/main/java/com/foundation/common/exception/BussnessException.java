package com.foundation.common.exception;

/**
 * Created by fqh on 2015/12/9.
 */
public class BussnessException extends Exception {

    private static final long serialVersionUID = 3490319235806360289L;

    private Exception exception;
    private String message;

    public BussnessException(Exception e,String message) {
        this.exception = e;
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }
}
