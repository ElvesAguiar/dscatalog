package com.elves.dscatalog.exceptions;

import org.springframework.http.HttpStatus;

public class DomainException extends RuntimeException {
    public DomainException(String msg) {
        super(msg);
    }

    public DomainException(String msg, HttpStatus status) {
        super(msg);
    }

    public DomainException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
