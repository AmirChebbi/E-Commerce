package com.example.ECommerce.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ExpiredTokenException extends  RuntimeException {
    public ExpiredTokenException(String msg) { super(msg); }
}

