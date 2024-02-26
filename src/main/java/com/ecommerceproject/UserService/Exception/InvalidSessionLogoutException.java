package com.ecommerceproject.UserService.Exception;

public class InvalidSessionLogoutException extends RuntimeException{

    public InvalidSessionLogoutException() {
    }

    public InvalidSessionLogoutException(String message) {
        super(message);
    }

    public InvalidSessionLogoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSessionLogoutException(Throwable cause) {
        super(cause);
    }

    public InvalidSessionLogoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
