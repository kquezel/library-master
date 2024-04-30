package com.example.library.exception;

public class UsernameExistsException extends Exception {

    public UsernameExistsException(String msg) {
        super(msg);
    }
}