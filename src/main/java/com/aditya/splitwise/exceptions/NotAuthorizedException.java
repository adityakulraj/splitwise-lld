package com.aditya.splitwise.exceptions;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String str) {
        super(str);
    }

}
